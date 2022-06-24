import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassHelperTest {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String encode = passwordEncoder.encode("admin");
        System.out.println(encode); // $2a$10$dnZyKi7q1v6GAkXm/3l0tOXvIhQ/P/B7IVX20blgEXpbGsjaX/y42

    }
}
