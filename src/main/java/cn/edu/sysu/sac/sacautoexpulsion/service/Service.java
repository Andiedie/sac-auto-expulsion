package cn.edu.sysu.sac.sacautoexpulsion.service;

import cn.edu.sysu.sac.sacautoexpulsion.entity.BadRequest;
import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Date;

@org.springframework.stereotype.Service
public class Service {
    static {
        PlatformImpl.startup(() -> {});
    }
    private MediaPlayer mp;
    private PlayerStatus status = new PlayerStatus();

    @Value("${cn.edu.sysu.sac.sacautoexpulsion.defaultPath}")
    private String defaultPath;

    public void start(MusicProfile profile) throws BadRequest {
        stop();
        Media hit = null;
        try {
            hit = new Media(new ClassPathResource(defaultPath).getFile().toURI().toString());
        } catch (Exception e) {
            throw new BadRequest("找不到指定的文件。");
        }
        mp = new MediaPlayer(hit);
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
