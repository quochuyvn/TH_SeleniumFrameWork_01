package utilities;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/*
 * ***** Mục tiêu của RandomDataUtil *****
	❌ Không dùng API deprecated (RandomStringUtils)
	✅ Dùng UUID + SecureRandom
	✅ Dùng được cho:
		username
		email
		password
		số ngẫu nhiên
		timestamp
	✅ Static util → gọi ở đâu cũng được	
	✅ Dễ maintain & mở rộng
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

    // Ngăn không cho new Util class
    private RandomDataUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    // =========================
    // UUID
    // =========================
    
    /**
     * Sinh UUID đầy đủ (36 ký tự, có dấu '-')
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Sinh UUID không có dấu '-'
     * Ví dụ: 32 ký tự
     */
    public static String uuidNoDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Sinh UUID rút gọn
     * @param length độ dài mong muốn
     */
    public static String shortUUID(int length) {
        if (length <= 0 || length > 32) {
            throw new IllegalArgumentException("Length must be between 1 and 32");
        }
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, length);
    }

    // =========================
    // Random String
    // =========================
    
    /**
     * Random chuỗi chữ thường (a-z)
     */
    public static String randomLowerCase(int length) {
        return randomFromSource(LOWER, length);
    }

    /**
     * Random chuỗi chữ hoa (A-Z)
     */
    public static String randomUpperCase(int length) {
        return randomFromSource(UPPER, length);
    }
    
    /**
     * Random chuỗi chữ hoa (A-Z) chữ thường (a-z)
     */
    public static String randomAlphabetic(int length) {
        return randomFromSource(ALPHA, length);
    }

    /**
     * Random chuỗi số
     */
    public static String randomNumber(int length) {
        return randomFromSource(DIGITS, length);
    }
    
    public static String randomAlphaNumeric(int length) {
        return randomFromSource(ALPHA_NUMERIC, length);
    }

    public static String randomAll(int length) {
        return randomFromSource(ALL, length);
    }

    // =========================
    // Email / Username
    // =========================
    public static String randomEmail() {
        return "user_" + shortUUID(6) + "@test.com";
    }

    public static String randomUsername() {
        return "auto_" + randomAlphabetic(5) + shortUUID(3);
    }
    
    // ==========================
    // First Name / Last Name
    // ==========================

    /**
     * Random First Name (English)
     */
    public static String randomFirstName() {
        String[] firstNames = {
                "John", "David", "Michael", "James", "Robert",
                "Anna", "Emma", "Olivia", "Sophia", "Linda"
        };
        return firstNames[secureRandom.nextInt(firstNames.length)];
    }

    /**
     * Random Last Name (English)
     */
    public static String randomLastName() {
        String[] lastNames = {
                "Smith", "Johnson", "Brown", "Taylor", "Anderson",
                "Thomas", "Jackson", "White", "Harris", "Martin"
        };
        return lastNames[secureRandom.nextInt(lastNames.length)];
    }

    /**
     * Random Full Name
     */
    public static String randomFullName() {
        return randomFirstName() + " " + randomLastName();
    }

    // =========================
    // Password
    // =========================
    
    /**
     * Random password gồm:
     * - chữ thường
     * - chữ hoa
     * - số
     * - ký tự đặc biệt
     */
    
    public static String randomPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be >= 8");
        }

        StringBuilder sb = new StringBuilder(length);

        sb.append(randomChar(LOWER));
        sb.append(randomChar(UPPER));
        sb.append(randomChar(DIGITS));
        sb.append(randomChar(SPECIAL));

        for (int i = 4; i < length; i++) {
            sb.append(randomChar(ALL));
        }

        return shuffle(sb.toString());
    }

    // =========================
    // Number
    // =========================
    public static int randomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min must be < Max");
        }
        return secureRandom.nextInt(max - min + 1) + min;
    }
    
 // ==========================
    // 4️⃣ Telephone
    // ==========================

    /**
     * Random số điện thoại Việt Nam (10 số)
     * Ví dụ: 03xxxxxxxx, 09xxxxxxxx
     */
    public static String randomVietnamPhone() {
        String[] prefixes = {"03", "05", "07", "08", "09"};
        String prefix = prefixes[secureRandom.nextInt(prefixes.length)];
        return prefix + randomNumber(8);
    }

    // =========================
    // Timestamp
    // =========================
    public static String timestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    // =========================
    // PRIVATE METHODS
    // =========================
    private static String randomFromSource(String source, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(
                    secureRandom.nextInt(source.length())));
        }
        return sb.toString();
    }

    private static char randomChar(String source) {
        return source.charAt(
                secureRandom.nextInt(source.length()));
    }
    
    // ==========================
    // Helper methods
    // ==========================

    /**
     * Random chuỗi từ danh sách ký tự cho trước
     */
//    private static String randomFromChars(String chars, int length) {
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append(chars.charAt(secureRandom.nextInt(chars.length())));
//        }
//        return sb.toString();
//    }

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

