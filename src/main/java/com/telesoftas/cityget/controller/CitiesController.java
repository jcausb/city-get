package com.telesoftas.cityget.controller;

import com.telesoftas.cityget.model.City;
import com.telesoftas.cityget.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CitiesController {
    private final CitiesService citiesService;

    @GetMapping
    public Page<City> getCities(@RequestParam(defaultValue = "${pagination.cities.default.page}") int page,
                                @RequestParam(defaultValue = "${pagination.cities.default.size}") int size,
                                @RequestParam(defaultValue = "${pagination.cities.default.sort}") String sort) {
        Pageable pageable = PageRequest.of(page, size, sortBy(sort));
        return citiesService.findAllCities(pageable);
    }

    @GetMapping("/{name}")
    public City getCityByName(@PathVariable("name") String name) {
        return citiesService.findCityByName(name);
    }

    private Sort sortBy(String sort) {
        String[] sortParts = sort.split(",");
        return Sort.by(Sort.Direction.fromString(sortParts[1]), sortParts[0]);
    }
}
