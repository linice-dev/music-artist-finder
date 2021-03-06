package com.linicedev.music_artist_finder.user.infrastructure;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.linicedev.music_artist_finder.user.domain.UserEntity;
import com.linicedev.music_artist_finder.user.domain.UserRepository;

@Repository
public interface UserCrudRepository extends CrudRepository<UserEntity, Long>, UserRepository {

    @Override
    Optional<UserEntity> findById(Long id);

    @Override
    UserEntity save(UserEntity entity);

}
