package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CurvePoint entities.
 * Extends JpaRepository to provide basic CRUD operations for CurvePoint
 * objects.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
