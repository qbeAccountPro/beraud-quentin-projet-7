package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

  @Autowired
  BidListRepository bidListRepository;

  public List<BidList> findAll() {
    return bidListRepository.findAll();
  }

  public Optional<BidList> findById(Integer id) {
    return bidListRepository.findById(id);
  }

  public void deleteBidById(Integer id) {
    bidListRepository.deleteById(id);
  }

  public BidList saveBidList(@Valid BidList bid) {
    return bidListRepository.save(bid);
  }
}