package com.linicedev.music_artist_finder.album.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.linicedev.music_artist_finder.album.domain.AlbumEntity;
import com.linicedev.music_artist_finder.album.domain.AlbumRepository;

@Repository
public interface AlbumCrudRepository extends CrudRepository<AlbumEntity, Long>, AlbumRepository {

    @Override
    List<AlbumEntity> findByArtistId(Long artistId);

    @Override
    Optional<AlbumEntity> findById(Long id);

    @Override
    AlbumEntity save(AlbumEntity entity);

}
