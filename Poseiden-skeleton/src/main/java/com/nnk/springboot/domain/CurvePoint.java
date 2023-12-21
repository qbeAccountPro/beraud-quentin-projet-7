package com.nnk.springboot.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Digits;

import jakarta.persistence.Id;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @Column(name = "curve_id")
  int curveId;

  @Column(name = "as_of_date")
  Timestamp asOfDate;

  @Column(name = "term")
  @NotNull(message = "Must not be null")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double term;

  @Column(name = "value")
  @NotNull(message = "Must not be null")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double value;

  @Column(name = "creation_date")
  Timestamp creationDate;

  public CurvePoint(int curveId,
      @NotNull(message = "Must not be null") @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.") Double term,
      @NotNull(message = "Must not be null") @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.") Double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }
}