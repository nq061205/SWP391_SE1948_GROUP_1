package helper;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {

    public static String encryptPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            return null;
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
     public static void main(String[] args) {
         System.out.println(encryptPassword("Duy"));
    }
}
