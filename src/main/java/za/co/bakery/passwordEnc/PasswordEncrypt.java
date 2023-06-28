package za.co.bakery.passwordEnc;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypt {
    
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
     public static void main(String[] args) {
        PasswordEncrypt pe = new PasswordEncrypt();
         String pass = "12345";
        pass=  pe.encryptPassword(pass);
      
         System.out.println(pass);
         
         System.out.println(pe.checkPassword("12345",pass));
         
    }
}
