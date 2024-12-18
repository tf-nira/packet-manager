package io.mosip.commons.packetmanager.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PacketServiceConfig {

    @Bean
    @Primary
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new AfterburnerModule()).registerModule(new JavaTimeModule());
       objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
