package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for Rating entities.
 * Extends JpaRepository to provide basic CRUD operations for Rating objects.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
