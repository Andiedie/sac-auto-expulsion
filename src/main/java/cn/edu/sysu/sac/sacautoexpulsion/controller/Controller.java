package cn.edu.sysu.sac.sacautoexpulsion.controller;

import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import cn.edu.sysu.sac.sacautoexpulsion.entity.Response;
import cn.edu.sysu.sac.sacautoexpulsion.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private MusicService service;

    @Autowired
    public Controller(MusicService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public Response start(@RequestBody @Nullable MusicProfile profile) {
        if (profile == null) profile = new MusicProfile();
        service.start(profile);
        return new Response();
    }

    @PostMapping("/stop")
    public Response stop() {
        service.stop();
        return new Response();
    }

    @GetMapping("/status")
    public Response<PlayerStatus> status() {
        return new Response<>("", service.status());
    }
}
