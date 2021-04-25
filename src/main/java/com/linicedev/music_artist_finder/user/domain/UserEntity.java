package com.linicedev.music_artist_finder.user.domain;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "music_artist_finder", name = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5130684794353203758L;

    public UserEntity() {
    }

    public UserEntity(Long userId, Long favouriteArtistId) {
        this.userId = requireNonNull(userId);
        this.favouriteArtistId = requireNonNull(favouriteArtistId);
        this.updated = now();
    }

    @Id
    private Long userId;

    private Long favouriteArtistId;

    private LocalDateTime updated;

    public Long getFavouriteArtistId() {
        return favouriteArtistId;
    }

    public boolean markFavouriteArtist(Long favouriteArtistId) {
        requireNonNull(userId, "favouriteArtistId is mandatory field");

        if (this.favouriteArtistId.equals(favouriteArtistId)) {
            return false;
        }

        this.favouriteArtistId = favouriteArtistId;
        this.updated = now();
        return true;
    }
}
