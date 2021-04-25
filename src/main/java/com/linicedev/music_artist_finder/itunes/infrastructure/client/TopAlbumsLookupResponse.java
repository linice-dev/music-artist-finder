package com.linicedev.music_artist_finder.itunes.infrastructure.client;

import java.util.List;
import java.util.Map;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Immutable
@JsonDeserialize(as = ImmutableTopAlbumsLookupResponse.class)
public interface TopAlbumsLookupResponse {

    Long getResultCount();

    List<Map<String, Object>> getResults();
}
