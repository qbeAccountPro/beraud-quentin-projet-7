package com.nnk.springboot.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;


@ExtendWith(MockitoExtension.class)
public class CurveControllerTest {

  @Mock
  CurveService curveService;

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @InjectMocks
  CurveController curveController = new CurveController();

  private CurvePoint curvePoint;

  @BeforeEach
  void setUp() {
    curvePoint = new CurvePoint(1, 10.00, 10.00);
  }

  @Test
  void testAddCurvePointForm() {
    // Arrange
    String expect = "curvePoint/add";

    // Act
    String actual = curveController.addCurvePointForm(curvePoint);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testDeleteBid() {
    // Arrange
    String expect = "redirect:/curvePoint/list";

    // Act
    String actual = curveController.deleteCurve(1);

    // Assert
    verify(curveService, times(1)).deleteById(1);
    assertEquals(expect, actual);
  }

  @Test
  void testHome() {
    // Arrange
    String expect = "curvePoint/list";

    // Act
    String actual = curveController.home(model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithPresentCurve() {
    // Arrange
    Optional<CurvePoint> optionalCurvePoint = Optional.of(curvePoint);
    when(curveService.findById(1)).thenReturn(optionalCurvePoint);
    String expect = "curvePoint/update";

    // Act
    String actual = curveController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testShowUpdateFormWithoutPresentCurve() {
    // Arrange
    Optional<CurvePoint> optionalCurvePoint = Optional.empty();
    when(curveService.findById(1)).thenReturn(optionalCurvePoint);
    String expect = "404";

    // Act
    String actual = curveController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testUpdateCurvePointWithError() {
  // Arrange
  when(resultValidate.hasErrors()).thenReturn(true);
  String expect = "curvePoint/update";

  // Act
  String actual = curveController.updateCurvePoint(1, curvePoint, resultValidate, model);

  // Assert
  assertEquals(expect, actual);
  }  @Test
  void testUpdateCurvePointWithoutError() {
 // Arrange
 when(resultValidate.hasErrors()).thenReturn(false);
 String expect = "redirect:/curvePoint/list";

 // Act
 String actual = curveController.updateCurvePoint(1, curvePoint, resultValidate, model);

 // Assert
 assertEquals(expect, actual);
 verify(curveService, times(1)).saveCurvePoint(curvePoint);

  }

  @Test
  void testValidateWithError() {
 // Arrange
 when(resultValidate.hasErrors()).thenReturn(true);
 String expect = "curvePoint/add";

 // Act
 String actual = curveController.validate(curvePoint, resultValidate, model);

 // Assert
 assertEquals(expect, actual);
  }  @Test
  void testValidateWithoutError() {
  // Arrange
  when(resultValidate.hasErrors()).thenReturn(false);
  String expect = "redirect:/curvePoint/list";

  // Act
  String actual = curveController.validate(curvePoint, resultValidate, model);

  // Assert
  verify(curveService, times(1)).saveCurvePoint(curvePoint);
  assertEquals(expect, actual);
  }
}
