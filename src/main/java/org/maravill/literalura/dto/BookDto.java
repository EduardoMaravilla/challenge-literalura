package org.maravill.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDto(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("authors")List<PersonDto> authors,
        @JsonProperty("summaries") List<String> sumaries,
        @JsonProperty("translators") List<PersonDto> translators,
        @JsonProperty("subjects") List<String> subjects,
        @JsonProperty("bookshelves") List<String> bookshelves,
        @JsonProperty("languages") List<String> languages,
        @JsonProperty("copyright") Boolean copyright,
        @JsonProperty("media_type") String mediaType,
        @JsonProperty("formats") Map<String,String> formats,
        @JsonProperty("download_count") Integer downloadCount
        ) {
}
