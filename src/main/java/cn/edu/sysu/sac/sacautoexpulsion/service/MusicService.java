package cn.edu.sysu.sac.sacautoexpulsion.service;

import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.util.Date;

@Service
public class MusicService {
    public MusicService() {
        Resource resource = new ClassPathResource("static/default.wav");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(resource.getInputStream()));
            player = AudioSystem.getClip();
            player.open(stream);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private Clip player;
    private PlayerStatus status = new PlayerStatus();

    public void start(MusicProfile profile) {
        stop();
        player.loop(Clip.LOOP_CONTINUOUSLY);
        status.setStartTime(new Date());
        status.setPlaying(true);
        status.setMaxtime(profile.getMaxtime());
        new Thread(() -> {
            try {
                Thread.sleep(profile.getMaxtime());
                stop();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    public void stop() {
        if (status.isPlaying()) {
            player.stop();
            player.flush();
            player.setMicrosecondPosition(0);
            status.setStartTime(null);
            status.setPlaying(false);
            status.setMaxtime(0);
        }
    }

    public PlayerStatus status() {
        return status;
    }
}
