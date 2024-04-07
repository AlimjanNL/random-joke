package nl.ns.randomjoke.dto;

import java.time.LocalDateTime;

public record RandomJokeNotFoundResponse(LocalDateTime timestamp, int status, String error, String message, String path) {

}
