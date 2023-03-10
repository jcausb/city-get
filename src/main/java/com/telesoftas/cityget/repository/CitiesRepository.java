package com.telesoftas.cityget.repository;

import com.telesoftas.cityget.model.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends PagingAndSortingRepository<City, Long> {
    City findByName(String name);

    City saveCity(City city);

    City findById(Long id);
}
