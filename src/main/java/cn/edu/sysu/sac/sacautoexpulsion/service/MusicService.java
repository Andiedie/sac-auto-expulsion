package cn.edu.sysu.sac.sacautoexpulsion.service;

import cn.edu.sysu.sac.sacautoexpulsion.entity.BadRequest;
import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
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


    public void test() throws BadRequest {
        if (status.isPlaying()) throw new BadRequest("催场音乐正在播放，无法启动测试");
        Resource resource = new ClassPathResource("static/test.wav");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(resource.getInputStream()));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }
    }

    private static final String NOTES_PATH = "/home/sys/notes/data.txt";
//    private static final String NOTES_PATH = "D:/download/data.txt";

    public void putData(String data) throws BadRequest {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(NOTES_PATH), StandardCharsets.UTF_8);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }
    }

    public String getData() throws BadRequest {
        try {
            File file = new File(NOTES_PATH);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, "UTF-8");
            return str;
        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }
    }
}
