package com.esprit.gestionrestaurants.config;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.boot.jackson2.autoconfigure.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tolère les nombres envoyés en chaîne par Angular (ex. {@code "fraisLivraison": "5"} depuis un input texte).
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCoercionCustomizer() {
        return builder -> builder.postConfigurer(objectMapper -> {
            objectMapper
                    .coercionConfigFor(LogicalType.Float)
                    .setCoercion(CoercionInputShape.String, CoercionAction.TryConvert);
            objectMapper
                    .coercionConfigFor(LogicalType.Integer)
                    .setCoercion(CoercionInputShape.String, CoercionAction.TryConvert);
        });
    }
}
