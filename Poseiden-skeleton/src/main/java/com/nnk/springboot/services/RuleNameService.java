package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * Service class for RuleName entities.
 * Provides methods to perform CRUD operations on RuleName objects.
 */
@Service
public class RuleNameService {

  @Autowired
  RuleNameRepository ruleNameRepository;

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all RuleName entities.
   *
   * @return List of RuleName objects.
   */
  public List<RuleName> findAll() {
    return ruleNameRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a RuleName entity by its ID.
   *
   * @param id the ID of the RuleName entity to retrieve.
   * @return Optional containing the RuleName object, or empty if not found.
   */
  public Optional<RuleName> findById(Integer id) {
    return ruleNameRepository.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a RuleName entity by its ID.
   *
   * @param id the ID of the RuleName entity to delete.
   */
  public void deleteById(Integer id) {
    ruleNameRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Saves or updates a RuleName entity.
   *
   * @param ruleName the RuleName object to save or update.
   * @return The saved or updated RuleName object.
   */
  public RuleName saveRuleName(RuleName ruleName) {
    return ruleNameRepository.save(ruleName);
  }
}
