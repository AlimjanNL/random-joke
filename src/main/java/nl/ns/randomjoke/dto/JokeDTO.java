package nl.ns.randomjoke.dto;

import nl.ns.randomjoke.client.model.Joke;

public record JokeDTO(int id, String joke) {

  public static JokeDTO toDTO(Joke joke) {
    return new JokeDTO(joke.id(), joke.joke());
  }
}
