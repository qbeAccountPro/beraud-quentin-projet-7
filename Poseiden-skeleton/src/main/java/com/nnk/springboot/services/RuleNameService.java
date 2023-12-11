package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {

  @Autowired
  RuleNameRepository ruleNameRepository;

  public List<RuleName> findAll() {
    return null;
  }

  public Optional<RuleName> findById(Integer id) {
    return ruleNameRepository.findById(id);
  }

  public void deleteById(Integer id) {
    ruleNameRepository.deleteById(id);
  }

  public RuleName saveRuleName(@Valid RuleName ruleName) {
    return ruleNameRepository.save(ruleName);
  }
}