package com.linicedev.music_artist_finder.lock.infrastructure;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.linicedev.music_artist_finder.lock.domain.ApiLockEntity;
import com.linicedev.music_artist_finder.lock.domain.ApiLockRepository;

@Repository
public interface ApiLockCrudRepository extends CrudRepository<ApiLockEntity, Long>, ApiLockRepository {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    Optional<ApiLockEntity> findByLockNameAndCurrentHour(String lockName, Long currentHour);

    @Override
    ApiLockEntity save(ApiLockEntity entity);

    @Override
    @Query(value = "select music_artist_finder.api_lock_id_sequence.nextval from dual", nativeQuery = true)
    Long findApiLockIdNextValue();
}
