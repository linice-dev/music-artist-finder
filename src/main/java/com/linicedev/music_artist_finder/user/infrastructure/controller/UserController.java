package com.linicedev.music_artist_finder.user.infrastructure.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linicedev.music_artist_finder.user.api.SaveFavouriteArtistCommand;
import com.linicedev.music_artist_finder.user.api.UserFavouriteArtist;
import com.linicedev.music_artist_finder.user.application.UserApplicationService;

@RestController
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping(value = "/user/{userId}/save-favourite-artist", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void saveFavouriteArtist(@PathVariable Long userId,
                                    @RequestBody SaveFavouriteArtistCommand command) {
        userApplicationService.saveFavouriteArtist(userId, command);
    }

    @GetMapping(value = "/user/{userId}/favourite-artist", produces = APPLICATION_JSON_VALUE)
    public UserFavouriteArtist getFavouriteArtist(@PathVariable Long userId) {
        return userApplicationService.findFavouriteArtist(userId);
    }

}