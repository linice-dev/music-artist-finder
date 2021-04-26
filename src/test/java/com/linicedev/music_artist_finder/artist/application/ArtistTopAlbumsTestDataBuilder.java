package com.linicedev.music_artist_finder.artist.application;

import static com.linicedev.music_artist_finder.TestDataConstants.AMG_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_VIEW_URL;
import static com.linicedev.music_artist_finder.TestDataConstants.ART_WORK_URL_100;
import static com.linicedev.music_artist_finder.TestDataConstants.ART_WORK_URL_60;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_CENSORED_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_EXPLICITNESS;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_PRICE;
import static com.linicedev.music_artist_finder.TestDataConstants.COLLECTION_VIEW_URL;
import static com.linicedev.music_artist_finder.TestDataConstants.COPYRIGHT;
import static com.linicedev.music_artist_finder.TestDataConstants.COUNTRY;
import static com.linicedev.music_artist_finder.TestDataConstants.CURRENCY;
import static com.linicedev.music_artist_finder.TestDataConstants.PRIMARY_GENRE_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.RELEASE_DATE;
import static com.linicedev.music_artist_finder.TestDataConstants.TRACK_COUNT;

import java.util.List;

import com.linicedev.music_artist_finder.artist.api.ArtistTopAlbums.TopAlbum;
import com.linicedev.music_artist_finder.artist.api.ImmutableArtistTopAlbums;
import com.linicedev.music_artist_finder.artist.api.ImmutableTopAlbum;

public final class ArtistTopAlbumsTestDataBuilder {

    private ArtistTopAlbumsTestDataBuilder() {
    }

    public static ImmutableArtistTopAlbums mockArtistTopAlbums() {
        TopAlbum topAlbum = mockTopAlbum();

        return ImmutableArtistTopAlbums.builder()
                   .topAlbums(List.of(topAlbum))
                   .build();
    }

    public static TopAlbum mockTopAlbum() {
        return ImmutableTopAlbum.builder()
                   .artistId(ARTIST_ID)
                   .amgArtistId(AMG_ARTIST_ID)
                   .artistName(ARTIST_NAME)
                   .artistViewUrl(ARTIST_VIEW_URL)
                   .artworkUrl60(ART_WORK_URL_60)
                   .artworkUrl100(ART_WORK_URL_100)
                   .collectionCensoredName(COLLECTION_CENSORED_NAME)
                   .collectionExplicitness(COLLECTION_EXPLICITNESS)
                   .collectionName(COLLECTION_NAME)
                   .collectionId(COLLECTION_ID)
                   .collectionPrice(COLLECTION_PRICE)
                   .collectionViewUrl(COLLECTION_VIEW_URL)
                   .trackCount(TRACK_COUNT)
                   .copyright(COPYRIGHT)
                   .country(COUNTRY)
                   .releaseDate(RELEASE_DATE)
                   .currency(CURRENCY)
                   .primaryGenreName(PRIMARY_GENRE_NAME)
                   .build();
    }
}
