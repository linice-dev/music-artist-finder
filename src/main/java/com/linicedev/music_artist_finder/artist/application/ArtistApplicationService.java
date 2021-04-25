package com.linicedev.music_artist_finder.artist.application;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.linicedev.music_artist_finder.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.api.ArtistSearchResult.Artist;
import com.linicedev.music_artist_finder.api.IllegalRequestParameterException;
import com.linicedev.music_artist_finder.api.ImmutableArtistSearchResult;
import com.linicedev.music_artist_finder.artist.domain.ArtistService;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;

@Service
public class ArtistApplicationService {

    private final ArtistService artistService;

    public ArtistApplicationService(ArtistService artistService) {
        this.artistService = artistService;
    }

    public ArtistSearchResult searchArtists(String searchQueryParameter) {
        assertSearchQueryParameterIsValid(searchQueryParameter);

        ArtistSearchResponse searchResponse = artistService.findArtist(searchQueryParameter);
        List<Artist> artists = searchResponse.getResults()
                                   .stream()
                                   .map(Artist::from)
                                   .collect(Collectors.toList());

        return ImmutableArtistSearchResult.builder()
                   .addAllResults(artists)
                   .build();
    }

    private void assertSearchQueryParameterIsValid(String searchQueryParameter) {
        if (StringUtils.isBlank(searchQueryParameter)) {
            throw new IllegalRequestParameterException("Artist search query parameter is missing");
        }
    }

}
