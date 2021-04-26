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
import static java.time.LocalDateTime.MIN;

import com.linicedev.music_artist_finder.album.domain.AlbumEntity;

public final class AlbumEntityTestDataBuilder {

    private AlbumEntityTestDataBuilder() {
    }

    public static AlbumEntity mockAlbumEntity() {
        return new AlbumEntity(
            COLLECTION_ID,
            AMG_ARTIST_ID,
            ARTIST_ID,
            ARTIST_NAME,
            COLLECTION_NAME,
            COLLECTION_CENSORED_NAME,
            ARTIST_VIEW_URL,
            COLLECTION_VIEW_URL,
            ART_WORK_URL_60,
            ART_WORK_URL_100,
            COLLECTION_PRICE,
            COLLECTION_EXPLICITNESS,
            TRACK_COUNT,
            COPYRIGHT,
            COUNTRY,
            CURRENCY,
            RELEASE_DATE,
            PRIMARY_GENRE_NAME
        );
    }

    public static AlbumEntity mockOldAlbumEntity() {
        AlbumEntity albumEntity = new AlbumEntity(
            COLLECTION_ID,
            AMG_ARTIST_ID,
            ARTIST_ID,
            ARTIST_NAME,
            COLLECTION_NAME,
            COLLECTION_CENSORED_NAME,
            ARTIST_VIEW_URL,
            COLLECTION_VIEW_URL,
            ART_WORK_URL_60,
            ART_WORK_URL_100,
            COLLECTION_PRICE,
            COLLECTION_EXPLICITNESS,
            TRACK_COUNT,
            COPYRIGHT,
            COUNTRY,
            CURRENCY,
            RELEASE_DATE,
            PRIMARY_GENRE_NAME
        );
        albumEntity.setUpdated(MIN);

        return albumEntity;
    }
}
