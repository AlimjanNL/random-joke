package nl.ns.randomjoke.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Optional;
import nl.ns.randomjoke.exception.RandomJokeNotFoundException;
import nl.ns.randomjoke.dto.JokeDTO;
import nl.ns.randomjoke.service.RandomJokeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RandomJokeController.class)
class RandomJokeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RandomJokeService randomJokeService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testGetRandomJoke() throws Exception {
    JokeDTO jokeDTO = new JokeDTO(1, "Short safe joke");

    given(randomJokeService.getRandomJoke()).willReturn(Optional.of(jokeDTO));

    mockMvc.perform(get("/api/v1/random-joke").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(jokeDTO)));
  }

  @Test
  void testGetRandomJoke_NotFound() throws Exception {
    given(randomJokeService.getRandomJoke()).willReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/random-joke").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound()).andExpect(
            result -> assertInstanceOf(RandomJokeNotFoundException.class,
                result.getResolvedException()))
        .andExpect(result -> assertEquals("Shortest, non-sexist, non-explicit joke not found.",
            Objects.requireNonNull(result.getResolvedException()).getMessage()));
  }
}