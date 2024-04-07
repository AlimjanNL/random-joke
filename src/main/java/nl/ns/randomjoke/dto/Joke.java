package nl.ns.randomjoke.dto;

public record Joke(
    String joke,
    Flags flags,
    int id,
    boolean safe
) {

}
