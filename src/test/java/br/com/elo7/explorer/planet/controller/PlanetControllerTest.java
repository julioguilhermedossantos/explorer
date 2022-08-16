package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.util.TestUtil;
import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should create planet successfully")
    void create() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/planets")
                .content(TestUtil.StringFromJsonFile("planet-request-dto.json", PlanetRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should throw MethodArgumentNotValidException")
    void create2() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/planets")
                .content(TestUtil.StringFromJsonFile("planet-request-dto-invalid.json", PlanetRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }

    @Test
    @Sql("/sql/planet-x5-y5.sql")
    @DisplayName("Should list planets successfully")
    void list() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get("/planets")
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['totalElements']").value("1"))
                .andExpect(jsonPath("$['content']").isNotEmpty());
    }

    @Test
    @DisplayName("Should return empty page")
    void list2() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get("/planets")
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("0"));

    }

    @Test
    @Sql("/sql/planet-x5-y5.sql")
    @DisplayName("Should return planet by id successfully")
    void findById() throws Exception {

        var planetId = 1L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/planets/%d", planetId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        var planet = TestUtil.fromJsonString(result.getResponse().getContentAsString(), Planet.class);

        assertEquals(planetId, planet.getId());

    }

    @Test
    @DisplayName("Should throw UnknownPlanetException")
    void findById2() throws Exception {

        var planetId = 99L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/planets/%d", planetId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownPlanetException));

    }

}