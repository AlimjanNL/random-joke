package nl.ns.randomjoke.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import nl.ns.randomjoke.exception.RandomJokeNotFoundException;
import nl.ns.randomjoke.dto.JokeDTO;
import nl.ns.randomjoke.dto.RandomJokeNotFoundResponse;
import nl.ns.randomjoke.service.RandomJokeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
@Slf4j
public class RandomJokeController {

  private final RandomJokeService randomJokeService;

  public RandomJokeController(RandomJokeService randomJokeService) {
    this.randomJokeService = randomJokeService;
  }

  @GetMapping("/random-joke")
  public JokeDTO getRandomJoke() {
    log.info("Request random joke");

    return randomJokeService.getRandomJoke().orElseThrow(() -> new RandomJokeNotFoundException(
        "Shortest, non-sexist, non-explicit joke not found."));
  }

  @ExceptionHandler(RandomJokeNotFoundException.class)
  public ResponseEntity<RandomJokeNotFoundResponse> randomJokeNotFoundHandler(
      RandomJokeNotFoundException ex, HttpServletRequest request) {
    log.info("Handle RandomJokeNotFoundException");

    RandomJokeNotFoundResponse randomJokeNotFoundResponse = new RandomJokeNotFoundResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );

    return new ResponseEntity<>(randomJokeNotFoundResponse, HttpStatus.NOT_FOUND);
  }
}
