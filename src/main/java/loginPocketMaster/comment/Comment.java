package loginPocketMaster.comment;

import jakarta.persistence.*;
import loginPocketMaster.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String teamName;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment(User user,String teamName, String content) {
        this.user = user;
        this.teamName = teamName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}