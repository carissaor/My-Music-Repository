package com.myProject.music.service;

import com.myProject.music.model.Genre;
import com.myProject.music.model.Music;
import com.myProject.music.repository.MusicRepository;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class MusicService implements MusicServiceInterface {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public Music saveMusic(Music music) {
        return musicRepository.save(music);
    }

    @Override
    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    @Override
    public List<Music> getRecommendations() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("backend/application.properties")) {
            properties.load(fileInputStream);
            String accessToken = properties.getProperty("SPOTIFY_ACCESS_TOKEN");
            // Use the accessToken in your application
            String seedArtists = "7nqOGRxlXj7N2JYbgNEjYH"; // todo: update this according to user
            String seedGenres = "anime";
            int limit = 3; // Number of recommendations

            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setAccessToken(accessToken)
                    .build();

            GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                    .limit(limit)
                    .seed_artists(seedArtists)
                    .seed_genres(seedGenres)
                    .build();

            try {
                Recommendations recommendations = getRecommendationsRequest.execute();
                List<Music> recommendedSongs = convertToMusicObjects(recommendations);
                return recommendedSongs;
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Music> convertToMusicObjects(Recommendations recommendations) {
        List<Music> musicList = new ArrayList<>();
        for (se.michaelthelin.spotify.model_objects.specification.TrackSimplified track : recommendations.getTracks()) {
            Music music = new Music();
            music.setTitle(track.getName());
            music.setArtist(track.getArtists()[0].getName()); // Assuming the first artist
            music.setGenre(mapGenre(track));
            musicList.add(music);
        }

        return musicList;
    }

    private Genre mapGenre(se.michaelthelin.spotify.model_objects.specification.TrackSimplified track) {
        String spotifyGenre = track.getHref();
        switch (spotifyGenre) {
            case "pop":
                return Genre.POP;
            case "kpop":
                return Genre.KPOP;
            case "indie":
                return Genre.INDIE;
            case "country":
                return Genre.COUNTRY;
            default:
                return Genre.POP; // Default to POP if genre is unknown
        }
    }
}

