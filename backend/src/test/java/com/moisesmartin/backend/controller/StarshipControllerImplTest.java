package com.moisesmartin.backend.controller;

import com.moisesmartin.backend.dto.CreateStarshipDto;
import com.moisesmartin.backend.model.StarshipEntity;
import com.moisesmartin.backend.services.StarshipEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StarshipControllerImplTests {

    @Mock
    private StarshipEntityService starshipEntityService;

    @InjectMocks
    private StarshipControllerImpl starshipController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<StarshipEntity> starships = new ArrayList<>();
        StarshipEntity enterprise = StarshipEntity.builder()
                .id(1L)
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        StarshipEntity falcon = StarshipEntity.builder()
                .id(2L)
                .name("Millennium Falcon")
                .model("YT-1300 light freighter")
                .starshipClass("Light freighter")
                .manufacturer("Corellian Engineering Corporation")
                .costInCredits(100000)
                .length((int) 34.37)
                .crew(4)
                .passengers(6)
                .maxAtmospheringSpeed("1050")
                .MGLT(String.valueOf(75))
                .cargoCapacity(100000)
                .consumables("2 months")
                .films(List.of("Star Wars: Episode IV - A New Hope", "Star Wars: Episode V - The Empire Strikes Back"))
                .pilots(List.of("Han Solo", "Chewbacca"))
                .starshipImage("https://example.com/falcon.png")
                .created("2023-03-20T09:15:00Z")
                .edited("2023-03-25T18:45:00Z")
                .build();
        starships.add(enterprise);
        starships.add(falcon);
        when(starshipEntityService.findAll()).thenReturn(starships);

        ResponseEntity<?> response = starshipController.findAll();

        assertEquals(starships, response.getBody());
    }

    @Test
    void testFindAllWithException() {
        when(starshipEntityService.findAll()).thenThrow(new RuntimeException("Error fetching starships"));

        ResponseEntity<?> response = starshipController.findAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching starships", response.getBody());
    }

    @Test
    void testFindById() {

        Long id = 1L;
        StarshipEntity enterprise = StarshipEntity.builder()
                .id(id)
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        when(starshipEntityService.findById(id)).thenReturn(Optional.of(enterprise));


        ResponseEntity<?> response = starshipController.findById(id);


        assertEquals(Optional.of(enterprise), response.getBody());
    }

    @Test
    void testFindByIdWithException() {
        Long id = 1L;
        when(starshipEntityService.findById(id)).thenThrow(new RuntimeException("Error fetching starship"));

        ResponseEntity<?> response = starshipController.findById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error fetching starship", response.getBody());
    }

    @Test
    void testContainsName() {

        String name = "Enterprise";
        StarshipEntity starship1 = StarshipEntity.builder()
                .id(1L)
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        StarshipEntity starship2 = StarshipEntity.builder()
                .id(1L)
                .name("Enterprise 2")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        when(starshipEntityService.findStarshipByName(name)).thenReturn(Optional.of(List.of(starship1, starship2)));

        ResponseEntity<?> response = starshipController.containsName(name);

        assertEquals(List.of(starship1, starship2), response.getBody());
    }

    @Test
    void testContainsNameOptionalNotPresent() {
        String name = "prueba";

        when(starshipEntityService.findStarshipByName(name)).thenReturn(Optional.empty());

        ResponseEntity<?> response = starshipController.containsName(name);

        assertEquals(List.of(), response.getBody());
    }

    @Test
    void testContainsNameWithException() {

        String name = "Enterprise";
        when(starshipEntityService.findStarshipByName(name)).thenThrow(new RuntimeException("Error fetching starships"));


        ResponseEntity<?> response = starshipController.containsName(name);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error fetching starships", response.getBody());
    }

    @Test
    void testCreateStarship() {

        CreateStarshipDto createStarshipDto = CreateStarshipDto.builder()
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        StarshipEntity createdStarship = StarshipEntity.builder()
                .id(1L)
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        when(starshipEntityService.addStarship(createStarshipDto)).thenReturn(createdStarship);


        ResponseEntity<?> response = starshipController.createStarship(createStarshipDto);


        assertEquals(createdStarship, response.getBody());
    }

    @Test
    void testCreateStarshipWithException() {

        CreateStarshipDto createStarshipDto = mock(CreateStarshipDto.class);
        when(starshipEntityService.addStarship(createStarshipDto)).thenThrow(new RuntimeException("Error creating starship"));


        ResponseEntity<?> response = starshipController.createStarship(createStarshipDto);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error creating starship", response.getBody());
    }

    @Test
    void testUpdateStarship() {

        StarshipEntity updatedStarship = StarshipEntity.builder()
                .id(1L)
                .name("Enterprise")
                .model("NCC-1701")
                .starshipClass("Constitution class")
                .manufacturer("Starfleet")
                .costInCredits(40000000)
                .length(289)
                .crew(430)
                .passengers(100)
                .maxAtmospheringSpeed("Warp 8")
                .MGLT(String.valueOf(8))
                .cargoCapacity(100000)
                .consumables("2 years")
                .films(List.of("Star Trek: The Motion Picture", "Star Trek II: The Wrath of Khan"))
                .pilots(List.of("James T. Kirk", "Spock"))
                .starshipImage("https://example.com/enterprise.png")
                .created("2023-04-05T12:00:00Z")
                .edited("2023-04-10T15:30:00Z")
                .build();
        when(starshipEntityService.updateStarship(updatedStarship)).thenReturn(updatedStarship);


        ResponseEntity<?> response = starshipController.updateStarship(updatedStarship);


        assertEquals(updatedStarship, response.getBody());
    }

    @Test
    void testUpdateStarshipWithException() {

        StarshipEntity updatedStarship = mock(StarshipEntity.class);
        when(starshipEntityService.updateStarship(updatedStarship)).thenThrow(new RuntimeException("Error updating starship"));


        ResponseEntity<?> response = starshipController.updateStarship(updatedStarship);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error updating starship", response.getBody());
    }

    @Test
    void testRemoveById() {

        Long id = 1L;
        doNothing().when(starshipEntityService).removeStarship(id);


        ResponseEntity<?> response = starshipController.removeById(id);


        assertEquals(
                ResponseEntity.ok().body(Map.of("message", String.format("Starship %d successfully removed", id))).getBody(),
                response.getBody()
        );
    }

    @Test
    void testRemoveByIdWithException() {

        Long id = 1L;
        doThrow(new RuntimeException("Error removing starship")).when(starshipEntityService).removeStarship(id);


        ResponseEntity<?> response = starshipController.removeById(id);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(
                ResponseEntity.badRequest().body(Map.of("error", "Error removing starship")).getBody(),
                response.getBody()
        );
    }
}