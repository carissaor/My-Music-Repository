package com.myProject.music.model;

import jakarta.persistence.*;

@Entity
public class Music {

    private enum SongCategory {
        POP,
        KPOP,
        INDIE,
        COUNTRY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    private String artist;
    private SongCategory category;

    public Music() {

    }

    public Music(int id, String title, String artist, SongCategory category) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public SongCategory getCategory() {
        return category;
    }

    public void setCategory(SongCategory category) {
        this.category = category;
    }
}
