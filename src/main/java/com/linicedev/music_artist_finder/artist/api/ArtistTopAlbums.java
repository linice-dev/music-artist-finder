package com.linicedev.music_artist_finder.artist.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.linicedev.music_artist_finder.album.domain.AlbumEntity;

@Immutable
@JsonSerialize(as = ImmutableArtistTopAlbums.class)
public interface ArtistTopAlbums {

    static ArtistTopAlbums from(List<AlbumEntity> albumEntities) {
        List<TopAlbum> topAlbums =
            albumEntities.stream()
                .map(entity -> ImmutableTopAlbum.builder()
                                   .amgArtistId(entity.getAmgArtistId())
                                   .artistId(entity.getArtistId())
                                   .artistName(entity.getArtistName())
                                   .artistViewUrl(entity.getArtistViewUrl())
                                   .artworkUrl60(entity.getArtworkUrl60())
                                   .artworkUrl100(entity.getArtworkUrl100())
                                   .collectionCensoredName(entity.getCollectionCensoredName())
                                   .collectionExplicitness(entity.getCollectionExplicitness())
                                   .collectionId(entity.getId())
                                   .collectionName(entity.getCollectionName())
                                   .collectionPrice(entity.getCollectionPrice())
                                   .collectionViewUrl(entity.getCollectionViewUrl())
                                   .trackCount(entity.getTrackCount())
                                   .copyright(entity.getCopyright())
                                   .country(entity.getCountry())
                                   .currency(entity.getCurrency())
                                   .releaseDate(entity.getReleaseDate())
                                   .primaryGenreName(entity.getPrimaryGenreName())
                                   .build())
                .collect(Collectors.toList());
        return ImmutableArtistTopAlbums.builder()
                   .addAllTopAlbums(topAlbums)
                   .build();
    }

    List<TopAlbum> getTopAlbums();

    @Immutable
    @JsonSerialize(as = ImmutableTopAlbum.class)
    public interface TopAlbum {
        Long getArtistId();

        Long getCollectionId();

        Long getAmgArtistId();

        String getArtistName();

        String getCollectionName();

        String getCollectionCensoredName();

        String getArtistViewUrl();

        String getCollectionViewUrl();

        String getArtworkUrl60();

        String getArtworkUrl100();

        BigDecimal getCollectionPrice();

        String getCollectionExplicitness();

        Integer getTrackCount();

        String getCopyright();

        String getCountry();

        String getCurrency();

        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate getReleaseDate();

        String getPrimaryGenreName();
    }
}
