package nl.ns.randomjoke.controller;

import nl.ns.randomjoke.dto.JokeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RandomJokeControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  void testGetRandomJoke() throws Exception {
    ResponseEntity<JokeDTO> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/random-joke", JokeDTO.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();

    JokeDTO joke = response.getBody();
    assertThat(joke.id()).isGreaterThan(0);
    assertThat(joke.joke()).isNotNull().isNotEmpty();
  }
}