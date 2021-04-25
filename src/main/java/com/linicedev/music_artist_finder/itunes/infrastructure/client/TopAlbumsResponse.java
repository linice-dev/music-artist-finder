package com.linicedev.music_artist_finder.itunes.infrastructure.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Immutable
@JsonDeserialize(as = ImmutableTopAlbumsResponse.class)
public interface TopAlbumsResponse {

    String getWrapperType();

    String getCollectionType();

    Long getArtistId();

    Long getCollectionId();

    Long getAmgArtistId();

    String getArtistName();

    String getCollectionName();

    String getCollectionCensoredName();

    String getArtistViewUrl();

    String getCollectionViewUrl();

    Optional<String> getArtworkUrl60();

    Optional<String> getArtworkUrl100();

    BigDecimal getCollectionPrice();

    String getCollectionExplicitness();

    Integer getTrackCount();

    String getCopyright();

    String getCountry();

    String getCurrency();

    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate getReleaseDate();

    String getPrimaryGenreName();

}
