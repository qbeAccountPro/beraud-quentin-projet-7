package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for RuleName entities.
 * Extends JpaRepository to provide basic CRUD operations for RuleName objects.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
