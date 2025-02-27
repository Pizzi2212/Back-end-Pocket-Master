package loginPocketMaster.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PocketMasterUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @Column(nullable = false, unique = true)
    private  String username;

    @Column(nullable = false , unique = true)
    private  String email;

    @Column(nullable = false)
    private  String password;
}