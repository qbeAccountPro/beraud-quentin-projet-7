package com.nnk.springboot.domain;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int tradeId;

  @Column(name = "account")
  @NotBlank(message = "Must not be null")
  String account;

  @Column(name = "type")
  @NotBlank(message = "Must not be null")
  String type;

  @Column(name = "buyQuantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double buyQuantity;

  @Column(name = "sellQuantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double sellQuantity;

  @Column(name = "buyPrice")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double buyPrice;

  @Column(name = "sellPrice")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double sellPrice;

  @Column(name = "benchmark")
  @NotBlank(message = "Must not be null")
  String benchmark;

  @Column(name = "tradeDate")
  @NotBlank(message = "Must not be null")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp tradeDate;

  @Column(name = "security")
  String security;

  @Column(name = "status")
  String status;

  @Column(name = "trader")
  String trader;

  @Column(name = "book")
  String book;

  @Column(name = "creationName")
  String creationName;

  @Column(name = "creationDate")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp creationDate;

  @Column(name = "revisionName")
  String revisionName;

  @Column(name = "revisionDate")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp revisionDate;

  @Column(name = "dealName")
  String dealName;

  @Column(name = "dealType")
  String dealType;

  @Column(name = "sourceListId")
  String sourceListId;

  @Column(name = "side")
  String side;

  public Trade(String string, String string2) {
  }

}