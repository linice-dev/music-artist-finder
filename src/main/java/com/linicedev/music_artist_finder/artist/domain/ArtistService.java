package com.linicedev.music_artist_finder.artist.domain;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.linicedev.music_artist_finder.album.domain.AlbumEntity;
import com.linicedev.music_artist_finder.album.domain.AlbumRepository;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.ArtistSearchResponse;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.HttpItunesApiClientWrapper;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.TopAlbumsResponse;

@Service
public class ArtistService {

    private final Integer topAlbumsLimit;
    private final Integer maxAlbumDataAge;
    private final HttpItunesApiClientWrapper httpItunesApiClientWrapper;
    private final AlbumRepository albumRepository;

    public ArtistService(
        @Value("${image-artist-finder.top-albums-limit:5}") Integer topAlbumsLimit,
        @Value("${image-artist-finder.max-album-data-age:7}") Integer maxAlbumDataAge,
        HttpItunesApiClientWrapper httpItunesApiClientWrapper,
        AlbumRepository albumRepository
    ) {
        this.topAlbumsLimit = topAlbumsLimit;
        this.maxAlbumDataAge = maxAlbumDataAge;
        this.httpItunesApiClientWrapper = httpItunesApiClientWrapper;
        this.albumRepository = albumRepository;
    }

    public ArtistSearchResponse findArtist(String searchQueryParameter) {
        return httpItunesApiClientWrapper.findArtistByKeyword(searchQueryParameter);
    }

    public List<AlbumEntity> findArtistTopAlbums(Long artistId) {
        List<AlbumEntity> albumEntities = albumRepository.findByArtistId(artistId);
        List<AlbumEntity> actualAlbumEntities =
            albumEntities.stream()
                .filter(albumEntity -> albumEntity.getUpdated().plus(maxAlbumDataAge, DAYS).isBefore(now()))
                .collect(toList());

        if (!actualAlbumEntities.isEmpty()) {
            return actualAlbumEntities;
        }

        List<TopAlbumsResponse> topAlbums = httpItunesApiClientWrapper.findTopAlbums(artistId, topAlbumsLimit);
        return topAlbums.stream()
                   .map(this::createOrUpdateAlbumEntity)
                   .collect(toList());
    }

    private AlbumEntity createOrUpdateAlbumEntity(TopAlbumsResponse topAlbum) {
        AlbumEntity albumEntity = albumRepository.findById(topAlbum.getArtistId())
                                      .orElse(new AlbumEntity(
                                          topAlbum.getCollectionId(),
                                          topAlbum.getAmgArtistId(),
                                          topAlbum.getArtistId(),
                                          topAlbum.getArtistName(),
                                          topAlbum.getCollectionName(),
                                          topAlbum.getCollectionCensoredName(),
                                          topAlbum.getArtistViewUrl(),
                                          topAlbum.getCollectionViewUrl(),
                                          topAlbum.getArtworkUrl60().orElse(null),
                                          topAlbum.getArtworkUrl100().orElse(null),
                                          topAlbum.getCollectionPrice(),
                                          topAlbum.getCollectionExplicitness(),
                                          topAlbum.getTrackCount(),
                                          topAlbum.getCopyright(),
                                          topAlbum.getCountry(),
                                          topAlbum.getCurrency(),
                                          topAlbum.getReleaseDate(),
                                          topAlbum.getPrimaryGenreName()
                                      ));

        albumEntity.update(topAlbum.getCollectionId(),
            topAlbum.getAmgArtistId(),
            topAlbum.getArtistId(),
            topAlbum.getArtistName(),
            topAlbum.getCollectionName(),
            topAlbum.getCollectionCensoredName(),
            topAlbum.getArtistViewUrl(),
            topAlbum.getCollectionViewUrl(),
            topAlbum.getArtworkUrl60().orElse(null),
            topAlbum.getArtworkUrl100().orElse(null),
            topAlbum.getCollectionPrice(),
            topAlbum.getCollectionExplicitness(),
            topAlbum.getTrackCount(),
            topAlbum.getCopyright(),
            topAlbum.getCountry(),
            topAlbum.getCurrency(),
            topAlbum.getReleaseDate(),
            topAlbum.getPrimaryGenreName());

        return albumRepository.save(albumEntity);
    }
}
