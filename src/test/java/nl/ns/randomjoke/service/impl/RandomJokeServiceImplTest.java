package nl.ns.randomjoke.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import nl.ns.randomjoke.client.FetchJokeClient;
import nl.ns.randomjoke.client.model.Flags;
import nl.ns.randomjoke.client.model.Joke;
import nl.ns.randomjoke.client.model.RandomJokeResponse;
import nl.ns.randomjoke.dto.JokeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RandomJokeServiceImplTest {

  @Mock
  private FetchJokeClient fetchJokeClient;

  @InjectMocks
  private RandomJokeServiceImpl randomJokeService;

  @Test
  void testGetRandomJoke_SafeJoke() {
    // Mock
    List<Joke> safeJokeList = safeJokeList();
    mockRandomJokeResponse(safeJokeList);

    // Invocation
    Optional<JokeDTO> result = randomJokeService.getRandomJoke();

    // Verification
    assertTrue(result.isPresent(), "Expected a present joke");
    assertEquals("Short safe joke", result.get().joke(), "Joke content mismatch");
  }

  @Test
  void testGetRandomJoke_NoSafeJokes() {
    // Mock
    List<Joke> unsafeJokeList = unsafeJokeList();
    mockRandomJokeResponse(unsafeJokeList);

    // Invocation
    Optional<JokeDTO> result = randomJokeService.getRandomJoke();

    // Verification
    assertFalse(result.isPresent(), "Expected no joke to be present");
  }

  private List<Joke> safeJokeList() {
    return Arrays.asList(
        new Joke("Longer safe joke", new Flags(false, false), 1, true),
        new Joke("Short explicit joke", new Flags(false, true), 2, false),
        new Joke("Long safe but sexist joke", new Flags(true, false), 3, true),
        new Joke("Very long, safe joke", new Flags(false, false), 4, true),
        new Joke("Short safe joke", new Flags(false, false), 5, true)
    );
  }

  private List<Joke> unsafeJokeList() {
    return Arrays.asList(
        new Joke("Explicit joke 1", new Flags(false, true), 1, false),
        new Joke("Sexist joke", new Flags(true, false), 2, false),
        new Joke("Explicit and sexist joke", new Flags(true, true), 3, false)
    );
  }

  private void mockRandomJokeResponse(List<Joke> jokeList) {
    RandomJokeResponse randomJokeResponse = new RandomJokeResponse(jokeList);
    when(fetchJokeClient.fetchRandomJoke()).thenReturn(randomJokeResponse);
  }
}
