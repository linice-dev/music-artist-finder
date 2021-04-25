package com.linicedev.music_artist_finder.user.api;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@Value.Style()
@JsonDeserialize(as = ImmutableSaveFavouriteArtistCommand.class)
public interface SaveFavouriteArtistCommand {

    @Value.Parameter
    Long getFavouriteArtistId();
}
