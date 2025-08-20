package io.mosip.commons.packetmanager.config;

import brave.Tracer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "spring.sleuth.enabled", matchIfMissing = true)
@Configuration
public class MySleuthLoggingConfig {

    @Bean
    public MySleuthValve mySleuthValve(Tracer tracer) {
        return new MySleuthValve(tracer);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer(MySleuthValve mySleuthValve) {
        return factory -> factory.addContextCustomizers(
                context -> context.getPipeline().addValve(mySleuthValve));
    }
}
