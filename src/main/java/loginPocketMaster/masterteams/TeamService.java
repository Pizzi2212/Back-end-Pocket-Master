package loginPocketMaster.masterteams;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final RestTemplate restTemplate;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.restTemplate = new RestTemplate();
    }


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }


    public Map<String, Object> getTeamWithPokemon(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team non trovato"));

        // Chiamate alla PokéAPI per ottenere i dettagli di ogni Pokémon
        List<Map<String, Object>> pokemons = team.getPokemonNames().stream()
                .map(this::fetchPokemonFromApi)
                .collect(Collectors.toList());

        return Map.of(
                "teamName", team.getName(),
                "pokemons", pokemons,
                "description", team.getDescription()
        );
    }

    private Map<String, Object> fetchPokemonFromApi(String name) {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + name.toLowerCase();
        return restTemplate.getForObject(apiUrl, Map.class);
    }
}
