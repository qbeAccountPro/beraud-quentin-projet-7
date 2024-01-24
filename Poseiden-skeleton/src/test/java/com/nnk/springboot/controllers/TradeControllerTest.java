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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {
  @Mock
  TradeService tradeService;

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @InjectMocks
  TradeController tradeController = new TradeController();

  private Trade trade;

  @BeforeEach
  void setUp() {
    trade = new Trade("accountTest", "typeTessssssst", 12.00);
  }

  @Test
  void testAddUser() {

    // Arrange
    String expect = "trade/add";

    // Act
    String actual = tradeController.addTrade(trade);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testDeleteTrade() {

    // Arrange
    String expect = "redirect:/trade/list";

    // Act
    String actual = tradeController.deleteTrade(1);

    // Assert
    verify(tradeService, times(1)).deleteById(1);
    assertEquals(expect, actual);
 
  }

  @Test
  void testHome() {
    // Arrange
    String expect = "trade/list";

    // Act
    String actual = tradeController.home(model);

    // Assert
    assertEquals(expect, actual);


  }

  @Test
  void testShowUpdateFormWithPresentTrade() {
    // Arrange
    Optional<Trade> optionalTrade = Optional.of(trade);
    when(tradeService.findById(1)).thenReturn(optionalTrade);
    String expect = "trade/update";

    // Act
    String actual = tradeController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);


  }
  @Test
  void testShowUpdateFormWithoutPresentTrade() {
    // Arrange
    Optional<Trade> optionalTrade = Optional.empty();
    when(tradeService.findById(1)).thenReturn(optionalTrade);
    String expect = "404";

    // Act
    String actual = tradeController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  

  }

  @Test
  void testUpdateTradeWithError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "trade/update";

    // Act
    String actual = tradeController.updateTrade(1, trade, resultValidate, model);

    // Assert
    assertEquals(expect, actual);


  }
  @Test
  void testUpdateTradeWithoutError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/trade/list";

    // Act
    String actual = tradeController.updateTrade(1, trade, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
    verify(tradeService, times(1)).saveTrade(trade);
  

  }

  @Test
  void testValidateWithError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "trade/add";

    // Act
    String actual = tradeController.validate(trade, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  

  }  @Test
  void testValidateWithoutError() {
    
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/trade/list";

    // Act
    String actual = tradeController.validate(trade, resultValidate, model);

    // Assert
    verify(tradeService, times(1)).saveTrade(trade);
    assertEquals(expect, actual);
  

  }
}
