package com.myProject.music.model;
import jakarta.persistence.*;

@Entity
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String artist;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    public Music() {

    }

    public Music(int id, String title, String artist, Genre genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

