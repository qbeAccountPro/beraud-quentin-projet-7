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

  public List<CurvePoint> findAll() {
    return curvePointRepository.findAll();
  }

  public Optional<CurvePoint> findById(Integer id) {
    return curvePointRepository.findById(id);
  }

  public void deleteById(Integer id) {
    curvePointRepository.deleteById(id);
  }

  public CurvePoint saveCurvePoint(@Valid CurvePoint curvePoint) {
    return curvePointRepository.save(curvePoint);
  }
}