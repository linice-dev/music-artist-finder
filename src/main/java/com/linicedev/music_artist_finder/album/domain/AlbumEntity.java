package com.linicedev.music_artist_finder.album.domain;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "music_artist_finder", name = "album")
public class AlbumEntity implements Serializable {

    private static final long serialVersionUID = 5130684794353203758L;
    @Id
    private Long id;
    private Long amgArtistId;
    private Long artistId;
    private String artistName;
    private String collectionName;
    private String collectionCensoredName;
    private String artistViewUrl;
    private String collectionViewUrl;
    private String artworkUrl60;
    private String artworkUrl100;
    private BigDecimal collectionPrice;
    private String collectionExplicitness;
    private Integer trackCount;
    private String copyright;
    private String country;
    private String currency;
    private LocalDate releaseDate;
    private String primaryGenreName;
    private LocalDateTime updated;

    public AlbumEntity() {
    }

    public AlbumEntity(Long collectionId,
                       Long amgArtistId,
                       Long artistId,
                       String artistName,
                       String collectionName,
                       String collectionCensoredName,
                       String artistViewUrl,
                       String collectionViewUrl,
                       String artworkUrl60,
                       String artworkUrl100,
                       BigDecimal collectionPrice,
                       String collectionExplicitness,
                       Integer trackCount,
                       String copyright,
                       String country,
                       String currency,
                       LocalDate releaseDate,
                       String primaryGenreName
    ) {
        this.id = requireNonNull(collectionId);
        this.amgArtistId = requireNonNull(amgArtistId);
        this.artistId = requireNonNull(artistId);
        this.artistName = requireNonNull(artistName);
        this.collectionName = requireNonNull(collectionName);
        this.collectionCensoredName = requireNonNull(collectionCensoredName);
        this.artistViewUrl = requireNonNull(artistViewUrl);
        this.collectionViewUrl = requireNonNull(collectionViewUrl);
        this.artworkUrl60 = artworkUrl60;
        this.artworkUrl100 = artworkUrl100;
        this.collectionPrice = requireNonNull(collectionPrice);
        this.collectionExplicitness = requireNonNull(collectionExplicitness);
        this.trackCount = requireNonNull(trackCount);
        this.copyright = requireNonNull(copyright);
        this.country = requireNonNull(country);
        this.currency = requireNonNull(currency);
        this.releaseDate = requireNonNull(releaseDate);
        this.primaryGenreName = requireNonNull(primaryGenreName);
        this.updated = now();
    }

    public void update(Long collectionId,
                       Long amgArtistId,
                       Long artistId,
                       String artistName,
                       String collectionName,
                       String collectionCensoredName,
                       String artistViewUrl,
                       String collectionViewUrl,
                       String artworkUrl60,
                       String artworkUrl100,
                       BigDecimal collectionPrice,
                       String collectionExplicitness,
                       Integer trackCount,
                       String copyright,
                       String country,
                       String currency,
                       LocalDate releaseDate,
                       String primaryGenreName
    ) {
        this.id = requireNonNull(collectionId);
        this.amgArtistId = requireNonNull(amgArtistId);
        this.artistId = requireNonNull(artistId);
        this.artistName = requireNonNull(artistName);
        this.collectionName = requireNonNull(collectionName);
        this.collectionCensoredName = requireNonNull(collectionCensoredName);
        this.artistViewUrl = requireNonNull(artistViewUrl);
        this.collectionViewUrl = requireNonNull(collectionViewUrl);
        this.artworkUrl60 = artworkUrl60;
        this.artworkUrl100 = artworkUrl100;
        this.collectionPrice = requireNonNull(collectionPrice);
        this.collectionExplicitness = requireNonNull(collectionExplicitness);
        this.trackCount = requireNonNull(trackCount);
        this.copyright = requireNonNull(copyright);
        this.currency = requireNonNull(currency);
        this.country = requireNonNull(country);
        this.releaseDate = requireNonNull(releaseDate);
        this.primaryGenreName = requireNonNull(primaryGenreName);
        this.updated = now();
    }

    public Long getId() {
        return id;
    }

    public Long getAmgArtistId() {
        return amgArtistId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public String getArtistViewUrl() {
        return artistViewUrl;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public BigDecimal getCollectionPrice() {
        return collectionPrice;
    }

    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
