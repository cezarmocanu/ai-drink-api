package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toriumsystems.aidrink.domain.model.Drink;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
