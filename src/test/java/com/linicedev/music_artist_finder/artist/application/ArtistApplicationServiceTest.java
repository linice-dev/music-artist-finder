package com.linicedev.music_artist_finder.artist.application;

import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_SEARCH_QUERY;
import static com.linicedev.music_artist_finder.artist.application.ArtistSearchResponseTestDataBuilder.mockArtistSearchResponse;
import static com.linicedev.music_artist_finder.artist.application.ArtistSearchResponseTestDataBuilder.mockEmptyArtistSearchResponse;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockArtistSearchResult;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockEmptyArtistSearchResult;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linicedev.music_artist_finder.api.ArtistSearchResult;
import com.linicedev.music_artist_finder.api.IllegalRequestParameterException;
import com.linicedev.music_artist_finder.artist.domain.ArtistService;
import com.linicedev.music_artist_finder.itunes.infrastructure.client.HttpItunesApiClient;

@ExtendWith(MockitoExtension.class)
public class ArtistApplicationServiceTest {

    @Mock
    private HttpItunesApiClient httpItunesApiClient;

    private ArtistApplicationService artistApplicationService;

    @BeforeEach
    void setUp() {
        ArtistService artistService = new ArtistService(httpItunesApiClient);
        artistApplicationService = new ArtistApplicationService(artistService);
    }

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
