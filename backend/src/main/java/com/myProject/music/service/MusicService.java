package com.myProject.music.service;

import com.myProject.music.model.Genre;
import com.myProject.music.model.Music;
import com.myProject.music.repository.MusicRepository;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        String clientId = System.getenv("SPOTIFY_CLIENT_ID");
        String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
        String accessToken = System.getenv("SPOTIFY_ACCESS_TOKEN");
        URI redirect = SpotifyHttpManager.makeUri("http://localhost:3000/");

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirect)
                .setAccessToken(accessToken)
                .build();

        GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(3) // Number of recommendations
                .build();

        try {
            Recommendations recommendations = getRecommendationsRequest.execute();
            // Process the recommendations and convert to Music objects
            List<Music> recommendedSongs = convertToMusicObjects(recommendations);
            return recommendedSongs;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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
