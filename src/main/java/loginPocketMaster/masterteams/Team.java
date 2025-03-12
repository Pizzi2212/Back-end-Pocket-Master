package loginPocketMaster.masterteams;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "team_pokemon_names", joinColumns = @JoinColumn(name = "team_id"))
    @MapKeyColumn(name = "pokemon_names")
    @Column(name = "description", columnDefinition = "TEXT")
    private Map<String, String> pokemonNames;
}
