package com.telesoftas.cityget.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CitiesController {

    @GetMapping
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(List.of());
    }
}
