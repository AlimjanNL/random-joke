package nl.ns.randomjoke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  private final RestClientProps restClientProps;

  public RestClientConfig(RestClientProps restClientProps) {
    this.restClientProps = restClientProps;
  }

  @Bean
  RestClient restClient() {
    return RestClient.create(restClientProps.baseUrl());
  }
}
