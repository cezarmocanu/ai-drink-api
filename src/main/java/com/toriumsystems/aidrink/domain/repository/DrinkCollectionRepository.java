package com.toriumsystems.aidrink.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toriumsystems.aidrink.domain.model.DrinkCollection;

public interface DrinkCollectionRepository extends JpaRepository<DrinkCollection, Long> {

}
