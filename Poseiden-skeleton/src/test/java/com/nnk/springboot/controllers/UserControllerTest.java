package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  Model model;

  @Mock
  BindingResult resultValidate;

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserController userController = new UserController();

  private User user;

  @BeforeEach
  void setUp() {
    user = new User(1, "bob", "secretPassword", "fullBob", "ultraMegaAdmin");
  }

  @Test
  void testAddUser() {
    // Arrange
    String expect = "user/add";

    // Act
    String actual = userController.addUser(user);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testDeleteUser() {
    // Arrange
    String expect = "redirect:/user/list";
    when(userRepository.findById(1)).thenReturn(Optional.of(user));

    // Act
    String actual = userController.deleteUser(1, model);

    // Assert
    verify(userRepository, times(1)).delete(user);
    assertEquals(expect, actual);
  }

  @Test
  void testHome() {
    // Arrange
    String expect = "user/list";

    // Act
    String actual = userController.home(model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithPresentUser() {
    // Arrange
    Optional<User> optionalUser = Optional.of(user);
    when(userRepository.findById(1)).thenReturn(optionalUser);
    String expect = "user/update";

    // Act
    String actual = userController.showUpdateForm(1, model);

    // Assert
    assertEquals(expect, actual);
  }

  @Test
  void testShowUpdateFormWithoutPresentUser() {
    // Arrange
    Optional<User> optionalUser = Optional.empty();
    when(userRepository.findById(1)).thenReturn(optionalUser);

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userController.showUpdateForm(1, model);
    });

    // Assert
    assertEquals("Invalid user Id:1", exception.getMessage());
  }

  @Test
  void testUpdateUserWithError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "user/update";

    // Act
    String actual = userController.updateUser(1, user, resultValidate, model);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testUpdateUserWithoutError() {

    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/user/list";

    // Act
    String actual = userController.updateUser(1, user, resultValidate, model);

    // Assert
    assertEquals(expect, actual);
    verify(userRepository, times(1)).save(user);

  }

  @Test
  void testValidateWithError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(true);
    String expect = "user/add";

    // Act
    String actual = userController.validate(user, resultValidate, model);

    // Assert
    assertEquals(expect, actual);

  }

  @Test
  void testValidateWithoutError() {
    // Arrange
    when(resultValidate.hasErrors()).thenReturn(false);
    String expect = "redirect:/user/list";

    // Act
    String actual = userController.validate(user, resultValidate, model);

    // Assert
    verify(userRepository, times(1)).save(user);
    assertEquals(expect, actual);
  }
}
