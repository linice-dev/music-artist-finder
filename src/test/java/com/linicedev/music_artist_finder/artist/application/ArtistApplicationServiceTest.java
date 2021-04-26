package com.linicedev.music_artist_finder.artist.application;

import static com.linicedev.music_artist_finder.TestDataConstants.API_LOCK_NAME;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_SEARCH_QUERY;
import static com.linicedev.music_artist_finder.TestDataConstants.LIMIT;
import static com.linicedev.music_artist_finder.artist.application.AlbumEntityTestDataBuilder.mockAlbumEntity;
import static com.linicedev.music_artist_finder.artist.application.AlbumEntityTestDataBuilder.mockOldAlbumEntity;
import static com.linicedev.music_artist_finder.artist.application.ApiLockEntityTestDataBuilder.mockApiLockEntity;
import static com.linicedev.music_artist_finder.artist.application.ApiLockEntityTestDataBuilder.mockOverLimitApiLockEntity;
import static com.linicedev.music_artist_finder.artist.application.ArtistSearchResponseTestDataBuilder.mockArtistSearchResponse;
import static com.linicedev.music_artist_finder.artist.application.ArtistSearchResponseTestDataBuilder.mockEmptyArtistSearchResponse;
import static com.linicedev.music_artist_finder.artist.application.ArtistTopAlbumsTestDataBuilder.mockArtistTopAlbums;
import static com.linicedev.music_artist_finder.artist.application.ArtistTopAlbumsTestDataBuilder.mockTopAlbum;
import static com.linicedev.music_artist_finder.artist.application.TopAlbumsResponseTestDataBuilder.mockTopAlbumsResponse;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockArtistSearchResult;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockEmptyArtistSearchResult;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linicedev.music_artist_finder.album.domain.AlbumEntity;
import com.linicedev.music_artist_finder.album.domain.AlbumRepository;
import com.linicedev.music_artist_finder.artist.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.artist.api.ArtistTopAlbums;
import com.linicedev.music_artist_finder.artist.api.IllegalRequestParameterException;
import com.linicedev.music_artist_finder.artist.domain.ArtistService;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.HttpItunesApiClient;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.HttpItunesApiClientWrapper;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.TopAlbumsResponse;
import com.linicedev.music_artist_finder.lock.domain.ApiLockEntity;
import com.linicedev.music_artist_finder.lock.domain.ApiLockRepository;

@ExtendWith(MockitoExtension.class)
public class ArtistApplicationServiceTest {

    private static final Integer MAX_ALBUM_DATA_AGE = 7;

    @Mock
    private HttpItunesApiClient httpItunesApiClient;

    @Mock
    private ApiLockRepository apiLockRepository;

    @Mock
    private AlbumRepository albumRepository;

    private ArtistApplicationService artistApplicationService;

    @BeforeEach
    void setUp() {
        HttpItunesApiClientWrapper httpItunesApiClientWrapper = new HttpItunesApiClientWrapper(apiLockRepository, httpItunesApiClient);
        ArtistService artistService = new ArtistService(3, MAX_ALBUM_DATA_AGE, httpItunesApiClientWrapper, albumRepository);

        artistApplicationService = new ArtistApplicationService(artistService);
    }

    @Nested
    class SearchArtists {

        @Test
        void throwsIllegalRequestParameterExceptionGivenRequestParameterIsNull() {
            // When
            ThrowingCallable callable = () -> artistApplicationService.searchArtists(null);

            // Then
            assertThatThrownBy(callable).isInstanceOf(IllegalRequestParameterException.class);
        }

        @Test
        void throwsIllegalRequestParameterExceptionGivenRequestParameterIsEmpty() {
            // When
            ThrowingCallable callable = () -> artistApplicationService.searchArtists("");

            // Then
            assertThatThrownBy(callable).isInstanceOf(IllegalRequestParameterException.class);
        }

