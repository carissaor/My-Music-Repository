package com.myProject.music.service;

import com.myProject.music.model.Music;
import java.util.List;

public interface MusicServiceInterface {

    public Music saveMusic(Music music);
    public List<Music> getAllMusic();
}

