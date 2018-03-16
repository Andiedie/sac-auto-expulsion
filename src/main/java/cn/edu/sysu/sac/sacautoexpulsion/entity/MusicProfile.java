package cn.edu.sysu.sac.sacautoexpulsion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MusicProfile {
    private static int defaultMaxTime;

    private int maxtime = -1;

    @Value("${cn.edu.sysu.sac.sacautoexpulsion.defaultMaxTime}")
    private void setDefaultMaxTime(int defaultMaxTime) {
        MusicProfile.defaultMaxTime = defaultMaxTime;
    }

    public int getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(int maxtime) {
        this.maxtime = maxtime;
    }

    @Autowired
    public MusicProfile() {
        maxtime = defaultMaxTime;
    }
}
