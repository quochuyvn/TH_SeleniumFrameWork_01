package utils;

import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ====================================================
 * VideoRecorder - Custom Screen Recorder
 * ====================================================
 * 
 * Mục đích:
 * - Extend ScreenRecorder của Monte Media Library
 * - Tạo file video với tên custom (theo test name + timestamp)
 * - Cấu hình format video (MP4, 15 FPS, quality)
 * 
 * Sử dụng:
 * - Được gọi bởi VideoManager
 * - Không gọi trực tiếp từ test case
 * 
 * @author Framework Team
 * @version 3.0 - MP4 format with timestamp
 * @since 2026-01-25
 */
public class VideoRecorder extends ScreenRecorder {

    private String testName;
    private String timestamp;

    /**
     * Constructor - Khởi tạo screen recorder với cấu hình enterprise
     * 
     * @param cfg GraphicsConfiguration - Cấu hình màn hình
     * @param captureArea Rectangle - Vùng màn hình cần record (null = toàn màn hình)
     * @param fileFormat Format - Format file output
     * @param screenFormat Format - Format video
     * @param mouseFormat Format - Format mouse cursor
     * @param audioFormat Format - Format audio (null = không record audio)
     * @param movieFolder File - Thư mục lưu video
     * @param testName String - Tên test case
     * @throws Exception nếu không thể khởi tạo recorder
     */
    public VideoRecorder(GraphicsConfiguration cfg, 
                         Rectangle captureArea,
                         Format fileFormat,
                         Format screenFormat, 
                         Format mouseFormat,
                         Format audioFormat,
                         File movieFolder,
                         String testName) throws Exception {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.testName = testName;
        // Tạo timestamp giống screenshot: yyyyMMdd_HHmmss
        this.timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    /**
     * Override createMovieFile - Tạo file video với tên custom + timestamp
     * 
     * Format: <testName>_<timestamp>.avi (sau đó convert sang MP4 bởi FFmpeg)
     * Ví dụ: TC001_LoginSuccessfully_Test_20260125_150816.avi
     * 
     * @param fileFormat Format - Format của file (đã set ở constructor)
     * @return File - File video đầu ra
     */
    @Override
    protected File createMovieFile(Format fileFormat) throws java.io.IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new java.io.IOException("\"" + movieFolder + "\" is not a directory.");
        }
        // Tạo tên file với timestamp: testName_yyyyMMdd_HHmmss.avi
        return new File(movieFolder, testName + "_" + timestamp + ".avi");
    }
}
