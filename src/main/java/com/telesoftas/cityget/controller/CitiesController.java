package com.telesoftas.cityget.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telesoftas.cityget.model.City;
import com.telesoftas.cityget.model.dto.CityUpdateRequest;
import com.telesoftas.cityget.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CitiesController {
    private final CitiesService citiesService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Page<City>> getCities(@RequestParam(defaultValue = "${pagination.cities.default.page}") int page,
                                                @RequestParam(defaultValue = "${pagination.cities.default.size}") int size,
                                                @RequestParam(defaultValue = "${pagination.cities.default.sort}") String sort) {
        Pageable pageable = PageRequest.of(page, size, sortBy(sort));
        return ResponseEntity.ok(citiesService.findAllCities(pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(citiesService.findCityByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateUserById(@PathVariable("id") Long id,
                                               @RequestBody String jsonBody) throws JsonProcessingException {
        City city = citiesService.findCityById(id);
        if (isNull(city)) return ResponseEntity.notFound().build();
        CityUpdateRequest request = objectMapper.readValue(jsonBody, CityUpdateRequest.class);
        return ResponseEntity.ok(citiesService.updateCity(city, request));
    }

    private Sort sortBy(String sort) {
        String[] sortParts = sort.split(",");
        return Sort.by(Sort.Direction.fromString(sortParts[1]), sortParts[0]);
    }
}
