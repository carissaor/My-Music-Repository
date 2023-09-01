package com.myProject.music.controller;

import com.myProject.music.model.Music;
import com.myProject.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @PostMapping("/add")
    public String add(@RequestBody Music music) {
        musicService.saveMusic(music);
        return "New song added!";
    }

    @GetMapping("/getAll")
    public List<Music> getAllMusic() {
        return musicService.getAllMusic();
    }
}
