package com.telesoftas.cityget.service;

import com.telesoftas.cityget.model.City;
import com.telesoftas.cityget.repository.CitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitiesService {
    private final CitiesRepository citiesRepository;


    public Page<City> findAllCities(Pageable pageable) {
        return citiesRepository.findAll(pageable);
    }
}
