package com.telesoftas.cityget.integration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.telesoftas.cityget.util.TestUtils.buildUpdateJson;
import static com.telesoftas.cityget.util.TestUtils.getJsonFromResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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

    @SneakyThrows
    @ParameterizedTest
    @CsvSource({"1, Vilnius, https://en.wikipedia.org/wiki/Vilnius#/media/File:Vilnius_old_town_by_Augustas_Didzgalvis.jpg"})
    void updateCityById(String id, String name, String photo) {
        mockMvc.perform(put("/api/v1/cities/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildUpdateJson(name, photo)))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonFromResource("integration/response/vilnius.json")));

        mockMvc.perform(get("/api/v1/cities/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonFromResource("integration/response/vilnius.json")));
    }
}
