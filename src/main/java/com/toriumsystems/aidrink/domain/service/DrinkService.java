package com.toriumsystems.aidrink.domain.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.toriumsystems.aidrink.domain.dto.DrinkGetDTO;
import com.toriumsystems.aidrink.domain.model.Drink;
import com.toriumsystems.aidrink.domain.repository.DrinkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final Random random = new Random();

    public Integer getPagesCount(Integer pageSize) {
        return drinkRepository.count() / pageSize;
    }

    public List<DrinkGetDTO> getShuffledDrinksByPage(Integer pageOffset, Integer pageSize) {
        var page = PageRequest.of(pageOffset, pageSize);

        var drinks = drinkRepository
                .findAll(page).get()
                .map(this::mapDrinkToGetDTO)
                .collect(Collectors.toList());

        IntStream
                .range(0, drinks.size())
                .forEach(i -> Collections.swap(drinks, i, random.nextInt(i + 1)));

        return drinks;
    }

    public List<DrinkGetDTO> mapDrinkListToGetDTO(List<Drink> list) {
        return list
                .stream()
                .map(this::mapDrinkToGetDTO)
                .toList();
    }

    public DrinkGetDTO mapDrinkToGetDTO(Drink drink) {
        return DrinkGetDTO
                .builder()
                .id(drink.getId())
                .name(drink.getName())
                .imagePath(drink.getImage().getPath())
                .build();
    }
}
