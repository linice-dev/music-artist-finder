package com.linicedev.music_artist_finder.user.api;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Immutable
@Value.Style()
@JsonSerialize(as = ImmutableUserFavouriteArtist.class)
public interface UserFavouriteArtist {

    @Value.Parameter
    Optional<Long> getFavouriteArtistId();
}
