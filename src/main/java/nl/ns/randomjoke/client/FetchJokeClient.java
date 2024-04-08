package nl.ns.randomjoke.client;

import nl.ns.randomjoke.client.model.RandomJokeResponse;

public interface FetchJokeClient {

  RandomJokeResponse fetchRandomJoke();
}
