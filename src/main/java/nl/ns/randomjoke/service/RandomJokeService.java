package nl.ns.randomjoke.service;

import java.util.Optional;
import nl.ns.randomjoke.dto.JokeDTO;

public interface RandomJokeService {

  Optional<JokeDTO> getRandomJoke();
}
