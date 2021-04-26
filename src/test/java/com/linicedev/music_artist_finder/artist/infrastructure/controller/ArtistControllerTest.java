package com.linicedev.music_artist_finder.artist.infrastructure.controller;

import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_ID;
import static com.linicedev.music_artist_finder.TestDataConstants.ARTIST_SEARCH_QUERY;
import static com.linicedev.music_artist_finder.TestResourceUtils.readFile;
import static com.linicedev.music_artist_finder.artist.application.ArtistTopAlbumsTestDataBuilder.mockArtistTopAlbums;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockArtistSearchResult;
import static com.linicedev.music_artist_finder.artist.infrastructure.controller.ArtistSearchResultTestDataBuilder.mockEmptyArtistSearchResult;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.linicedev.music_artist_finder.artist.application.ArtistApplicationService;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArtistApplicationService artistApplicationService;

    @Nested
    class SearchArtists {

        @Test
        public void returnsArtistsGivenArtistsHaveBeenFound() throws Exception {
            given(artistApplicationService.searchArtists(ARTIST_SEARCH_QUERY)).willReturn(mockArtistSearchResult());

            // When
            MvcResult mvcResult = mvc.perform(get("/artists?query={query}", ARTIST_SEARCH_QUERY)
                                                  .contentType(APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();
            // Then
            assertJsonEquals(
                readFile("expected/artist-search-result.json"),
                mvcResult.getResponse().getContentAsString());
        }

        @Test
        public void returnsNoArtistsGivenNoArtistsHaveBeenFound() throws Exception {
            String unsuccessfulQueryKeyword = "unsuccessful";
            given(artistApplicationService.searchArtists(unsuccessfulQueryKeyword)).willReturn(mockEmptyArtistSearchResult());

            // When
            MvcResult mvcResult = mvc.perform(get("/artists?query={query}", unsuccessfulQueryKeyword))
                                      .andExpect(status().isOk())
                                      .andReturn();

            // Then
            assertJsonEquals(
                readFile("expected/empty-artist-search-result.json"),
                mvcResult.getResponse().getContentAsString());
        }
    }

    @Nested
    class GetArtistAlbums {

        @Test
        public void returnsArtistTopAlbums() throws Exception {
            given(artistApplicationService.findArtistTopAlbums(ARTIST_ID)).willReturn(mockArtistTopAlbums());

            // When
            MvcResult mvcResult = mvc.perform(get("/artist/{artistId}/top-albums", ARTIST_ID)
                                                  .contentType(APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();
            // Then
            assertJsonEquals(
                readFile("expected/artist-top-albums.json"),
                mvcResult.getResponse().getContentAsString());
        }

        @Test
        public void doesNotReturnArtistTopAlbumsGivenNoTopAlbumsAreFound() throws Exception {
            given(artistApplicationService.findArtistTopAlbums(ARTIST_ID)).willReturn(mockArtistTopAlbums().withTopAlbums(List.of()));

            // When
            MvcResult mvcResult = mvc.perform(get("/artist/{artistId}/top-albums", ARTIST_ID)
                                                  .contentType(APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();
            // Then
            assertJsonEquals(
                readFile("expected/empty-artist-top-albums.json"),
                mvcResult.getResponse().getContentAsString());
        }

    }
}
