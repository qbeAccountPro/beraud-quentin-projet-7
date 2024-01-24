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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {
  @Mock
  BidListService bidListService;

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @InjectMocks
  BidListController bidListController = new BidListController();

  private BidList bidList;

  @BeforeEach
  void setUp() {
    bidList = new BidList("accountTest", "typeTest", 12.0);
  }

  @Test
  void testAddBidForm() {
    // Arrange
    String expect = "bidList/add";

    // Act
    String actual = bidListController.addBidForm(bidList);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testDeleteBid() {
    // Arrange
    String expect = "redirect:/bidList/list";

    // Act
    String actual = bidListController.deleteBid(1);

    // Assert
    verify(bidListService, times(1)).deleteBidById(1);
    assertEquals(expect, actual);
  }

  @Test
  void testHome() {
    // Arrange
    String expect = "bidList/list";

    // Act
    String actual = bidListController.home(model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithPresentBid() {
    // Arrange
    Optional<BidList> optionalBidList = Optional.of(bidList);
    when(bidListService.findById(1)).thenReturn(optionalBidList);
    String expect = "bidList/update";

    // Act
    String actual = bidListController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithoutPresentBid() {
    // Arrange
    Optional<BidList> optionalBidList = Optional.empty();
    when(bidListService.findById(1)).thenReturn(optionalBidList);
    String expect = "404";

    // Act
    String actual = bidListController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateBidWithError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "bidList/update";

    // Act
    String actual = bidListController.updateBid(1, bidList, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testUpdateBidWithoutError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/bidList/list";

    // Act
    String actual = bidListController.updateBid(1, bidList, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
    verify(bidListService, times(1)).saveBidList(bidList);
  }

  @Test
  void testValidateWithError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "bidList/add";

    // Act
    String actual = bidListController.validate(bidList, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testValidateWithoutError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/bidList/list";

    // Act
    String actual = bidListController.validate(bidList, resultValidate, model);

    // Assert
    verify(bidListService, times(1)).saveBidList(bidList);
    assertEquals(expect, actual);
  }
}
