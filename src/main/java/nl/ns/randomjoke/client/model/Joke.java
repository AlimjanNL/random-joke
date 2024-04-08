package nl.ns.randomjoke.client.model;

public record Joke(
    String joke,
    Flags flags,
    int id,
    boolean safe
) {

}
