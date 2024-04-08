package nl.ns.randomjoke.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import nl.ns.randomjoke.client.model.Flags;
import nl.ns.randomjoke.client.model.Joke;
import nl.ns.randomjoke.client.model.RandomJokeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

@SpringBootTest
public class FetchJokeClientImplIntegrationTest {

  @MockBean
  private RestClient restClient;

  @Autowired
  private FetchJokeClientImpl fetchJokeClient;

  @Value("${random-joke.api.path}")
  private String defaultPath;

  @Value("${random-joke.api.params.type}")
  private String defaultType;

  @Value("${random-joke.api.params.amount}")
  private int defaultAmount;

  @BeforeEach
  public void setUp() {
    // mock
    List<Joke> safeJokeList = safeJokeList();
    mockRandomJokeResponse(safeJokeList);
  }

  @Test
  public void fetchRandomJoke_returnsExpectedJoke() {
    RandomJokeResponse response = fetchJokeClient.fetchRandomJoke();

    assertEquals(5, response.jokes().size());
    assertEquals("Longer safe joke", response.jokes().get(0).joke());
  }

  private List<Joke> safeJokeList() {
    return Arrays.asList(
        new Joke("Longer safe joke", new Flags(false, false), 1, true),
        new Joke("Short explicit joke", new Flags(false, true), 2, false),
        new Joke("Long safe but sexist joke", new Flags(true, false), 3, true),
        new Joke("Very long, safe joke", new Flags(false, false), 4, true),
        new Joke("Short safe joke", new Flags(false, false), 5, true));
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