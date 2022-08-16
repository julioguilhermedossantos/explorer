package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import br.com.elo7.explorer.advice.excepion.ProbeNotFoundException;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.probe.model.Probe;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("Should throw UnknownPlanetException trying to create probe with invalid planet id")
    void create2() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile("probe-request-dto.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());

    }

    @Test
    @Sql({"/sql/planet-x5-y5.sql"})
    @DisplayName("Should throw OrbitalLimitExceededException trying to create probe with invalid position")
    void create3() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile(
                        "probe-request-dto-invalid-position.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrbitalLimitExceededException));

    }

    @Test
    @Sql({"/sql/planet-x5-y5.sql", "/sql/probe-x3-y3-planet-1-point-to-north.sql"})
    @DisplayName("Should throw CollisionExpection trying to create probe at same position")
    void create4() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post("/probes")
                .content(TestUtil.StringFromJsonFile("probe-request-dto.json", ProbeRequestDTO.class))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CollisionExpection));

    }

    @Test
    @Sql({"/sql/planet-x5-y5.sql", "/sql/probe-x3-y3-planet-1-point-to-north.sql"})
    @DisplayName("Should list probes successfully")
    void list() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = get("/probes")
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

        MockHttpServletRequestBuilder requestBuilder = get("/probes")
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['totalElements']").value("0"))
                .andExpect(jsonPath("$['content']").isEmpty());

    }

    @Test
    @Sql({"/sql/planet-x5-y5.sql", "/sql/probe-x3-y3-planet-1-point-to-north.sql"})
    @DisplayName("Should return probe by id successfully")
    void findById() throws Exception {

        var probeId = 1L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/probes/%d", probeId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        var result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        var probe = TestUtil.fromJsonString(result.getResponse().getContentAsString(), Probe.class);

        assertEquals(probeId, probe.getId());

    }

    @Test
    @DisplayName("Should throw ProbeNotFoundException")
    void findById2() throws Exception {

        var probeId = 99L;

        MockHttpServletRequestBuilder requestBuilder = get(String.format("/probes/%d", probeId))
                .contentType(APPLICATION_JSON)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProbeNotFoundException));

    }

    @Test
    void move() {
    }
}