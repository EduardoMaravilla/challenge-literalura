package org.maravill.literalura.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PersonDto(
        @JsonProperty("birth_year") Integer birthYear,
        @JsonProperty("death_year") Integer deathYear,
        @JsonProperty("name") String name) {
}
