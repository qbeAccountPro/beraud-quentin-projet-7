package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.Id;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trade_id")
  int tradeId;

  @Column(name = "account")
  @NotBlank(message = "Must not be null")
  String account;

  @Column(name = "type")
  @NotBlank(message = "Must not be null")
  String type;

  @Column(name = "buy_quantity")
  @NotNull(message = "Must not be null")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  Double buyQuantity;

  @Column(name = "sell_quantity")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Must be greater than 0")
  Double sellQuantity;

  @Column(name = "buy_price")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Must be greater than 0")
  Double buyPrice;

  @Column(name = "sell_price")
  @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.")
  @DecimalMin(value = "0.00", inclusive = false, message = "Must be greater than 0")
  Double sellPrice;

  @Column(name = "benchmark")
  String benchmark;

  @Column(name = "trade_date")
  Timestamp tradeDate;

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

  @Column(name = "revisiondate")
  Timestamp revisionDate;

  @Column(name = "dealname")
  String dealName;

  @Column(name = "deal_type")
  String dealType;

  @Column(name = "source_list_id")
  String sourceListId;

  @Column(name = "side")
  String side;

  public Trade(@NotBlank(message = "Must not be null") String account,
      @NotBlank(message = "Must not be null") String type,
      @NotNull(message = "Must not be null") @Digits(integer = 10, fraction = 2, message = "Only numbers are accepted.") Double buyQuantity) {
    this.account = account;
    this.type = type;
    this.buyQuantity = buyQuantity;
  }

}