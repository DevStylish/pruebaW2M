package com.moisesmartin.backend.repos;

import com.moisesmartin.backend.model.StarshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarshipEntityRepository extends JpaRepository<StarshipEntity, Long> {

    Optional<List<StarshipEntity>> searchByNameIsLikeIgnoreCase(String name);

}
