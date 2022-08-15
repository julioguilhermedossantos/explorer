package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.advice.ErrorMessage;
import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProbeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Sql("/sql/planet-x5-y5.sql")
    @DisplayName("Should create probe successfully")
    void create() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile("probe-request-dto.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should throw UnknownPlanetException trying to create probe with invalid id")
    void create2() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile("probe-request-dto.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should throw MethodArgumentNotValidException trying to create probe with invalid id")
    void create3() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile("probe-request-dto-invalid-position.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    void list() {
    }

    @Test
    void getProbe() {
    }

    @Test
    void moveProbe() {
    }
}