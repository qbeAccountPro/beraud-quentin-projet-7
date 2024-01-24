package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Service class for Trade entities.
 * Provides methods to perform CRUD operations on Trade objects.
 */
@Service
public class TradeService {

  @Autowired
  TradeRepository tradeRepository;

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all Trade entities.
   *
   * @return List of Trade objects.
   */
  public List<Trade> findAll() {
    return tradeRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a Trade entity by its ID.
   *
   * @param id the ID of the Trade entity to retrieve.
   * @return Optional containing the Trade object, or empty if not found.
   */
  public Optional<Trade> findById(Integer id) {
    return tradeRepository.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a Trade entity by its ID.
   *
   * @param id the ID of the Trade entity to delete.
   */
  public void deleteById(Integer id) {
    tradeRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Saves or updates a Trade entity.
   *
   * @param trade the Trade object to save or update.
   * @return The saved or updated Trade object.
   */
  public Trade saveTrade(@Valid Trade trade) {
    return tradeRepository.save(trade);
  }
}
