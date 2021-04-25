package com.linicedev.music_artist_finder.artist.domain;

import org.springframework.stereotype.Service;

import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.HttpItunesApiClient;

@Service
public class ArtistService {

    private final HttpItunesApiClient httpItunesApiClient;

    public ArtistService(HttpItunesApiClient httpItunesApiClient) {
        this.httpItunesApiClient = httpItunesApiClient;
    }

    public ArtistSearchResponse findArtist(String searchQueryParameter) {
        return httpItunesApiClient.findArtistByKeyword(searchQueryParameter);
    }
}
