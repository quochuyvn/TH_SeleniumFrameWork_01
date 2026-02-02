package utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/*
 * ***** RandomDataUtil *****
 * Update: 14/01/2026
 * * Mục tiêu:
 * ✅ Dùng UUID + SecureRandom (An toàn hơn Random thường)
 * ✅ Hỗ trợ sinh data test: username, email, password, phone, timestamp
 * ✅ Static utility class -> gọi trực tiếp không cần khởi tạo
 * ✅ [UPDATE] Email dùng domain an toàn (@example.com)
 * ✅ [UPDATE] Timestamp hỗ trợ format tùy chỉnh
 * ✅ [UPDATE] Số điện thoại Việt Nam chuẩn các đầu số nhà mạng
 */

public final class RandomDataUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*";

    private static final String ALPHA = LOWER + UPPER;
    private static final String ALPHA_NUMERIC = ALPHA + DIGITS;
    private static final String ALL = ALPHA_NUMERIC + SPECIAL;

    // Ngăn không cho khởi tạo class
    private RandomDataUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ==================================================
    // 1. UUID GENERATION
    // ==================================================

    /**
     * Sinh UUID đầy đủ (36 ký tự, có dấu gạch ngang)
     * Ex: 550e8400-e29b-41d4-a716-446655440000
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Sinh UUID không có dấu gạch ngang (32 ký tự)
     * Ex: 550e8400e29b41d4a716446655440000
     */
    public static String uuidNoDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Sinh UUID rút gọn (cắt chuỗi)
     * @param length độ dài mong muốn (max 32)
     */
    public static String shortUUID(int length) {
        if (length <= 0 || length > 32) {
            throw new IllegalArgumentException("Length must be between 1 and 32");
        }
        return uuidNoDash().substring(0, length);
    }

    // ==================================================
    // 2. RANDOM STRINGS
    // ==================================================

    public static String randomLowerCase(int length) {
        return randomFromSource(LOWER, length);
    }

    public static String randomUpperCase(int length) {
        return randomFromSource(UPPER, length);
    }

    public static String randomAlphabetic(int length) {
        return randomFromSource(ALPHA, length);
    }

    public static String randomNumber(int length) {
        return randomFromSource(DIGITS, length);
    }

    public static String randomAlphaNumeric(int length) {
        return randomFromSource(ALPHA_NUMERIC, length);
    }

    public static String randomAll(int length) {
        return randomFromSource(ALL, length);
    }

    // ==================================================
    // 3. EMAIL & USERNAME (UPDATED)
    // ==================================================

    /**
     * [UPDATE] Random Email mặc định domain @example.com
     * Lý do: Tránh gửi nhầm vào domain thật (@test.com có tồn tại)
     */
    public static String randomEmail() {
        return randomEmail("example.com");
    }

    /**
     * [NEW] Random Email với domain tùy chỉnh
     * Ex: randomEmail("myproject.vn")
     */
    public static String randomEmail(String domain) {
        return "user_" + shortUUID(6) + "@" + domain;
    }

    public static String randomUsername() {
        return "auto_" + randomAlphabetic(5) + shortUUID(3);
    }

    // ==================================================
    // 4. FULL NAME
    // ==================================================

    public static String randomFirstName() {
        String[] firstNames = {
            "John", "David", "Michael", "James", "Robert",
            "Anna", "Emma", "Olivia", "Sophia", "Linda",
            "William", "Lucas", "Mason", "Ethan" // [UPDATE] Added more names
        };
        return firstNames[secureRandom.nextInt(firstNames.length)];
    }

    public static String randomLastName() {
        String[] lastNames = {
            "Smith", "Johnson", "Brown", "Taylor", "Anderson",
            "Thomas", "Jackson", "White", "Harris", "Martin",
            "Wilson", "Moore", "Clark", "Lewis" // [UPDATE] Added more names
        };
        return lastNames[secureRandom.nextInt(lastNames.length)];
    }

    public static String randomFullName() {
        return randomFirstName() + " " + randomLastName();
    }

    // ==================================================
    // 5. PASSWORD
    // ==================================================

    /**
     * Tạo password mạnh (Chữ hoa, thường, số, ký tự đặc biệt)
     */
    public static String randomPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be >= 8");
        }
        StringBuilder sb = new StringBuilder(length);
        
        // Đảm bảo có ít nhất 1 ký tự cho mỗi loại
        sb.append(randomChar(LOWER));
        sb.append(randomChar(UPPER));
        sb.append(randomChar(DIGITS));
        sb.append(randomChar(SPECIAL));

        // Điền nốt các ký tự còn lại
        for (int i = 4; i < length; i++) {
            sb.append(randomChar(ALL));
        }

        return shuffle(sb.toString());
    }

    // ==================================================
    // 6. NUMBERS & PHONE (UPDATED)
    // ==================================================

    /**
     * Random số integer trong khoảng [min, max]
     */
    public static int randomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min must be < Max");
        }
        return secureRandom.nextInt(max - min + 1) + min;
    }

    /**
     * [UPDATE] Random số điện thoại Việt Nam (10 số)
     * Cập nhật danh sách đầu số thực tế 3 chữ số
     */
    public static String randomVietnamPhone() {
        String[] prefixes = {
            "032", "033", "034", "035", "036", "037", "038", "039", // Viettel
            "070", "079", "077", "076", "078",                      // MobiFone
            "083", "084", "085", "081", "082",                      // VinaPhone
            "056", "058", "092"                                     // Vietnamobile
        };
        
        String prefix = prefixes[secureRandom.nextInt(prefixes.length)];
        // Prefix có 3 số, cần thêm 7 số nữa -> Tổng 10 số
        return prefix + randomNumber(7);
    }

    // ==================================================
    // 7. TIMESTAMP (UPDATED)
    // ==================================================

    /**
     * [UPDATE] Lấy timestamp mặc định (dùng cho screenshot, tên file)
     * Format: yyyyMMdd_HHmmss
     */
    public static String timestamp() {
        return timestamp("yyyyMMdd_HHmmss");
    }

    /**
     * [NEW] Lấy timestamp với format tùy chỉnh
     * Ex: timestamp("dd-MM-yyyy") -> 14-01-2026
     */
    public static String timestamp(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    // ==================================================
    // PRIVATE HELPER METHODS
    // ==================================================

    private static String randomFromSource(String source, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(secureRandom.nextInt(source.length())));
        }
        return sb.toString();
    }

    private static char randomChar(String source) {
        return source.charAt(secureRandom.nextInt(source.length()));
    }

    /**
     * Xáo trộn chuỗi để đảm bảo ký tự bắt buộc không nằm cố định ở đầu
     */
    private static String shuffle(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = secureRandom.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}