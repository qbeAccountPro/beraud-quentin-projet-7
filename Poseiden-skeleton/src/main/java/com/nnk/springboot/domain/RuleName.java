package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "name")
  @NotBlank(message = "Must not be null")
  String name;

  @Column(name = "description")
  @NotBlank(message = "Must not be null")
  String description;

  @Column(name = "json")
  @NotBlank(message = "Must not be null")
  String json;

  @Column(name = "template")
  @NotBlank(message = "Must not be null")
  String template;

  @Column(name = "sqlStr")
  @NotBlank(message = "Must not be null")
  String sqlStr;

  @Column(name = "sqlPart")
  @NotBlank(message = "Must not be null")
  String sqlPart;

  public RuleName(String string, String string2, String string3, String string4, String string5, String string6) {
  }
}