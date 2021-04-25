package com.linicedev.music_artist_finder.artist.application;

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
import static com.linicedev.music_artist_finder.TestDataConstants.WRAPPER_TYPE;

import java.util.List;

import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse.Artist;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ImmutableArtist;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ImmutableArtistSearchResponse;

public final class ArtistSearchResponseTestDataBuilder {

    private ArtistSearchResponseTestDataBuilder() {
    }

    public static ArtistSearchResponse mockArtistSearchResponse() {
        Artist artist = ImmutableArtist.builder()
                            .wrapperType(WRAPPER_TYPE)
                            .artistType(ARTIST_TYPE)
                            .artistName(ARTIST_NAME)
                            .artistLinkUrl(ARTIST_LINK_URL)
                            .artistId(ARTIST_ID)
                            .amgArtistId(AMG_ARTIST_ID)
                            .primaryGenreName(PRIMARY_GENRE_NAME)
                            .primaryGenreId(PRIMARY_GENRE_ID)
                            .build();
        Artist softwareArtist = ImmutableArtist.builder()
                                    .wrapperType(WRAPPER_TYPE)
                                    .artistType(SOFTWARE_ARTIST_TYPE)
                                    .artistName(ARTIST_NAME)
                                    .artistLinkUrl(SOFTWARE_ARTIST_LINK_URL)
                                    .artistId(SOFTWARE_ARTIST_ID)
                                    .build();

        List<Artist> artists = List.of(artist, softwareArtist);
        return ImmutableArtistSearchResponse.builder()
                   .resultCount(artists.size())
                   .addResults(artist, softwareArtist)
                   .build();
    }

    public static ArtistSearchResponse mockEmptyArtistSearchResponse() {
        return ImmutableArtistSearchResponse.builder()
                   .resultCount(0)
                   .addAllResults(List.of())
                   .build();
    }
}
