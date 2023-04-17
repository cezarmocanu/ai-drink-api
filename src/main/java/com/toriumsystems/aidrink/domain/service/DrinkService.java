package com.toriumsystems.aidrink.domain.service;

import java.util.List;

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

    public List<DrinkGetDTO> getAllDrinks() {
        var page = PageRequest.of(0, 20);
        return drinkRepository
                .findAll(page).get()
                .map(this::mapDrinkToGetDTO)
                .toList();
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
