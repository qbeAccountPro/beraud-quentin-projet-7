package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {
  @Mock
  RatingService ratingService;

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @InjectMocks
  RatingController ratingController = new RatingController();

  private Rating rating;

  @BeforeEach
  void setUp() {
    rating = new Rating("moodyRatingTest", "sandRatingTest", "fitchRatingTest", 1);
  }

  @Test
  void testAddRatingForm() {
    // Arrange
    String expect = "rating/add";

    // Act
    String actual = ratingController.addRatingForm(rating);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testDeleteRating() {

    // Arrange
    String expect = "redirect:/rating/list";

    // Act
    String actual = ratingController.deleteRating(1);

    // Assert
    verify(ratingService, times(1)).deleteRating(1);
    assertEquals(expect, actual);

  }

  @Test
  void testHome() {
    // Arrange
    String expect = "rating/list";

    // Act
    String actual = ratingController.home(model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithPresentRating() {

    // Arrange
    Optional<Rating> optionalRating = Optional.of(rating);
    when(ratingService.findById(1)).thenReturn(optionalRating);
    String expect = "rating/update";

    // Act
    String actual = ratingController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithoutPresentRating() {

    // Arrange
    Optional<Rating> optionalRating = Optional.empty();
    when(ratingService.findById(1)).thenReturn(optionalRating);
    String expect = "404";

    // Act
    String actual = ratingController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateRatingWithError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "rating/update";

    // Act
    String actual = ratingController.updateRating(1, rating, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateRatingWithoutError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/rating/list";

    // Act
    String actual = ratingController.updateRating(1, rating, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
    verify(ratingService, times(1)).saveRating(rating);

  }

  @Test
  void testValidateWithError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "rating/add";

    // Act
    String actual = ratingController.validate(rating, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testValidateWithoutError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/rating/list";

    // Act
    String actual = ratingController.validate(rating, resultValidate, model);

    // Assert
    verify(ratingService, times(1)).saveRating(rating);
    assertEquals(expect, actual);
  }
}
