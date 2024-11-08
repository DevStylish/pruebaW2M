package com.moisesmartin.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StarshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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

    @ElementCollection
    private List<String> films;

    @ElementCollection
    private List<String> pilots;

    private String starshipImage;

    private String created;

    private String edited;

}
