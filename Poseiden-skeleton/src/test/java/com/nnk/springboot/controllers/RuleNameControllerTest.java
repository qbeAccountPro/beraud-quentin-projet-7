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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@ExtendWith(MockitoExtension.class)

public class RuleNameControllerTest {

  @Mock
  RuleNameService ruleNameService;

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @InjectMocks
  RuleNameController ruleNameController = new RuleNameController();

  private RuleName ruleName;

  @BeforeEach
  void setUp() {
    ruleName = new RuleName("nameTest", "descriptionTest", "json", "template", "oki", "daci");
  }

  @Test
  void testAddRuleForm() {

    // Arrange
    String expect = "ruleName/add";

    // Act
    String actual = ruleNameController.addRuleForm(ruleName);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testDeleteRuleName() {
    // Arrange
    String expect = "redirect:/ruleName/list";

    // Act
    String actual = ruleNameController.deleteRuleName(1);

    // Assert
    verify(ruleNameService, times(1)).deleteById(1);
    assertEquals(expect, actual);
  }

  @Test
  void testHome() {
    // Arrange
    String expect = "ruleName/list";

    // Act
    String actual = ruleNameController.home(model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithPresentRuleName() {

    // Arrange
    Optional<RuleName> optionalRuleName = Optional.of(ruleName);
    when(ruleNameService.findById(1)).thenReturn(optionalRuleName);
    String expect = "ruleName/update";

    // Act
    String actual = ruleNameController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithoutPresentRuleName() {
    // Arrange
    Optional<RuleName> optionalRuleName = Optional.empty();
    when(ruleNameService.findById(1)).thenReturn(optionalRuleName);
    String expect = "404";

    // Act
    String actual = ruleNameController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateRuleNameWithError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "ruleName/update";

    // Act
    String actual = ruleNameController.updateRuleName(1, ruleName, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateRuleNameWithoutError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/ruleName/list";

    // Act
    String actual = ruleNameController.updateRuleName(1, ruleName, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
    verify(ruleNameService, times(1)).saveRuleName(ruleName);
  
  }

  @Test
  void testValidateWithError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "ruleName/add";

    // Act
    String actual = ruleNameController.validate(ruleName, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testValidateWithoutError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/ruleName/list";

    // Act
    String actual = ruleNameController.validate(ruleName, resultValidate, model);

    // Assert
    verify(ruleNameService, times(1)).saveRuleName(ruleName);
    assertEquals(expect, actual);
  }
}
