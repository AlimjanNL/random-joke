package nl.ns.randomjoke.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "random-joke.api")
public record RestClientProps(String baseUrl, String path, String type, String amount) {

}
