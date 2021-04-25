package com.linicedev.music_artist_finder.user.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.linicedev.music_artist_finder.user.api.SaveFavouriteArtistCommand;

@Service
public class UserFavouriteArtistService {

    private final UserRepository userRepository;

    public UserFavouriteArtistService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUserFavouriteArtistService(Long userId, SaveFavouriteArtistCommand command) {
        Long favouriteArtistId = command.getFavouriteArtistId();

        UserEntity user = userRepository.findById(userId).orElse(new UserEntity(userId, favouriteArtistId));
        user.markFavouriteArtist(favouriteArtistId);

        userRepository.save(user);
    }

    public Optional<UserEntity> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