        @Test
        void throwsIllegalRequestParameterExceptionGivenRequestParameterContainsOnlyEmptyCharacters() {
            // When
            ThrowingCallable callable = () -> artistApplicationService.searchArtists(" ");

            // Then
            assertThatThrownBy(callable).isInstanceOf(IllegalRequestParameterException.class);
        }

        @Test
        void findsArtistsGivenArtistsCanBeFoundInItunes() {
            // Given
            given(httpItunesApiClient.findArtistByKeyword(ARTIST_SEARCH_QUERY)).willReturn(mockArtistSearchResponse());

            // When
            ArtistSearchResult searchResult = artistApplicationService.searchArtists(ARTIST_SEARCH_QUERY);

            // Then
            ArtistSearchResult expectedResult = mockArtistSearchResult();
            assertThat(searchResult).isEqualTo(expectedResult);
        }

        @Test
        void doesNotFindArtistsGivenArtistCannotBeFoundInItunes() {
            // Given
            given(httpItunesApiClient.findArtistByKeyword(ARTIST_SEARCH_QUERY)).willReturn(mockEmptyArtistSearchResponse());

            // When
            ArtistSearchResult searchResult = artistApplicationService.searchArtists(ARTIST_SEARCH_QUERY);

            // Then
            ArtistSearchResult expectedResult = mockEmptyArtistSearchResult();
            assertThat(searchResult).isEqualTo(expectedResult);
        }
    }

    @Nested
    class FindArtistTopAlbums {

        @Test
        void findsArtistTopAlbumsGivenAlbumsCanBeFoundInRepository() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of(mockAlbumEntity(), mockAlbumEntity()));

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ArtistTopAlbums expectedArtistTopAlbums = mockArtistTopAlbums().withTopAlbums(mockTopAlbum(), mockTopAlbum());

