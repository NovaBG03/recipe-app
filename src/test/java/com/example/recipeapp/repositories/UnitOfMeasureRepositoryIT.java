package com.example.recipeapp.repositories;

import com.example.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescriptionTeaspoon() {
        String teaspoon = "Teaspoon";

        Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByDescription(teaspoon);

        assertEquals(teaspoon, optional.get().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        String cup = "Cup";

        Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByDescription(cup);

        assertEquals(cup, optional.get().getDescription());
    }
}