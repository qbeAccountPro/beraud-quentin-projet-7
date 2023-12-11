package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {

  @Autowired
  TradeRepository tradeRepository;

  public List<Trade> findAll() {
    return tradeRepository.findAll();
  }

  public Optional<Trade> findById(Integer id) {
    return tradeRepository.findById(id);
  }

  public void deleteById(Integer id) {
    tradeRepository.deleteById(id);
  }

  public Trade saveTrade(@Valid Trade trade) {
    return tradeRepository.save(trade);
  }
}