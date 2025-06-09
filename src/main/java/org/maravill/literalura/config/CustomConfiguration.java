package org.maravill.literalura.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.maravill.literalura.services.MapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MapperService mapperService() {
        return new MapperService();
    }
}
