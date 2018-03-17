package cn.edu.sysu.sac.sacautoexpulsion.controller;

import cn.edu.sysu.sac.sacautoexpulsion.entity.BadRequest;
import cn.edu.sysu.sac.sacautoexpulsion.entity.MusicProfile;
import cn.edu.sysu.sac.sacautoexpulsion.entity.PlayerStatus;
import cn.edu.sysu.sac.sacautoexpulsion.entity.Response;
import cn.edu.sysu.sac.sacautoexpulsion.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/test")
    public Response test() throws BadRequest {
        service.test();
        return new Response();
    }

    // These fucking functions are for gay gray
    // http://expulsion.work.sac/gayPut
    @PostMapping("/gayPut")
    @CrossOrigin
    public Response putData(@RequestBody @Nullable String data) throws BadRequest {
        if (data == null) data = "";
        service.putData(data);
        return new Response();
    }

    @GetMapping("/gayGet")
    @CrossOrigin
    public Response<String> getData() throws BadRequest {
        String data = service.getData();
        return new Response<>("", data);
    }
}
