package utils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ====================================================
 * FFmpegInstaller - Auto-install FFmpeg Portable
 * ====================================================
 * 
 * Mục đích:
 * - Tự động download và extract FFmpeg portable version
 * - Check xem FFmpeg đã tồn tại chưa
 * - Trả về path của ffmpeg.exe
 * 
 * Lợi ích:
 * - Không cần user cài FFmpeg thủ công
 * - Framework tự động setup dependencies
 * - Portable, không ảnh hưởng system PATH
 * 
 * @author Framework Team
 * @version 1.0
 * @since 2026-01-25
 */
public class FFmpegInstaller {

    private static final String FFMPEG_FOLDER = "src/main/resources/ffmpeg";
    private static final String FFMPEG_EXE = FFMPEG_FOLDER + "/bin/ffmpeg.exe";
    
    // FFmpeg essentials build (smaller size, ~70MB)
    private static final String FFMPEG_DOWNLOAD_URL = 
        "https://www.gyan.dev/ffmpeg/builds/ffmpeg-release-essentials.zip";

    /**
     * Get FFmpeg executable path
     * Tự động download nếu chưa có
     * 
     * @return String - Absolute path to ffmpeg.exe
     */
    public static String getFFmpegPath() {
        File ffmpegExe = new File(FFMPEG_EXE);
        
        // Nếu đã có FFmpeg, return path
        if (ffmpegExe.exists()) {
            return ffmpegExe.getAbsolutePath();
        }
        
        // Nếu chưa có, tự động download và extract
        try {
            System.out.println("📦 FFmpeg not found. Downloading portable version...");
            downloadAndExtract();
            
            // Verify lại sau khi download
            if (ffmpegExe.exists()) {
                System.out.println("✅ FFmpeg installed successfully: " + ffmpegExe.getAbsolutePath());
                return ffmpegExe.getAbsolutePath();
            } else {
                System.err.println("❌ FFmpeg installation failed");
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Cannot download FFmpeg: " + e.getMessage());
            return null;
        }
    }

    /**
     * Download và extract FFmpeg portable
     */
    private static void downloadAndExtract() throws Exception {
        // 1. Tạo folder
        File folder = new File(FFMPEG_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 2. Download zip file
        File zipFile = new File(FFMPEG_FOLDER + "/ffmpeg.zip");
        System.out.println("📥 Downloading FFmpeg from: " + FFMPEG_DOWNLOAD_URL);
        System.out.println("⏳ This may take a few minutes (~70MB)...");
        
        // Setup connection with timeout
        URL url = URI.create(FFMPEG_DOWNLOAD_URL).toURL();
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(15000);  // 15 seconds to connect
        connection.setReadTimeout(30000);     // 30 seconds to read data
        
        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(zipFile)) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytes = 0;
            
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
                
                // Show progress every 5MB
                if (totalBytes % (5 * 1024 * 1024) == 0) {
                    System.out.println("   Downloaded: " + (totalBytes / 1024 / 1024) + " MB");
                }
            }
            
            System.out.println("✅ Download completed: " + (totalBytes / 1024 / 1024) + " MB");
        }

        // 3. Extract zip file
        System.out.println("📂 Extracting FFmpeg...");
        extractZip(zipFile, folder);
        
        // 4. Tìm và move ffmpeg.exe ra ngoài
        moveFfmpegBinary(folder);
        
        // 5. Xóa zip file để tiết kiệm disk space
        zipFile.delete();
        System.out.println("🧹 Cleaned up temporary files");
    }

    /**
     * Extract ZIP file
     */
    private static void extractZip(File zipFile, File destFolder) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            byte[] buffer = new byte[8192];
            
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destFolder, entry.getName());
                
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // Tạo parent directories nếu cần
                    new File(newFile.getParent()).mkdirs();
                    
                    // Extract file
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    /**
     * Tìm ffmpeg.exe trong extracted folder và move ra bin/
     */
    private static void moveFfmpegBinary(File folder) throws Exception {
        // FFmpeg zip có structure: ffmpeg-x.x.x-essentials_build/bin/ffmpeg.exe
        // Mình cần tìm và move ra src/main/resources/ffmpeg/bin/
        
        File[] subfolders = folder.listFiles(File::isDirectory);
        if (subfolders != null) {
            for (File subfolder : subfolders) {
                File binFolder = new File(subfolder, "bin");
                if (binFolder.exists()) {
                    File ffmpegExe = new File(binFolder, "ffmpeg.exe");
                    if (ffmpegExe.exists()) {
                        // Move bin folder ra ngoài
                        File destBinFolder = new File(FFMPEG_FOLDER + "/bin");
                        if (!destBinFolder.exists()) {
                            destBinFolder.mkdirs();
                        }
                        
                        // Copy ffmpeg.exe
                        Files.copy(ffmpegExe.toPath(), 
                                  Paths.get(FFMPEG_FOLDER + "/bin/ffmpeg.exe"),
                                  java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        
                        System.out.println("✅ FFmpeg binary extracted successfully");
                        return;
                    }
                }
            }
        }
        
        throw new Exception("Cannot find ffmpeg.exe in downloaded package");
    }
}
