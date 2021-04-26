package com.linicedev.music_artist_finder.user.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linicedev.music_artist_finder.user.api.ImmutableUserFavouriteArtist;
import com.linicedev.music_artist_finder.user.api.SaveFavouriteArtistCommand;
import com.linicedev.music_artist_finder.user.api.UserFavouriteArtist;
import com.linicedev.music_artist_finder.user.domain.UserEntity;
import com.linicedev.music_artist_finder.user.domain.UserFavouriteArtistService;

@Service
@Transactional
public class UserApplicationService {

    private final UserFavouriteArtistService userFavouriteArtistService;

    public UserApplicationService(UserFavouriteArtistService userFavouriteArtistService) {
        this.userFavouriteArtistService = userFavouriteArtistService;
    }

    public void saveFavouriteArtist(Long userId, SaveFavouriteArtistCommand command) {
        userFavouriteArtistService.saveUserFavouriteArtistService(userId, command);
    }

    // searches for user in the database and if found, maps the user to favourite artist id and returns it
    //  if user is not found, empty favourite artist is returned
    @Transactional(readOnly = true)
    public UserFavouriteArtist findFavouriteArtist(Long userId) {
        Optional<UserEntity> userEntity = userFavouriteArtistService.findUserById(userId);
        return ImmutableUserFavouriteArtist.of(userEntity.map(UserEntity::getFavouriteArtistId));
    }
}
