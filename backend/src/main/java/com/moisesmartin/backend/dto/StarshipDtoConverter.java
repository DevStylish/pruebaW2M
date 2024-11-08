package com.moisesmartin.backend.dto;

import com.moisesmartin.backend.model.StarshipEntity;

public class StarshipDtoConverter {

    /**
     * Convierte StarshipEntity a CreateStarshipDto
     * @param starship Objeto StarshipEntity
     */
    public static CreateStarshipDto convertStarhipToCreateStarshipDto(StarshipEntity starship) {
        return CreateStarshipDto.builder()
                .name(starship.getName())
                .model(starship.getModel())
                .starshipClass(starship.getStarshipClass())
                .manufacturer(starship.getManufacturer())
                .costInCredits(starship.getCostInCredits())
                .length(starship.getLength())
                .crew(starship.getCrew())
                .passengers(starship.getPassengers())
                .maxAtmospheringSpeed(starship.getMaxAtmospheringSpeed())
                .MGLT(starship.getMGLT())
                .cargoCapacity(starship.getCargoCapacity())
                .consumables(starship.getConsumables())
                .films(starship.getFilms())
                .pilots(starship.getPilots())
                .starshipImage(starship.getStarshipImage())
                .build();
    }

    /**
     * Convierte CreateStarshipDto a StarshipEntity
     * @param createStarshipDto Objeto CreateStarshipDto
     */
    public static StarshipEntity convertCreateStarshipDtoToStarship(CreateStarshipDto createStarshipDto){
        return StarshipEntity.builder()
                .name(createStarshipDto.getName())
                .model(createStarshipDto.getModel())
                .starshipClass(createStarshipDto.getStarshipClass())
                .manufacturer(createStarshipDto.getManufacturer())
                .costInCredits(createStarshipDto.getCostInCredits())
                .length(createStarshipDto.getLength())
                .crew(createStarshipDto.getCrew())
                .passengers(createStarshipDto.getPassengers())
                .maxAtmospheringSpeed(createStarshipDto.getMaxAtmospheringSpeed())
                .MGLT(createStarshipDto.getMGLT())
                .cargoCapacity(createStarshipDto.getCargoCapacity())
                .consumables(createStarshipDto.getConsumables())
                .films(createStarshipDto.getFilms())
                .pilots(createStarshipDto.getPilots())
                .starshipImage(createStarshipDto.getStarshipImage())
                .created(createStarshipDto.getCreated())
                .edited(createStarshipDto.getEdited())
                .build();
    }
}