            assertThat(expectedArtistTopAlbums)
                .usingRecursiveComparison()
                .isEqualTo(albums);
        }

        @Test
        void findsArtistTopAlbumsGivenAlbumsCannotBeFoundInRepositoryAndCanBeFoundByApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of());

            List<TopAlbumsResponse> responses = List.of(mockTopAlbumsResponse(), mockTopAlbumsResponse());
            given(httpItunesApiClient.findTopAlbums(ARTIST_ID, LIMIT)).willReturn(responses);
            given(albumRepository.save(any(AlbumEntity.class))).willReturn(mockAlbumEntity());

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ArtistTopAlbums expectedArtistTopAlbums = mockArtistTopAlbums().withTopAlbums(mockTopAlbum(), mockTopAlbum());

            assertThat(expectedArtistTopAlbums)
                .usingRecursiveComparison()
                .isEqualTo(albums);
        }

        @Test
        void savesArtistTopAlbumEntityGivenAlbumsCannotBeFoundInRepositoryAndCanBeFoundByApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of());

            List<TopAlbumsResponse> responses = List.of(mockTopAlbumsResponse(), mockTopAlbumsResponse());
            given(httpItunesApiClient.findTopAlbums(ARTIST_ID, LIMIT)).willReturn(responses);
            given(albumRepository.save(any(AlbumEntity.class))).willReturn(mockAlbumEntity());

            // When
            artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            List<AlbumEntity> expectedAlbumEntities = List.of(mockAlbumEntity(), mockAlbumEntity());

            ArgumentCaptor<AlbumEntity> captor = ArgumentCaptor.forClass(AlbumEntity.class);
            verify(albumRepository, times(responses.size())).save(captor.capture());

            assertThat(captor.getAllValues())
                .usingRecursiveComparison()
                .ignoringFields("updated")
                .ignoringCollectionOrder()
                .isEqualTo(expectedAlbumEntities);
        }

        @Test
        void findsArtistTopAlbumsByApiGivenAlbumsFoundInRepositoryAreTooOldAndCanBeFoundByApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of(mockOldAlbumEntity(), mockOldAlbumEntity()));

            List<TopAlbumsResponse> responses = List.of(mockTopAlbumsResponse(), mockTopAlbumsResponse());
            given(httpItunesApiClient.findTopAlbums(ARTIST_ID, LIMIT)).willReturn(responses);
            given(albumRepository.save(any(AlbumEntity.class))).willReturn(mockAlbumEntity());

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ArtistTopAlbums expectedArtistTopAlbums = mockArtistTopAlbums().withTopAlbums(mockTopAlbum(), mockTopAlbum());

            assertThat(expectedArtistTopAlbums)
                .usingRecursiveComparison()
                .isEqualTo(albums);
        }

        @Test
        void savesArtistTopAlbumEntityGivenAlbumsFoundInRepositoryAreTooOldAndCanBeFoundByApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of(mockOldAlbumEntity(), mockOldAlbumEntity()));

            List<TopAlbumsResponse> responses = List.of(mockTopAlbumsResponse(), mockTopAlbumsResponse());
            given(httpItunesApiClient.findTopAlbums(ARTIST_ID, LIMIT)).willReturn(responses);
            given(albumRepository.save(any(AlbumEntity.class))).willReturn(mockAlbumEntity());

            // When
            artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            List<AlbumEntity> expectedAlbumEntities = List.of(mockAlbumEntity(), mockAlbumEntity());

            ArgumentCaptor<AlbumEntity> captor = ArgumentCaptor.forClass(AlbumEntity.class);
            verify(albumRepository, times(responses.size())).save(captor.capture());

            assertThat(captor.getAllValues())
                .usingRecursiveComparison()
                .ignoringFields("updated")
                .ignoringCollectionOrder()
                .isEqualTo(expectedAlbumEntities);
        }

        @Test
        void findsEmptyArtistTopAlbumListGivenAlbumsCannotBeFoundInRepositoryAndCannotBeFoundByApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of());
            given(httpItunesApiClient.findTopAlbums(ARTIST_ID, LIMIT)).willReturn(List.of());

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ArtistTopAlbums expectedArtistTopAlbums = mockArtistTopAlbums().withTopAlbums(List.of());

            assertThat(expectedArtistTopAlbums)
                .usingRecursiveComparison()
                .isEqualTo(albums);
        }

        @Test
        void findsEmptyArtistTopAlbumListGivenAlbumsCannotBeFoundInRepositoryAndApiRequestCountIsOverLimit() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of());
            given(apiLockRepository.findByLockNameAndCurrentHour(eq(API_LOCK_NAME), anyLong())).willReturn(of(mockOverLimitApiLockEntity()));

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ArtistTopAlbums expectedArtistTopAlbums = mockArtistTopAlbums().withTopAlbums(List.of());

            assertThat(expectedArtistTopAlbums)
                .usingRecursiveComparison()
                .isEqualTo(albums);
        }

        @Test
        void incrementsApiLockCountGivenApiTopArtistCanBeReturnedFromApi() {
            // Given
            given(albumRepository.findByArtistId(ARTIST_ID)).willReturn(List.of());
            given(apiLockRepository.findByLockNameAndCurrentHour(eq(API_LOCK_NAME), anyLong())).willReturn(of(mockApiLockEntity()));

            // When
            ArtistTopAlbums albums = artistApplicationService.findArtistTopAlbums(ARTIST_ID);

            // Then
            ApiLockEntity apiLockEntity = mockApiLockEntity();
            apiLockEntity.incrementCount();

            ArgumentCaptor<ApiLockEntity> lockEntityArgumentCaptor = ArgumentCaptor.forClass(ApiLockEntity.class);
            verify(apiLockRepository).save(lockEntityArgumentCaptor.capture());

            assertThat(lockEntityArgumentCaptor.getValue().getCount())
                .isEqualTo(apiLockEntity.getCount());
        }
    }

}
