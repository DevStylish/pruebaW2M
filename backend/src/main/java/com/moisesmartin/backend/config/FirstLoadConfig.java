package com.moisesmartin.backend.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.moisesmartin.backend.model.StarshipEntity;
import com.moisesmartin.backend.services.StarshipEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirstLoadConfig implements CommandLineRunner {

    private final StarshipEntityService repository;
    private final RestTemplate restTemplate;

    @Value("${api.url:https://www.swapi.tech/api/starships/}")
    private String baseUrl;

    @Override
    public void run(String... args) {
        log.info("Initiating spacecraft data loading...");
        try {
            String apiUrl = baseUrl;

            ResponseEntity<JsonNode> response = restTemplate.getForEntity(apiUrl, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode results = response.getBody().get("results");

                if (results.isArray()) {
                    for (JsonNode result : results) {
                        String detailUrl = result.get("url").asText();
                        String uid = result.get("uid").asText();

                        ResponseEntity<JsonNode> detailResponse = restTemplate.getForEntity(
                                detailUrl,
                                JsonNode.class
                        );

                        if (detailResponse.getStatusCode() == HttpStatus.OK && detailResponse.getBody() != null) {
                            JsonNode properties = detailResponse.getBody()
                                    .get("result")
                                    .get("properties");

                            StarshipEntity starship = StarshipEntity.builder()
                                    .name(getStringValue(properties, "name"))
                                    .model(getStringValue(properties, "model"))
                                    .starshipClass(getStringValue(properties, "starship_class"))
                                    .manufacturer(getStringValue(properties, "manufacturer"))
                                    .costInCredits(parseInteger(getStringValue(properties, "cost_in_credits")))
                                    .length(parseInteger(getStringValue(properties, "length")))
                                    .crew(parseInteger(getStringValue(properties, "crew")))
                                    .passengers(parseInteger(getStringValue(properties, "passengers")))
                                    .maxAtmospheringSpeed(getStringValue(properties, "max_atmosphering_speed"))
                                    .MGLT(getStringValue(properties, "MGLT"))
                                    .cargoCapacity(parseInteger(getStringValue(properties, "cargo_capacity")))
                                    .consumables(getStringValue(properties, "consumables"))
                                    .films(getListValue(properties, "films"))
                                    .pilots(getListValue(properties, "pilots"))
                                    .starshipImage(String.format("https://starwars-visualguide.com/assets/img/starships/%s.jpg", uid))
                                    .created(getStringValue(properties, "created"))
                                    .edited(getStringValue(properties, "edited"))
                                    .build();

                            repository.save(starship);
                            log.info("Spaceship stored: {}", starship.getName());
                        }
                    }
                }
                log.info("Data upload successfully completed");
            }
        } catch (Exception e) {
            log.error("Error loading data: {}", e.getMessage(), e);
        }
    }

    private String getStringValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private List<String> getListValue(JsonNode node, String field) {
        if (node.has(field) && node.get(field).isArray()) {
            List<String> values = new ArrayList<>();
            node.get(field).forEach(item -> values.add(item.asText()));
            return values;
        }
        return new ArrayList<>();
    }

    private Integer parseInteger(String value) {
        try {
            if (value == null || value.equals("unknown") || value.equals("n/a")) {
                return null;
            }
            return Integer.parseInt(value.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
