package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/planets")
                .content(TestUtil.StringFromJsonFile("planet-request-dto.json", PlanetRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

    }

    @Test
    @Sql("/sql/get-all-planets.sql")
    @DisplayName("Should list planets correctly")
    void getAllPlanets() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get("/planets")
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        var planets = TestUtil.fromJsonString(result.getResponse().getContentAsString(), Planet[].class);

        assertTrue(Arrays.stream(planets).findAny().isPresent());

    }

    @Test
    @DisplayName("Should return empty array")
    void getAllPlanets2() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get("/planets")
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        var emptyArray = TestUtil.fromJsonString(result.getResponse().getContentAsString(), Object[].class);

        assertFalse(Arrays.stream(emptyArray).findAny().isPresent());

    }

    @Test
    @Sql("/sql/get-all-planets.sql")
    @DisplayName("Should return planet by id")
    void getPlanet() throws Exception {

        var planetId = 1L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/planets/%d", planetId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        var planets = TestUtil.fromJsonString(result.getResponse().getContentAsString(), Planet.class);

        assertEquals(planetId, planets.getId());

    }

    @Test
    @DisplayName("Should throw UnknownPlanetException")
    void getPlanet2() throws Exception {

        var planetId = 99L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/planets/%d", planetId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

       mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownPlanetException));

    }
}