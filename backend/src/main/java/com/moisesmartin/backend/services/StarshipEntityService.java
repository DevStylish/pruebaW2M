package com.moisesmartin.backend.services;

import com.moisesmartin.backend.dto.CreateStarshipDto;
import com.moisesmartin.backend.dto.StarshipDtoConverter;
import com.moisesmartin.backend.model.StarshipEntity;
import com.moisesmartin.backend.repos.StarshipEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarshipEntityService extends BaseService<StarshipEntity, Long, StarshipEntityRepository> {

    /**
     * Encuentra una nave por nombre
     * @param name Nombre de la nave a buscar
     */
    public Optional<List<StarshipEntity>> findStarshipByName(String name){
        return this.repository.searchByNameIsLikeIgnoreCase("%" + name + "%");
    }

    /**
     * Crea una StarshipEntity en base de datos usando CreateStarshipDto
     * @param newStarship Objeto CreateStarshipDto
     */
    public StarshipEntity addStarship(CreateStarshipDto newStarship){

        StarshipEntity starshipEntity = StarshipDtoConverter.convertCreateStarshipDtoToStarship(newStarship);

        try {
            return save(starshipEntity);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Starship name already exists");
        }

    }

    /**
     * Actualiza un StarshipEntity en base de datos
     * @param starship Objeto StarshipEntity
     */
    public StarshipEntity updateStarship(StarshipEntity starship){
        try{
            return edit(starship);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update the starship " + starship.getId());
        }
    }

    /**
     * Elimina una nave en base de datos
     * @param id Id de la nave a eliminar
     */
    public void removeStarship(Long id){
        try{
            this.repository.deleteById(id);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update the starship " + id);
        }
    }

}
