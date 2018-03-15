package cn.edu.sysu.sac.sacautoexpulsion.service;

import cn.edu.sysu.sac.sacautoexpulsion.entity.BadRequest;
import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@org.springframework.stereotype.Service
public class Service {
    @Value("${cn.edu.sysu.sac.sacautoexpulsion.defaultPath}")
    private void prepare(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        PlatformImpl.startup(() -> {});
        Path mp3 = Files.createTempFile("now-playing", ".mp3");
        Files.copy(resource.getInputStream(), mp3, StandardCopyOption.REPLACE_EXISTING);
        media = new Media(mp3.toFile().toURI().toString());
    }

    private MediaPlayer mp;
    private PlayerStatus status = new PlayerStatus();

    private Media media;

    public void start(MusicProfile profile) throws BadRequest {
        stop();
        mp = new MediaPlayer(media);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();
        status.setStartTime(new Date());
        status.setPlaying(true);
        status.setMaxtime(profile.getMaxtime());
        new Thread(() -> {
            try {
                Thread.sleep(profile.getMaxtime());
                stop();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            status.setStartTime(null);
            status.setPlaying(false);
            status.setMaxtime(0);
        }
    }

    public PlayerStatus status() {
        return status;
    }
}
