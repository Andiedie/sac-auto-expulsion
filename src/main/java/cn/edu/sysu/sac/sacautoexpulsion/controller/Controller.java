package cn.edu.sysu.sac.sacautoexpulsion.controller;

import cn.edu.sysu.sac.sacautoexpulsion.entity.BadRequest;
import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import cn.edu.sysu.sac.sacautoexpulsion.entity.Response;
import cn.edu.sysu.sac.sacautoexpulsion.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/start")
    public Response start(@RequestBody @Nullable MusicProfile profile) throws BadRequest {
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
