package utils;

import org.monte.media.Format;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.util.List;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * ====================================================
 * VideoManager - Video Recording Manager
 * ====================================================
 * 
 * Mục đích:
 * - Quản lý lifecycle của video recording
 * - Start/Stop recording
 * - Xoá video nếu test pass
 * - Giữ video nếu test fail
 * 
 * Nguyên tắc:
 * - ❌ Test FAIL → Giữ video để debug
 * - ✅ Test PASS → Xoá video để tiết kiệm disk space
 * 
 * Tích hợp:
 * - Được gọi bởi ExtentReportManager (TestNG Listener)
 * - Hoàn toàn tự động, không cần config trong test case
 * 
 * @author Framework Team
 * @version 2.0
 * @since 2026-01-25
 */
public class VideoManager {

    private static VideoRecorder recorder;
    private static String lastVideoPath;  // Lưu path của video vừa record
    private static final String VIDEO_FOLDER = "target/videos";

    /**
     * Start video recording cho 1 test case
     * 
     * @param testName String - Tên test case (dùng làm tên file video)
     */
    public static void startRecording(String testName) {
        try {
            // Reset video path từ test trước
            lastVideoPath = null;
            
            // 1. Tạo thư mục videos nếu chưa tồn tại
            File dir = new File(VIDEO_FOLDER);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 2. Lấy GraphicsConfiguration của màn hình mặc định
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            // 3. Cấu hình format cho video recording
            // File format: AVI (best compatibility với Monte library)
            Format fileFormat = new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI);
            
            // Video format: AVI Techsmith codec (browser-friendly, good quality)
            Format screenFormat = new Format(MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,  // Techsmith codec - tốt cho screen recording
                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    DepthKey, 24,           // 24-bit color depth
                    FrameRateKey, Rational.valueOf(15),  // 15 FPS (đủ cho automation, file nhẹ)
                    QualityKey, 1.0f,       // Quality 100%
                    KeyFrameIntervalKey, 15 * 60);  // Keyframe mỗi 60 giây
            
            // Mouse cursor format: black cursor
            Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO,
                    EncodingKey, "black",
                    FrameRateKey, Rational.valueOf(30));

            // 4. Khởi tạo và start recorder
            recorder = new VideoRecorder(gc, null, fileFormat, screenFormat, mouseFormat, null, dir, testName);
            recorder.start();

