package nl.ns.randomjoke.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import nl.ns.randomjoke.client.model.Flags;
import nl.ns.randomjoke.client.model.Joke;
import nl.ns.randomjoke.client.model.RandomJokeResponse;
import nl.ns.randomjoke.dto.JokeDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

@ExtendWith(MockitoExtension.class)
class RandomJokeServiceImplTest {

  @Mock
  private RestClient restClient;

  @InjectMocks
  private RandomJokeServiceImpl randomJokeService;

//  @Test
//  void testFetchRandomJoke() {
//    // mock
//    List<Joke> safeJokeList = safeJokeList();
//    mockRandomJokeResponse(safeJokeList);
//
//    // invocation
//    String defaultPath = "/joke/Any";
//    String defaultType = "single";
//    int defaultAmount = 16;
//    RandomJokeResponse response = randomJokeService.fetchRandomJoke(defaultPath, defaultType, defaultAmount);
//
//    // verification
//    assertNotNull(response);
//    assertEquals(5, response.jokes().size());
//    assertEquals("Longer safe joke", response.jokes().get(0).joke());
//  }

  @Test
  void testGetRandomJoke_SafeJoke() {
    // mock
    List<Joke> safeJokeList = safeJokeList();
    mockRandomJokeResponse(safeJokeList);

    // invocation
    Optional<JokeDTO> result = randomJokeService.getRandomJoke();

    // verification
    assertNotNull(result);
    assertTrue(result.isPresent());
    assertEquals("Short safe joke", result.get().joke());
  }

  @Test
  void testGetRandomJoke_NoSafeJokes() {
    // mock
    List<Joke> unSafeJokeList = unsafeJokeList();
    mockRandomJokeResponse(unSafeJokeList);

    // invocation
    Optional<JokeDTO> result = randomJokeService.getRandomJoke();

    // verification
    assertNotNull(result);
    assertFalse(result.isPresent());
  }

  private List<Joke> safeJokeList() {
    return Arrays.asList(
        new Joke("Longer safe joke", new Flags(false, false), 1, true),
        new Joke("Short explicit joke", new Flags(false, true), 2, false),
        new Joke("Long safe but sexist joke", new Flags(true, false), 3, true),
        new Joke("Very long, safe joke", new Flags(false, false), 4, true),
        new Joke("Short safe joke", new Flags(false, false), 5, true));
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

    RequestHeadersUriSpec mockUriSpec = mock(RequestHeadersUriSpec.class);
    RequestHeadersSpec mockHeadersSpec = mock(RequestHeadersSpec.class);
    ResponseSpec mockResponseSpec = mock(ResponseSpec.class);

    when(restClient.get()).thenReturn(mockUriSpec);
    when(mockUriSpec.uri(any(Function.class))).thenReturn(mockHeadersSpec);
    when(mockHeadersSpec.accept(any())).thenReturn(mockHeadersSpec);
    when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
    when(mockResponseSpec.body(eq(RandomJokeResponse.class))).thenReturn(randomJokeResponse);
  }
}