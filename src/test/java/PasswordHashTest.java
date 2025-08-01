import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Hashes de la base de datos
        String adminHash = "$2a$10$8EblcKoHONzjLlCJJgb2fOiYnKNJN7cFq5CJG2VVt7LCBDjQUOOFC";
        String otherHash = "$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK";
        
        System.out.println("=== VERIFICANDO CONTRASEÑAS ===");
        
        // Test admin hash
        System.out.println("\nAdmin hash:");
        System.out.println("admin123: " + encoder.matches("admin123", adminHash));
        System.out.println("password123: " + encoder.matches("password123", adminHash));
        
        // Test other users hash  
        System.out.println("\nOther users hash:");
        System.out.println("password123: " + encoder.matches("password123", otherHash));
        System.out.println("admin123: " + encoder.matches("admin123", otherHash));
        System.out.println("manager123: " + encoder.matches("manager123", otherHash));
        
        // Generate correct hashes
        System.out.println("\n=== NUEVOS HASHES ===");
        System.out.println("admin123: " + encoder.encode("admin123"));
        System.out.println("password123: " + encoder.encode("password123"));
    }
}