            System.out.println("🎥 Video recording started: " + testName + "_<timestamp>.avi");

        } catch (Exception e) {
            // Không throw exception để tránh ảnh hưởng test
            System.err.println("❌ Cannot start video recording: " + e.getMessage());
        }
    }

    /**
     * Stop video recording và quyết định giữ hay xoá video
     * 
     * @param keepVideo boolean
     *        - true: Giữ video (test fail)
     *        - false: Xoá video (test pass)
     */
    public static void stopRecording(boolean keepVideo) {
        try {
            if (recorder != null) {
                // 1. Stop recorder
                recorder.stop();

                // 2. Lưu video path TRƯỚC KHI xoá hoặc reset recorder
                List<File> videoFiles = recorder.getCreatedMovieFiles();
                if (!videoFiles.isEmpty()) {
                    lastVideoPath = videoFiles.get(0).getAbsolutePath();
                }

                // 3. Xoá video nếu test pass
                if (!keepVideo) {
                    for (File file : videoFiles) {
                        if (file.exists() && file.delete()) {
                            System.out.println("🗑️ Video deleted (test passed): " + file.getName());
                            lastVideoPath = null;  // Clear path vì đã xoá
                        }
                    }
                } else {
                    // Test FAILED - Giữ video và convert sang MP4 để play trong browser
                    for (File file : videoFiles) {
                        System.out.println("💾 Video saved (test failed): " + file.getAbsolutePath());
                        
                        // Convert AVI → MP4 để browser có thể play
                        String mp4Path = convertToMP4(file.getAbsolutePath());
                        if (mp4Path != null) {
                            // Xóa file AVI gốc để tiết kiệm disk space
                            file.delete();
                            // Update path sang file MP4
                            lastVideoPath = mp4Path;
                            System.out.println("🎬 Video converted to MP4: " + mp4Path);
                        }
                    }
                }

                // 4. Reset recorder
                recorder = null;
            }

        } catch (Exception e) {
            System.err.println("❌ Cannot stop video recording: " + e.getMessage());
        }
    }

    /**
     * Get đường dẫn của video file (nếu có)
     * 
     * @return String - Absolute path của video file, hoặc null nếu không có
     */
    public static String getVideoPath() {
        return lastVideoPath;
    }

    /**
     * Convert AVI video sang MP4 format để browser có thể play
     * Sử dụng FFmpeg để conversion
     * 
     * @param aviFilePath String - Path của file AVI cần convert
     * @return String - Path của file MP4 output, hoặc null nếu conversion failed
     */
    private static String convertToMP4(String aviFilePath) {
        try {
            // 1. Get FFmpeg path (auto-download nếu chưa có)
            String ffmpegPath = FFmpegInstaller.getFFmpegPath();
            if (ffmpegPath == null) {
                System.err.println("⚠️ FFmpeg not available. Cannot convert video to MP4.");
                System.err.println("   Video will remain in AVI format (may not play in browser)");
                return null;
            }

            // 2. Tạo output path: thay .avi thành .mp4
            String mp4FilePath = aviFilePath.replace(".avi", ".mp4");

            // 3. Build FFmpeg command cho browser-compatible MP4
            // Sử dụng baseline profile để maximum compatibility
            ProcessBuilder pb = new ProcessBuilder(
                ffmpegPath,
                "-i", aviFilePath,                    // Input AVI file
                "-c:v", "libx264",                    // H.264 codec
                "-profile:v", "baseline",             // Baseline profile (max compatibility)
                "-level", "3.0",                      // H.264 level 3.0
                "-pix_fmt", "yuv420p",                // Pixel format (required for compatibility)
                "-preset", "medium",                  // Encoding speed/quality balance
                "-crf", "23",                         // Constant quality (18=best, 28=worst)
                "-movflags", "+faststart",            // Enable progressive streaming
                "-vf", "scale=trunc(iw/2)*2:trunc(ih/2)*2", // Ensure even dimensions
                "-an",                                // No audio stream
                "-y",                                 // Overwrite output file
                mp4FilePath                           // Output MP4 file
            );

            // 4. Capture output để debug nếu có lỗi
            pb.redirectErrorStream(true);
            File logFile = new File(VIDEO_FOLDER + "/ffmpeg_last.log");
            pb.redirectOutput(ProcessBuilder.Redirect.to(logFile));

            // 5. Run FFmpeg
            System.out.println("🔄 Converting video to MP4 format...");
            Process process = pb.start();
            
            // Wait for conversion to complete (timeout 30 seconds)
            boolean finished = process.waitFor(30, java.util.concurrent.TimeUnit.SECONDS);
            
            if (!finished) {
                process.destroyForcibly();
                System.err.println("❌ Video conversion timeout (>30s)");
                return null;
            }

            int exitCode = process.exitValue();
            if (exitCode == 0) {
                // Verify MP4 file created
                File mp4File = new File(mp4FilePath);
                if (mp4File.exists() && mp4File.length() > 0) {
                    System.out.println("✅ MP4 size: " + String.format("%.2f", mp4File.length() / 1024.0) + " KB");
                    return mp4FilePath;
                } else {
                    System.err.println("❌ MP4 file not created or is empty");
                    if (logFile.exists()) {
                        System.err.println("   Check log: " + logFile.getAbsolutePath());
                    }
                    return null;
                }
            } else {
                System.err.println("❌ FFmpeg conversion failed with exit code: " + exitCode);
                if (logFile.exists()) {
                    System.err.println("   Check FFmpeg log: " + logFile.getAbsolutePath());
                }
                return null;
            }

        } catch (Exception e) {
            System.err.println("❌ Cannot convert video to MP4: " + e.getMessage());
            return null;
        }
    }
}
