package nl.ns.randomjoke.service.impl;

import java.net.URI;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import nl.ns.randomjoke.dto.Joke;
import nl.ns.randomjoke.dto.JokeDTO;
import nl.ns.randomjoke.dto.RandomJokeResponse;
import nl.ns.randomjoke.service.RandomJokeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

@Service
@Slf4j
public class RandomJokeServiceImpl implements RandomJokeService {

  @Value("${random-joke.api.path:/joke/Any}")
  private String defaultPath;

  @Value("${random-joke.api.params.type:single}")
  private String defaultType;

  @Value("${random-joke.api.params.amount:16}")
  private int defaultAmount;

  private final RestClient restClient;

  public RandomJokeServiceImpl(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public RandomJokeResponse fetchRandomJoke(String path, String type, int amount) {
    log.info("Fetching random joke");

    Function<UriBuilder, URI> uri = uriBuilder -> uriBuilder
        .path(path)
        .queryParam("type", type)
        .queryParam("amount", amount)
        .build();

    return this.restClient.get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(RandomJokeResponse.class);
  }

  @Override
  public Optional<JokeDTO> getRandomJoke() {
    return getRandomJoke(defaultPath, defaultType, defaultAmount);
  }

  @Override
  public Optional<JokeDTO> getRandomJoke(String path, String type, int amount) {
    RandomJokeResponse randomJokeResponse = this.fetchRandomJoke(path, type, amount);

    log.info("Filtering random joke");

    return randomJokeResponse.jokes().stream()
        .filter(Joke::safe)
        .filter(joke -> !joke.flags().sexist())
        .filter(joke -> !joke.flags().explicit())
        .min(Comparator.comparingInt(joke -> joke.joke().length()))
        .map(JokeDTO::toDTO);
  }
}
