package nl.ns.randomjoke.client.model;

import nl.ns.randomjoke.client.model.Joke;

import java.util.List;

public record RandomJokeResponse(List<Joke> jokes) {

}
