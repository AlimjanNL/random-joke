package nl.ns.randomjoke.client.impl;

import lombok.extern.slf4j.Slf4j;
import nl.ns.randomjoke.client.FetchJokeClient;
import nl.ns.randomjoke.client.model.RandomJokeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Service
@Slf4j
public class FetchJokeClientImpl implements FetchJokeClient {

  @Value("${random-joke.api.path:/joke/Any}")
  private String defaultPath;

  @Value("${random-joke.api.params.type:single}")
  private String defaultType;

  @Value("${random-joke.api.params.amount:16}")
  private int defaultAmount;

  private final RestClient restClient;

  public FetchJokeClientImpl(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public RandomJokeResponse fetchRandomJoke() {
    log.info("Fetching random joke");

    Function<UriBuilder, URI> uri = uriBuilder -> uriBuilder
        .path(defaultPath)
        .queryParam("type", defaultType)
        .queryParam("amount", defaultAmount)
        .build();

    return this.restClient.get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(RandomJokeResponse.class);
  }
}
