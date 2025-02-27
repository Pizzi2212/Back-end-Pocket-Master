package user;

import auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(u -> passwordEncoder.matches(password, u.getPassword())).orElse(false);
    }
  }

