package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.toriumsystems.aidrink.domain.model.Drink;

public interface DrinkRepository extends PagingAndSortingRepository<Drink, Long> {

}
