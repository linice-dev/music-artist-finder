package com.linicedev.music_artist_finder.user.application;

import static com.linicedev.music_artist_finder.TestDataConstants.FAVOURITE_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.USER_ID;
import static com.linicedev.music_artist_finder.user.application.UserEntityTestDataBuilder.mockUserEntity;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linicedev.music_artist_finder.user.api.ImmutableSaveFavouriteArtistCommand;
import com.linicedev.music_artist_finder.user.api.UserFavouriteArtist;
import com.linicedev.music_artist_finder.user.domain.UserEntity;
import com.linicedev.music_artist_finder.user.domain.UserFavouriteArtistService;
import com.linicedev.music_artist_finder.user.domain.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceTest {

    private static final UserEntity USER_ENTITY = mockUserEntity();

    @Mock
    private UserRepository userRepository;

    private UserApplicationService userApplicationService;

    @BeforeEach
    void setUp() {
        UserFavouriteArtistService saveUserFavouriteArtistService = new UserFavouriteArtistService(userRepository);
        userApplicationService = new UserApplicationService(saveUserFavouriteArtistService);
    }

    @Nested
    class SaveFavouriteArtist {

        @Test
        void savesUserWithFavouriteArtistIdGivenUserIsFound() {
            // Given
            Long favouriteArtistId = 456L;
            given(userRepository.findById(USER_ID)).willReturn(of(USER_ENTITY));

            // When
            userApplicationService.saveFavouriteArtist(USER_ID, ImmutableSaveFavouriteArtistCommand.of(favouriteArtistId));

            // Then
            UserEntity expectedUserEntity = new UserEntity(USER_ID, favouriteArtistId);

            ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
            verify(userRepository).save(userEntityArgumentCaptor.capture());

            assertThat(userEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("updated")
                .isEqualTo(expectedUserEntity);
        }

        @Test
        void savesUserWithFavouriteArtistIdGivenUserIsNotFound() {
            // Given
            given(userRepository.findById(USER_ID)).willReturn(empty());

            // When
            userApplicationService.saveFavouriteArtist(USER_ID, ImmutableSaveFavouriteArtistCommand.of(FAVOURITE_ARTIST_ID));

            // Then
            UserEntity expectedUserEntity = new UserEntity(USER_ID, FAVOURITE_ARTIST_ID);

            ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
            verify(userRepository).save(userEntityArgumentCaptor.capture());

            assertThat(userEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("updated")
                .isEqualTo(expectedUserEntity);
        }
    }

    @Nested
    class FindFavouriteArtist {
        @Test
        void findsUserFavouriteArtistGivenUserFavouriteArtistExists() {
            // Given
            given(userRepository.findById(USER_ID)).willReturn(of(USER_ENTITY));

            // When
            UserFavouriteArtist userFavouriteArtist = userApplicationService.findFavouriteArtist(USER_ID);

            // Then
            assertThat(userFavouriteArtist.getFavouriteArtistId().isPresent()).isTrue();
            assertThat(userFavouriteArtist.getFavouriteArtistId().get()).isEqualTo(FAVOURITE_ARTIST_ID);
        }

        @Test
        void doesNotFindUserFavouriteArtistGivenUserFavouriteArtistDoesNotExist() {
            // Given
            given(userRepository.findById(USER_ID)).willReturn(empty());

            // When
            UserFavouriteArtist userFavouriteArtist = userApplicationService.findFavouriteArtist(USER_ID);

            // Then
            assertThat(userFavouriteArtist.getFavouriteArtistId().isEmpty()).isTrue();
        }
    }
}
