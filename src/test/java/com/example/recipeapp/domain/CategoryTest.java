package com.example.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 12L;
        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    void getName() {
        String nameValue = "category";
        category.setName(nameValue);

        assertEquals(nameValue, category.getName());
    }
}