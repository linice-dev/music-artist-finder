package com.linicedev.music_artist_finder.artist.application;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linicedev.music_artist_finder.artist.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.artist.api.ArtistSearchResult.Artist;
import com.linicedev.music_artist_finder.artist.api.ArtistTopAlbums;
import com.linicedev.music_artist_finder.artist.api.IllegalRequestParameterException;
import com.linicedev.music_artist_finder.artist.api.ImmutableArtistSearchResult;
import com.linicedev.music_artist_finder.artist.domain.ArtistService;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;

@Service
@Transactional
public class ArtistApplicationService {

    private final ArtistService artistService;

    public ArtistApplicationService(ArtistService artistService) {
        this.artistService = artistService;
    }

    public ArtistSearchResult searchArtists(String searchQueryParameter) {
        assertSearchQueryParameterIsValid(searchQueryParameter);

        // search for artist and maps the result to response object
        ArtistSearchResponse searchResponse = artistService.findArtist(searchQueryParameter);
        List<Artist> artists = searchResponse.getResults()
                                   .stream()
                                   .map(Artist::from)
                                   .collect(Collectors.toList());

        return ImmutableArtistSearchResult.builder()
                   .addAllResults(artists)
                   .build();
    }

    public ArtistTopAlbums findArtistTopAlbums(Long artistId) {
        return ArtistTopAlbums.from(artistService.findArtistTopAlbums(artistId));
    }

    // validates if the search query is present. If search query is empty (or contains whitespace only), bad request error code is returned
    private void assertSearchQueryParameterIsValid(String searchQueryParameter) {
        if (StringUtils.isBlank(searchQueryParameter)) {
            throw new IllegalRequestParameterException("Artist search query parameter is missing");
        }
    }

}
