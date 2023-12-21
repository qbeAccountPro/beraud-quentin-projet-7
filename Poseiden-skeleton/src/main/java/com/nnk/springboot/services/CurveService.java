package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurveService {

  @Autowired
  CurvePointRepository curvePointRepository;

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all CurvePoint entities.
   *
   * @return List of CurvePoint objects.
   */
  public List<CurvePoint> findAll() {
    return curvePointRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a CurvePoint entity by its ID.
   *
   * @param id the ID of the CurvePoint entity to retrieve.
   * @return Optional containing the CurvePoint object, or empty if not found.
   */
  public Optional<CurvePoint> findById(Integer id) {
    return curvePointRepository.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a CurvePoint entity by its ID.
   *
   * @param id the ID of the CurvePoint entity to delete.
   */
  public void deleteById(Integer id) {
    curvePointRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Saves or updates a CurvePoint entity.
   *
   * @param curvePoint the CurvePoint object to save or update.
   * @return The saved or updated CurvePoint object.
   */
  public CurvePoint saveCurvePoint(@Valid CurvePoint curvePoint) {
    return curvePointRepository.save(curvePoint);
  }
}
