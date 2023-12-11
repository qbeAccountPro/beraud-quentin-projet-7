package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {

  @Autowired
  RatingRepository ratingRepository;

  public List<Rating> findAll() {
    return ratingRepository.findAll();
  }

  public Optional<Rating> findById(Integer id) {
    return ratingRepository.findById(id);
  }

  public void deleteRating(Integer id) {
    ratingRepository.deleteById(id);
  }

  public Rating saveRating(@Valid Rating rating) {
    return ratingRepository.save(rating);
  }
}