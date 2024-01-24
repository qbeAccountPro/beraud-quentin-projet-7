package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Trade entities.
 * Extends JpaRepository to provide basic CRUD operations for Trade objects.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

}
