package com.nnk.springboot.domain;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bidlist")
public class BidList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bidListId")
  int bidListId;

  @Column(name = "account")
  @NotBlank(message = "Account is mandatory")
  String account;

  @Column(name = "type")
  @NotBlank(message = "Type is mandatory")
  String type;

  @Column(name = "bidQuantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double bidQuantity;

  @Column(name = "askQuantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double askQuantity;

  @Column(name = "bid")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double bid;

  @Column(name = "ask")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double ask;

  @Column(name = "benchmark")
  String benchmark;

  @Column(name = "bidListDate")
  @NotBlank(message = "Must not be null")
  @PastOrPresent(message = "Date must be in the past or present")
  Timestamp bidListDate;

  @Column(name = "commentary")
  String commentary;

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
  Timestamp creationDate;

  @Column(name = "revisionName")
  String revisionName;

  @Column(name = "revisionDate")
  @NotBlank(message = "Must not be null")
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

  public BidList(String string, String string2, double d) {
  }
}
