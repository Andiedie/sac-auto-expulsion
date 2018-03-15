package cn.edu.sysu.sac.sacautoexpulsion.entity;

import java.util.Date;

public class PlayerStatus {
    private boolean isPlaying = false;
    private Date startTime = null;
    private int maxtime = 0;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(int maxtime) {
        this.maxtime = maxtime;
    }
}
