package com.moisesmartin.backend.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateStarshipDto {

    private String name;
    private String model;
    private String starshipClass;
    private String manufacturer;
    private Integer costInCredits;
    private Integer length;
    private Integer crew;
    private Integer passengers;
    private String maxAtmospheringSpeed;
    private String MGLT;
    private Integer cargoCapacity;
    private String consumables;
    private List<String> films;
    private List<String> pilots;
    private String starshipImage;
    private String created;
    private String edited;

}
