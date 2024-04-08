package nl.ns.randomjoke.exception;

public class RandomJokeNotFoundException extends RuntimeException {

  public RandomJokeNotFoundException(String message) {
    super(message);
  }
}
