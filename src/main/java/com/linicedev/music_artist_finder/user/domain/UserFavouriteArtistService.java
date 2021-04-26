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

        // checks if user is persisted in database, if no - the user is new, and new entity is created for it
        UserEntity user = userRepository.findById(userId).orElse(new UserEntity(userId, favouriteArtistId));
        // favourite artist is set in user entity
        user.markFavouriteArtist(favouriteArtistId);

        userRepository.save(user);
    }

    public Optional<UserEntity> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
