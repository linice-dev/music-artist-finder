package com.linicedev.music_artist_finder.artist.application;

import static com.linicedev.music_artist_finder.TestDataConstants.API_LOCK_ENTITY_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.API_LOCK_NAME;

import com.linicedev.music_artist_finder.lock.domain.ApiLockEntity;

public final class ApiLockEntityTestDataBuilder {

    private ApiLockEntityTestDataBuilder() {
    }

    public static ApiLockEntity mockApiLockEntity() {
        return new ApiLockEntity(API_LOCK_ENTITY_ID, API_LOCK_NAME);
    }

    public static ApiLockEntity mockOverLimitApiLockEntity() {
        ApiLockEntity lockEntity = new ApiLockEntity(API_LOCK_ENTITY_ID, API_LOCK_NAME);
        for (int i = 0; i < 150; i++) {
            lockEntity.incrementCount();
        }

        return lockEntity;
    }
}
