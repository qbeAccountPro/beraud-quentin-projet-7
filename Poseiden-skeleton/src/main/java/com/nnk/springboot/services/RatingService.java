package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Service class for Rating entities.
 * Provides methods to perform CRUD operations on Rating objects.
 */
@Service
public class RatingService {

  @Autowired
  RatingRepository ratingRepository;

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all Rating entities.
   *
   * @return List of Rating objects.
   */
  public List<Rating> findAll() {
    return ratingRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a Rating entity by its ID.
   *
   * @param id the ID of the Rating entity to retrieve.
   * @return Optional containing the Rating object, or empty if not found.
   */
  public Optional<Rating> findById(Integer id) {
    return ratingRepository.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a Rating entity by its ID.
   *
   * @param id the ID of the Rating entity to delete.
   */
  public void deleteRating(Integer id) {
    ratingRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Saves or updates a Rating entity.
   *
   * @param rating the Rating object to save or update.
   * @return The saved or updated Rating object.
   */
  public Rating saveRating(@Valid Rating rating) {
    return ratingRepository.save(rating);
  }
}
