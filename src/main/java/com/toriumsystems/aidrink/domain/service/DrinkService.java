package com.toriumsystems.aidrink.domain.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Integer getPagesCount(Integer pageSize) {
        return drinkRepository.count() / pageSize;
    }

    public List<DrinkGetDTO> getDrinksByPage(Integer pageOffset, Integer pageSize) {
        var page = PageRequest.of(pageOffset, pageSize);

        return drinkRepository
                .findAll(page).get()
                .map(this::mapDrinkToGetDTO)
                .collect(Collectors.toList());
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
