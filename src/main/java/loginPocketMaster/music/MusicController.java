package loginPocketMaster.music;

import loginPocketMaster.faq.Faq;
import loginPocketMaster.faq.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/music")
@CrossOrigin(origins = "localhost:3000")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @GetMapping
    public List<Music> getAllMusics() {
        return musicService.getAllMusics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> getMusicById(@PathVariable Long id) {
        Optional<Music> faq = musicService.getMusicById(id);
        return faq.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Music createMusic(@RequestBody Music music) {
        return musicService.createMusic(music);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id) {
        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }
}
