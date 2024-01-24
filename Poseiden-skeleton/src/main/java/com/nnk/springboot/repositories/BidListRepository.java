package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for BidList entities.
 * Extends JpaRepository to provide basic CRUD operations for BidList objects.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
