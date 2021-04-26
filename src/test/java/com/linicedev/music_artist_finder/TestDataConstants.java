package com.linicedev.music_artist_finder;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TestDataConstants {

    public static final String ARTIST_SEARCH_QUERY = "abba";
    public static final String ARTIST_TYPE = "Artist";
    public static final String SOFTWARE_ARTIST_TYPE = "Software Artist";
    public static final String ARTIST_NAME = "ABBA";
    public static final String ARTIST_LINK_URL = "https://music.apple.com/us/artist/abba/372976?uo=4";
    public static final String SOFTWARE_ARTIST_LINK_URL = "https://apps.apple.com/us/developer/abba/id1438722470?uo=4";
    public static final Long ARTIST_ID = 372976L;
    public static final Long SOFTWARE_ARTIST_ID = 1438722470L;
    public static final Long AMG_ARTIST_ID = 3492L;
    public static final String PRIMARY_GENRE_NAME = "Pop";
    public static final Long PRIMARY_GENRE_ID = 14L;
    public static final String WRAPPER_TYPE = "artist";
    public static final Long FAVOURITE_ARTIST_ID = 372976L;
    public static final Long USER_ID = 123L;

    public static final String COLLECTION_TYPE = "Album";
    public static final Long COLLECTION_ID = 1422648512L;
    public static final String COLLECTION_NAME = "Gold: Greatest Hits";
    public static final String COLLECTION_CENSORED_NAME = "Gold: Greatest Hits";
    public static final String ARTIST_VIEW_URL = "https://music.apple.com/us/artist/abba/372976?uo=4";
    public static final String COLLECTION_VIEW_URL = "https://music.apple.com/us/album/gold-greatest-hits/1422648512?uo=4";
    public static final String ART_WORK_URL_60 = "https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/29/55/05/29550595-ee55-5de9-24c6-01358267fc42/source/60x60bb.jpg";
    public static final String ART_WORK_URL_100 = "https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/29/55/05/29550595-ee55-5de9-24c6-01358267fc42/source/100x100bb.jpg";
    public static final BigDecimal COLLECTION_PRICE = new BigDecimal("7.99");
    public static final String COLLECTION_EXPLICITNESS = "notExplicit";
    public static final Integer TRACK_COUNT = 19;
    public static final String COPYRIGHT = "This Compilation 2014 Polar Music International AB";
    public static final String COUNTRY = "USA";
    public static final String CURRENCY = "USD";
    public static final LocalDate RELEASE_DATE = LocalDate.of(2014, 1, 1);
    public static final Integer LIMIT = 3;
    public static final Long API_LOCK_ENTITY_ID = 789L;
    public static final String API_LOCK_NAME = "ItunesAPICounter";

    private TestDataConstants() {
    }
}
