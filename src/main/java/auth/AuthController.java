package auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import request.LoginRequest;
import request.RegisterRequest;
import user.UserRepository;
import user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint POST per registrare un nuovo utente
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            // Qui gestiamo la logica di registrazione
            userService.registerUser(request);
            return ResponseEntity.ok("Utente registrato con successo!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore durante la registrazione: " + e.getMessage());
        }
    }

    /**
     * Endpoint POST per effettuare il login
     * Ritorna un token (JWT o altro) se le credenziali sono valide.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {

            String token = userService.loginUser(request);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenziali non valide: " + e.getMessage());
        }
    }
}
