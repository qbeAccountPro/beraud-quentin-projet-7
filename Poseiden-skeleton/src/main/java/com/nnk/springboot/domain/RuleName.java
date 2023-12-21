package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
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

  @Column(name = "sql_str")
  @NotBlank(message = "Must not be null")
  String sqlStr;

  @Column(name = "sql_part")
  @NotBlank(message = "Must not be null")
  String sqlPart;

  public RuleName(@NotBlank(message = "Must not be null") String name,
      @NotBlank(message = "Must not be null") String description, @NotBlank(message = "Must not be null") String json,
      @NotBlank(message = "Must not be null") String template, @NotBlank(message = "Must not be null") String sqlStr,
      @NotBlank(message = "Must not be null") String sqlPart) {
    this.name = name;
    this.description = description;
    this.json = json;
    this.template = template;
    this.sqlStr = sqlStr;
    this.sqlPart = sqlPart;
  }

}