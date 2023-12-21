package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @Column(name = "moodys_rating")
  @NotBlank(message = "Must not be null")
  String moodysRating;

  @Column(name = "sand_p_rating")
  @NotBlank(message = "Must not be null")
  String sandPRating;

  @Column(name = "fitch_rating")
  @NotBlank(message = "Must not be null")
  String fitchRating;

  @Column(name = "order_number")
  @NotNull(message = "BidQuantity is mandatory")
  @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Must be a valid integer")
  int orderNumber;

  public Rating(@NotBlank(message = "Must not be null") String moodysRating,
      @NotBlank(message = "Must not be null") String sandPRating,
      @NotBlank(message = "Must not be null") String fitchRating,
      @NotNull(message = "BidQuantity is mandatory") @Digits(integer = 2147483647, fraction = 0, message = "Must be a valid integer") int orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }
}