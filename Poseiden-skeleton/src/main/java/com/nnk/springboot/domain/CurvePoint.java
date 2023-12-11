package com.nnk.springboot.domain;
/* 
import org.hibernate.validator.constraints.Length; */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import jakarta.persistence.Id;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "curveId")
  @NotBlank(message = "Must not be null")
  @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Must be a valid integer")
  Integer curveId;

  @Column(name = "asOfDate")
  @NotBlank(message = "Must not be null")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp asOfDate;

  @Column(name = "term")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double term;

  @Column(name = "value")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double value;

  @Column(name = "creationDate")
  @NotBlank(message = "Must not be null")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp creationDate;

  public CurvePoint(int i, double d, double e) {
  }
}