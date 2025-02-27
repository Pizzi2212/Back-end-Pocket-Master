package user;

import auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import request.LoginRequest;
import request.RegisterRequest;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Registra un nuovo utente nel sistema.
     */
    public void registerUser(RegisterRequest request) {
        // Controlliamo se username o email sono già in uso
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Errore: Username già in uso!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Errore: Email già in uso!");
        }

        // Creiamo il nuovo utente e salviamo nel database
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Hash della password

        userRepository.save(newUser);
    }

    /**
     * Effettua il login e restituisce un token JWT se le credenziali sono corrette.
     */
    public String loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Se l'autenticazione va a buon fine, generiamo il token JWT
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateJwtToken(userDetails.getUsername());
    }

    /**
     * Restituisce la lista di tutti gli utenti registrati.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Restituisce un utente dato il suo ID.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Errore: Utente non trovato con ID: " + id));
    }

    /**
     * Aggiorna le informazioni di un utente.
     */
    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);

        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());

        // Se la password è stata aggiornata, la criptiamo prima di salvarla
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    /**
     * Cancella un utente dato il suo ID.
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Errore: Utente non trovato con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

