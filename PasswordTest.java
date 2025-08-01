import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Contraseñas que queremos verificar
        String[] passwords = {"admin123", "password123", "manager123", "sales123"};
        
        // Hashes de la base de datos
        String adminHash = "$2a$10$8EblcKoHONzjLlCJJgb2fOiYnKNJN7cFq5CJG2VVt7LCBDjQUOOFC";
        String otherHash = "$2a$10$rOe6BsOlKhIgr7uL5Jnc6u5dV4X9ZOkpJl8r7/4o6RzPqZ8zIRHDK";
        
        System.out.println("=== VERIFICANDO HASHES DE CONTRASEÑAS ===");
        System.out.println();
        
        System.out.println("Hash del admin: " + adminHash);
        for (String pwd : passwords) {
            boolean matches = encoder.matches(pwd, adminHash);
            System.out.println("  '" + pwd + "' -> " + (matches ? "COINCIDE" : "NO COINCIDE"));
        }
        
        System.out.println();
        System.out.println("Hash de otros usuarios: " + otherHash);
        for (String pwd : passwords) {
            boolean matches = encoder.matches(pwd, otherHash);
            System.out.println("  '" + pwd + "' -> " + (matches ? "COINCIDE" : "NO COINCIDE"));
        }
        
        System.out.println();
        System.out.println("=== GENERANDO NUEVOS HASHES ===");
        for (String pwd : passwords) {
            String newHash = encoder.encode(pwd);
            System.out.println("'" + pwd + "' -> " + newHash);
            
            // Verificar que el nuevo hash funciona
            boolean verification = encoder.matches(pwd, newHash);
            System.out.println("  Verificacion: " + (verification ? "OK" : "ERROR"));
            System.out.println();
        }
    }
}