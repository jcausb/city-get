package com.telesoftas.cityget.integration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.telesoftas.cityget.util.TestUtils.getJsonFromResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/init.sql"})
class CityGetControllerIT {
    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getCitiesReturnsCities() {
        mockMvc.perform(get("/api/v1/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonFromResource("integration/response/default.json")));
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = "Tokyo")
    void getCityByName(String name) {
        mockMvc.perform(get("/api/v1/cities/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonFromResource("integration/response/tokyo.json")));
    }
}
