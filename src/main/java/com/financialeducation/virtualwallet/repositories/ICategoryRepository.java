package com.financialeducation.virtualwallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialeducation.virtualwallet.entities.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
