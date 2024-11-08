package com.moisesmartin.backend.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.moisesmartin.backend.dto.CreateStarshipDto;
import com.moisesmartin.backend.model.StarshipEntity;
import com.moisesmartin.backend.services.StarshipEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/starship")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200/"})
@EnableAutoConfiguration
public class StarshipControllerImpl implements StarshipController {

    private final StarshipEntityService starshipEntityService;
    private final Cache<Long, Optional<StarshipEntity>> starshipCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    @Override
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(this.starshipEntityService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Optional<StarshipEntity> starship = starshipCache.get(id, this.starshipEntityService::findById);
            return ResponseEntity.ok().body(starship);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/name/{name}")
    public ResponseEntity<?> containsName(@PathVariable String name) {

        try {
            Optional<List<StarshipEntity>> starships = starshipEntityService.findStarshipByName(name);
            if (starships.isPresent()) {
                starships.get().forEach(starship -> starshipCache.put(starship.getId(), Optional.of(starship)));
                return ResponseEntity.ok().body(starships.get());
            } else {
                return ResponseEntity.ok().body(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<?> createStarship(@RequestBody CreateStarshipDto starship) {

        try {
            return ResponseEntity.ok().body(this.starshipEntityService.addStarship(starship));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PatchMapping("/")
    public ResponseEntity<?> updateStarship(StarshipEntity starship) {
        try {
            StarshipEntity updatedStarship = this.starshipEntityService.updateStarship(starship);
            starshipCache.put(updatedStarship.getId(), Optional.of(updatedStarship));
            return ResponseEntity.ok().body(updatedStarship);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) {
        try {
            this.starshipEntityService.removeStarship(id);
            return ResponseEntity.ok()
                    .body(Map.of("message", String.format("Starship %d successfully removed", id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
