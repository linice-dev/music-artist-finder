package com.linicedev.music_artist_finder.itunes.infrastructure.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.linicedev.music_artist_finder.lock.domain.ApiLockEntity;
import com.linicedev.music_artist_finder.lock.domain.ApiLockRepository;

@Component
public class HttpItunesApiClientWrapper {

    private final static String ITUNES_API_COUNTER_LOCK = "ItunesAPICounter";

    private final ApiLockRepository apiLockRepository;
    private final HttpItunesApiClient httpItunesApiClient;

    public HttpItunesApiClientWrapper(ApiLockRepository apiLockRepository, HttpItunesApiClient httpItunesApiClient) {
        this.apiLockRepository = apiLockRepository;
        this.httpItunesApiClient = httpItunesApiClient;
    }

    // cache is added in order to minimize itunes api calls number and to avoid api counter increments
    @Cacheable("artists")
    public ArtistSearchResponse findArtistByKeyword(String keyword) {
        // if api call limit is exceeded empty list of artist is returned
        if (!assertApiCallsUnderLimit()) {
            return ImmutableArtistSearchResponse.builder()
                       .resultCount(0)
                       .build();
        }

        // if counter did not reach the limit yet, real itunes api is called
        return httpItunesApiClient.findArtistByKeyword(keyword);
    }

    // cache is added in order to minimize itunes api calls number and to avoid api counter increments
    @Cacheable("albums")
    public List<TopAlbumsResponse> findTopAlbums(Long artistId, int limit) {
        // if api call limit is exceeded empty list of albums is returned
        if (!assertApiCallsUnderLimit()) {
            return List.of();
        }

        // if counter did not reach the limit yet, real itunes api is called
        return httpItunesApiClient.findTopAlbums(artistId, limit);
    }

    private boolean assertApiCallsUnderLimit() {
        // current hour value is based on epoch time every hour this value is different
        Long currentHour = System.currentTimeMillis() / 1000 / 3600;
        // checks if counter lock for current hour exists
        Optional<ApiLockEntity> lockEntity = apiLockRepository.findByLockNameAndCurrentHour(ITUNES_API_COUNTER_LOCK, currentHour);

        // if counter lock for current hour does not exist, creates it
        if (lockEntity.isEmpty()) {
            Long apiLockId = apiLockRepository.findApiLockIdNextValue();
            apiLockRepository.save(new ApiLockEntity(apiLockId, ITUNES_API_COUNTER_LOCK));

            return true;
        }

        // check if counter is under limit
        ApiLockEntity entity = lockEntity.get();
        if (entity.getCount() >= 100) {
            return false;
        }

        entity.incrementCount();
        apiLockRepository.save(entity);

        return true;
    }
}
