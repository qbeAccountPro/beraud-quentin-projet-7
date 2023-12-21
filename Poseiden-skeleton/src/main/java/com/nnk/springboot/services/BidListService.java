package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * Some javadoc :
 * 
 * Service class for BidList entities.
 * Provides methods to perform CRUD operations on BidList objects.
 */
@Service
public class BidListService {

  @Autowired
  BidListRepository bidListRepository;

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all BidList entities.
   *
   * @return List of BidList objects.
   */
  public List<BidList> findAll() {
    return bidListRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a BidList entity by its ID.
   *
   * @param id the ID of the BidList entity to retrieve.
   * @return Optional containing the BidList object, or empty if not found.
   */
  public Optional<BidList> findById(Integer id) {
    return bidListRepository.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a BidList entity by its ID.
   *
   * @param id the ID of the BidList entity to delete.
   */
  public void deleteBidById(Integer id) {
    bidListRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Saves or updates a BidList entity.
   *
   * @param bid the BidList object to save or update.
   * @return The saved or updated BidList object.
   */
  public BidList saveBidList(BidList bid) {
    return bidListRepository.save(bid);
  }
}
