package com.linicedev.music_artist_finder.lock.domain;

import java.util.Optional;

public interface ApiLockRepository {

    Optional<ApiLockEntity> findByLockNameAndCurrentHour(String lockName, Long currentHour);

    ApiLockEntity save(ApiLockEntity entity);

    Long findApiLockIdNextValue();

}
