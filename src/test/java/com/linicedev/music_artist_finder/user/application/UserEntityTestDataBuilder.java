package com.linicedev.music_artist_finder.user.application;

import static com.linicedev.music_artist_finder.TestDataConstants.FAVOURITE_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.USER_ID;

import com.linicedev.music_artist_finder.user.domain.UserEntity;

public final class UserEntityTestDataBuilder {

    private UserEntityTestDataBuilder() {
    }

    public static UserEntity mockUserEntity() {
        return new UserEntity(USER_ID, FAVOURITE_ARTIST_ID);
    }

}
