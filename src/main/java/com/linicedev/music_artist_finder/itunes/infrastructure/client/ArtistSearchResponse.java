package com.linicedev.music_artist_finder.itunes.infrastructure.client;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@JsonDeserialize(as = ImmutableArtistSearchResponse.class)
public interface ArtistSearchResponse {

    Integer getResultCount();

    List<Artist> getResults();

    @Immutable
    @JsonDeserialize(as = ImmutableArtist.class)
    interface Artist {

        String getWrapperType();

        String getArtistType();

        String getArtistName();

        String getArtistLinkUrl();

        Long getArtistId();

        Optional<Long> getAmgArtistId();

        Optional<String> getPrimaryGenreName();

        Optional<Long> getPrimaryGenreId();
    }
}
