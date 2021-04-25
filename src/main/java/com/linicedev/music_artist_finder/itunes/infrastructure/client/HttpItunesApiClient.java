package com.linicedev.music_artist_finder.itunes.infrastructure.client;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpItunesApiClient {

    private static final String ITUNES_BASE_URI = "https://itunes.apple.com";
    private static final String ARTIST_SEARCH_PATH = "/search?entity=allArtist&term={keyword}";
    private static final String TOP_ALBUMS_LOOKUP_PATH = "/lookup?id={artistId}&entity=album&limit={limit}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HttpItunesApiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Cacheable("artists")
    public ArtistSearchResponse findArtistByKeyword(String keyword) {
        return restTemplate.postForObject(ITUNES_BASE_URI + ARTIST_SEARCH_PATH, null, ArtistSearchResponse.class, keyword);
    }

    public List<TopAlbumsResponse> findTopAlbums(Long amgArtistId, int limit) {
        return lookupTopAlbums(amgArtistId, limit).getResults()
                   .stream()
                   .filter(wrapper -> wrapper.get("wrapperType").equals("collection"))
                   .filter(wrapper -> wrapper.get("collectionType").equals("Album"))
                   .map(wrapper -> objectMapper.convertValue(wrapper, TopAlbumsResponse.class))
                   .collect(toList());
    }

    private TopAlbumsLookupResponse lookupTopAlbums(Long artistId, int limit) {
        return restTemplate.postForObject(ITUNES_BASE_URI + TOP_ALBUMS_LOOKUP_PATH, null, TopAlbumsLookupResponse.class, artistId, limit);
    }
}
