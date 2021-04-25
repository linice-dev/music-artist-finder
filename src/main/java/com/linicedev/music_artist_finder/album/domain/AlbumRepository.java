package com.linicedev.music_artist_finder.album.domain;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository {

    List<AlbumEntity> findByArtistId(Long artistId);

    Optional<AlbumEntity> findById(Long id);

    AlbumEntity save(AlbumEntity entity);

}
