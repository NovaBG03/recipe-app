package com.example.recipeapp.controllers;

import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        //given
        Set<Recipe> mockRecipes = new HashSet<>();
        for (long i = 1L; i <= 2; i++) {
            Recipe mockRecipe = new Recipe();
            mockRecipe.setId(i);
            mockRecipes.add(mockRecipe);
        }

        when(recipeService.getAllRecipes()).thenReturn(mockRecipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getAllRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> capturedRecipes = argumentCaptor.getValue();
        assertEquals(mockRecipes.size(), capturedRecipes.size());
    }
}