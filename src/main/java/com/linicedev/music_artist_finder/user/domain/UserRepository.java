package com.linicedev.music_artist_finder.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findById(Long userId);

    UserEntity save(UserEntity entity);

    Long findUserIdNextValue();

}
