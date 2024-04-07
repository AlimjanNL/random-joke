package nl.ns.randomjoke.service;

import java.util.Optional;
import nl.ns.randomjoke.dto.JokeDTO;
import nl.ns.randomjoke.dto.RandomJokeResponse;

public interface RandomJokeService {

  RandomJokeResponse fetchRandomJoke(String path, String type, int amount);

  Optional<JokeDTO> getRandomJoke();

  Optional<JokeDTO> getRandomJoke(String path, String type, int amount);
}
