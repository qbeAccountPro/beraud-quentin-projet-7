package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "moodysRating")
  @NotBlank(message = "Must not be null")
  String moodysRating;

  @Column(name = "sandPRating")
  @NotBlank(message = "Must not be null")
  String sandPRating;

  @Column(name = "fitchRating")
  @NotBlank(message = "Must not be null")
  String fitchRating;

  @Column(name = "orderNumber")
  @NotBlank(message = "Must not be null")
  @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Must be a valid integer")
  Integer orderNumber;

  public Rating(String string, String string2, String string3, int i) {
  }
}