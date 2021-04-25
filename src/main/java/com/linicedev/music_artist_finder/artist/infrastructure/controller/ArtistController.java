package com.linicedev.music_artist_finder.artist.infrastructure.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linicedev.music_artist_finder.artist.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.artist.api.ArtistTopAlbums;
import com.linicedev.music_artist_finder.artist.application.ArtistApplicationService;

@RestController
public class ArtistController {
    private final ArtistApplicationService artistApplicationService;

    public ArtistController(ArtistApplicationService artistApplicationService) {
        this.artistApplicationService = artistApplicationService;
    }

    @GetMapping(value = "/artists", produces = APPLICATION_JSON_VALUE)
    public ArtistSearchResult searchArtists(@RequestParam String query) {
        return artistApplicationService.searchArtists(query);
    }

    @GetMapping(value = "/artist/{artistId}/top-albums", produces = APPLICATION_JSON_VALUE)
    public ArtistTopAlbums getArtistAlbums(@PathVariable Long artistId) {
        return artistApplicationService.findArtistTopAlbums(artistId);
    }

}