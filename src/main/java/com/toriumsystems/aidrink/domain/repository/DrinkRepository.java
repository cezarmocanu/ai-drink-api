package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toriumsystems.aidrink.domain.model.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
