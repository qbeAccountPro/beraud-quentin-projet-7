package com.nnk.springboot.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class RatingIntegrationTests {
  @Autowired
  RatingController ratingController;
  @Autowired
  RatingService ratingService;

  private MockMvc mvc;
@Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void ratingIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(ratingController)
                .setControllerAdvice()
                .build();

        Rating rating = new Rating("moodysRating", "sandRating", "fitchRating", 13);

        // Add Rating :
        mvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .param("moodysRating", rating.getMoodysRating())
                .param("sandPRating", rating.getSandPRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", String.valueOf(rating.getOrderNumber()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved Rating :
        List<Rating> ratings = ratingService.findAll();
        int lastRating = ratings.size() - 1;
        Rating ratingSaved = ratings.get(lastRating);

        // Check Values :
        assertEquals(rating.getMoodysRating(), ratingSaved.getMoodysRating());
        assertEquals(rating.getSandPRating(), ratingSaved.getSandPRating());
        assertEquals(rating.getFitchRating(), ratingSaved.getFitchRating());
        assertEquals(rating.getOrderNumber(), ratingSaved.getOrderNumber());

        int ratingId = ratingSaved.getId();

        // Update Rating :
        Rating ratingtUpdated = new Rating("moodysRatingUpdated", "sandRatingUpdated", "fitchRatingUpdated", 51);

        mvc.perform(MockMvcRequestBuilders.post("/rating/update/" + ratingId)
                .param("moodysRating", ratingtUpdated.getMoodysRating())
                .param("sandPRating", ratingtUpdated.getSandPRating())
                .param("fitchRating", ratingtUpdated.getFitchRating())
                .param("orderNumber", String.valueOf(ratingtUpdated.getOrderNumber()))
                .param("id", String.valueOf(ratingId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ratings = ratingService.findAll();
        ratingSaved = ratings.get(lastRating);

        // Check Values :
        assertEquals(ratingtUpdated.getMoodysRating(), ratingSaved.getMoodysRating());
        assertEquals(ratingtUpdated.getSandPRating(), ratingSaved.getSandPRating());
        assertEquals(ratingtUpdated.getFitchRating(), ratingSaved.getFitchRating());
        assertEquals(ratingtUpdated.getOrderNumber(), ratingSaved.getOrderNumber());

        // Delete Rating :
        mvc.perform(MockMvcRequestBuilders.get("/rating/delete/" + ratingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<Rating> optionalRating = ratingService.findById(ratingId);
        Assert.assertFalse(optionalRating.isPresent());
    }
}
