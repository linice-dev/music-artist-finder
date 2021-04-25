package com.linicedev.music_artist_finder.artist.infrastructure.controller;

import static com.linicedev.music_artist_finder.TestDataConstants.AMG_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_LINK_URL;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_TYPE;
import static com.linicedev.music_artist_finder.TestDataConstants.PRIMARY_GENRE_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.PRIMARY_GENRE_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.SOFTWARE_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.SOFTWARE_ARTIST_LINK_URL;
import static com.linicedev.music_artist_finder.TestDataConstants.SOFTWARE_ARTIST_TYPE;

import java.util.List;

import com.linicedev.music_artist_finder.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.api.ArtistSearchResult.Artist;
import com.linicedev.music_artist_finder.api.ImmutableArtist;
import com.linicedev.music_artist_finder.api.ImmutableArtistSearchResult;

public final class ArtistSearchResultTestDataBuilder {

    private ArtistSearchResultTestDataBuilder() {
    }

    public static ArtistSearchResult mockArtistSearchResult() {
        Artist artist = ImmutableArtist.builder()
                            .artistType(ARTIST_TYPE)
                            .artistName(ARTIST_NAME)
                            .artistLinkUrl(ARTIST_LINK_URL)
                            .artistId(ARTIST_ID)
                            .amgArtistId(AMG_ARTIST_ID)
                            .primaryGenreName(PRIMARY_GENRE_NAME)
                            .primaryGenreId(PRIMARY_GENRE_ID)
                            .build();
        Artist softwareArtist = ImmutableArtist.builder()
                                    .artistType(SOFTWARE_ARTIST_TYPE)
                                    .artistName(ARTIST_NAME)
                                    .artistLinkUrl(SOFTWARE_ARTIST_LINK_URL)
                                    .artistId(SOFTWARE_ARTIST_ID)
                                    .build();

        return ImmutableArtistSearchResult.builder()
                   .addResults(artist, softwareArtist)
                   .build();
    }

    public static ArtistSearchResult mockEmptyArtistSearchResult() {
        return ImmutableArtistSearchResult.builder()
                   .addAllResults(List.of())
                   .build();
    }
}
