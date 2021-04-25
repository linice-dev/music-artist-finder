package com.linicedev.music_artist_finder.artist.api;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;

@Immutable
@JsonSerialize(as = ImmutableArtistSearchResult.class)
public interface ArtistSearchResult {

    List<Artist> getResults();

    @Immutable
    @JsonSerialize(as = Artist.class)
    interface Artist {
        String getArtistType();

        String getArtistName();

        String getArtistLinkUrl();

        Long getArtistId();

        Optional<Long> getAmgArtistId();

        Optional<String> getPrimaryGenreName();

        Optional<Long> getPrimaryGenreId();

        static Artist from(ArtistSearchResponse.Artist responseArtist) {
            return ImmutableArtist.builder()
                       .artistType(responseArtist.getArtistType())
                       .artistName(responseArtist.getArtistName())
                       .artistLinkUrl(responseArtist.getArtistLinkUrl())
                       .artistId(responseArtist.getArtistId())
                       .amgArtistId(responseArtist.getAmgArtistId())
                       .primaryGenreName(responseArtist.getPrimaryGenreName())
                       .primaryGenreId(responseArtist.getPrimaryGenreId())
                       .build();
        }
    }
}
