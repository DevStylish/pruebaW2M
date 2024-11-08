package com.moisesmartin.backend.controller;

import com.moisesmartin.backend.dto.CreateStarshipDto;
import com.moisesmartin.backend.model.StarshipEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface StarshipController {

    @GetMapping
    ResponseEntity<?> findAll();

    @GetMapping
    ResponseEntity<?> findById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<?> containsName(@PathVariable String name);

    @PostMapping
    ResponseEntity<?> createStarship(@RequestBody CreateStarshipDto createStarshipDto);

    @PatchMapping
    ResponseEntity<?> updateStarship(@RequestBody StarshipEntity StarshipEntity);

    @DeleteMapping
    ResponseEntity<?> removeById(@PathVariable Long id);


}
