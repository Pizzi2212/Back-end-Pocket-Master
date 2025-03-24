package loginPocketMaster.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicService {
    @Autowired
    private MusicRepository faqRepository;

    public List<Music> getAllMusics() {
        return faqRepository.findAll();
    }

    public Optional<Music> getMusicById(Long id) {
        return faqRepository.findById(id);
    }

    public Music createMusic(Music music) {
        return faqRepository.save(music);
    }

    public void deleteMusic(Long id) {
        faqRepository.deleteById(id);
    }
}
