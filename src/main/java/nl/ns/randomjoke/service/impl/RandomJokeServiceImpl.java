package nl.ns.randomjoke.service.impl;

import java.util.Comparator;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import nl.ns.randomjoke.client.FetchJokeClient;
import nl.ns.randomjoke.client.model.Joke;
import nl.ns.randomjoke.client.model.RandomJokeResponse;
import nl.ns.randomjoke.dto.JokeDTO;
import nl.ns.randomjoke.service.RandomJokeService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RandomJokeServiceImpl implements RandomJokeService {

  private final FetchJokeClient fetchJokeClient;
  public RandomJokeServiceImpl(FetchJokeClient fetchJokeClient) {
    this.fetchJokeClient = fetchJokeClient;
  }

  @Override
  public Optional<JokeDTO> getRandomJoke() {
    RandomJokeResponse randomJokeResponse = this.fetchJokeClient.fetchRandomJoke();

    log.info("Filtering random joke");

    return randomJokeResponse.jokes().stream()
        .filter(Joke::safe)
        .filter(joke -> !joke.flags().sexist())
        .filter(joke -> !joke.flags().explicit())
        .min(Comparator.comparingInt(joke -> joke.joke().length()))
        .map(JokeDTO::toDTO);
  }
}
