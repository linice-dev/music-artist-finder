package com.linicedev.music_artist_finder.user.infrastructure.controller;

import static com.linicedev.music_artist_finder.TestDataConstants.FAVOURITE_ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.USER_ID;
import static com.linicedev.music_artist_finder.TestResourceUtils.readFile;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.linicedev.music_artist_finder.user.api.ImmutableSaveFavouriteArtistCommand;
import com.linicedev.music_artist_finder.user.api.ImmutableUserFavouriteArtist;
import com.linicedev.music_artist_finder.user.application.UserApplicationService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserApplicationService userApplicationService;

    @Nested
    class SaveFavouriteArtist {

        @Test
        public void savesFavouriteArtist() throws Exception {
            // When
            mvc.perform(post("/user/{userId}/save-favourite-artist", USER_ID)
                            .contentType(APPLICATION_JSON)
                            .content(readFile("payload/save-favourite-artist.json")))
                .andExpect(status().isNoContent())
                .andReturn();

            // Then
            verify(userApplicationService).saveFavouriteArtist(USER_ID, ImmutableSaveFavouriteArtistCommand.of(FAVOURITE_ARTIST_ID));
        }
    }

    @Nested
    class GetFavouriteArtist {

        @Test
        public void returnsFavouriteArtistIdGivenUserFavouriteArtistIsFound() throws Exception {
            // Given
            given(userApplicationService.findFavouriteArtist(USER_ID)).willReturn(ImmutableUserFavouriteArtist.of(of(FAVOURITE_ARTIST_ID)));

            // When
            MvcResult mvcResult = mvc.perform(get("/user/{userId}/favourite-artist", USER_ID))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(APPLICATION_JSON))
                                      .andReturn();

            // Then
            assertJsonEquals(
                readFile("expected/user-favourite-artist.json"),
                mvcResult.getResponse().getContentAsString());
        }

        @Test
        public void returnsEmptyResponseGivenUserFavouriteArtistIsNotFound() throws Exception {
            // Given
            given(userApplicationService.findFavouriteArtist(USER_ID)).willReturn(ImmutableUserFavouriteArtist.of(empty()));

            // When
            MvcResult mvcResult = mvc.perform(get("/user/{userId}/favourite-artist", USER_ID))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(APPLICATION_JSON))
                                      .andReturn();

            // Then
            String expectedResponse = "{}";
            assertJsonEquals(expectedResponse, mvcResult.getResponse().getContentAsString());
        }
    }

}
