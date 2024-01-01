package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bidlist")
public class BidList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bid_list_id")
  int BidListId;

  @Column(name = "account")
  @NotBlank(message = "Account is mandatory")
  String account;

  @Column(name = "type")
  @NotBlank(message = "Type is mandatory")
  String type;

  @Column(name = "bid_quantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Bid Quantity must be greater than 0")
  @NotNull(message = "BidQuantity is mandatory")
  Double bidQuantity;

  @Column(name = "ask_quantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.0", inclusive = false, message = "Must be greater than 0")
  Double askQuantity;

  @Column(name = "bid")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Must be greater than 0")
  Double bid;

  @Column(name = "ask")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Must be greater than 0")
  Double ask;

  @Column(name = "benchmark")
  String benchmark;

  @Column(name = "bid_list_date")
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

  @Column(name = "creation_name")
  String creationName;

  @Column(name = "creation_date")
  Timestamp creationDate;

  @Column(name = "revision_name")
  String revisionName;

  @Column(name = "revision_date")
  Timestamp revisionDate;

  @Column(name = "deal_name")
  String dealName;

  @Column(name = "deal_type")
  String dealType;

  @Column(name = "source_list_id")
  String sourceListId;

  @Column(name = "side")
  String side;

  public BidList(
      @NotBlank(message = "Account is mandatory") 
      String account,
      @NotBlank(message = "Type is mandatory") 
      String type,
      @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.") 
      @DecimalMin(value = "0.0", inclusive = false, message = "Bid Quantity must be greater than 0") 
      @NotNull(message = "BidQuantity is mandatory") 
      Double bidQuantity) {
    this.account = account;
    this.type = type;
    this.bidQuantity = bidQuantity;
  }

}
