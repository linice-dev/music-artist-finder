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

    @Cacheable("artists")
    public ArtistSearchResponse findArtistByKeyword(String keyword) {
        if (!assertApiCallsUnderLimit()) {
            return ImmutableArtistSearchResponse.builder()
                       .resultCount(0)
                       .build();
        }

        return httpItunesApiClient.findArtistByKeyword(keyword);
    }

    @Cacheable("albums")
    public List<TopAlbumsResponse> findTopAlbums(Long artistId, int limit) {
        if (!assertApiCallsUnderLimit()) {
            return List.of();
        }

        return httpItunesApiClient.findTopAlbums(artistId, limit);
    }

    private boolean assertApiCallsUnderLimit() {
        Long currentHour = System.currentTimeMillis() / 1000 / 3600;
        Optional<ApiLockEntity> lockEntity = apiLockRepository.findByLockNameAndCurrentHour(ITUNES_API_COUNTER_LOCK, currentHour);

        if (lockEntity.isEmpty()) {
            Long apiLockId = apiLockRepository.findApiLockIdNextValue();
            apiLockRepository.save(new ApiLockEntity(apiLockId, ITUNES_API_COUNTER_LOCK));

            return true;
        }

        ApiLockEntity entity = lockEntity.get();
        if (entity.getCount() >= 100) {
            return false;
        }

        entity.incrementCount();
        apiLockRepository.save(entity);

        return true;
    }
}
