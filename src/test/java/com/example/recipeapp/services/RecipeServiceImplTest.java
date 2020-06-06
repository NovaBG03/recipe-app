package com.example.recipeapp.services;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.converters.RecipeCommandToRecipe;
import com.example.recipeapp.converters.RecipeToRecipeCommand;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.exceptions.NotFoundException;
import com.example.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    void getAllRecipes() {

        Recipe mockRecipe = new Recipe();
        Set<Recipe> mockRecipes = new HashSet<>();
        mockRecipes.add(mockRecipe);

        when(recipeRepository.findAll()).thenReturn(mockRecipes);

        Set<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.getRecipeById(id);

        assertNotNull(returnedRecipe, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(id);
        verify(recipeRepository, never()).findAll();
    }

    @Test()
    void getRecipeByIdNotFoundTest() {
        Long id = 1L;
        Optional recipeOptional = Optional.empty();

        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(id));
    }

    @Test
    void testSaveRecipeCommand() {
        Long id = 4L;
        RecipeCommand noIdRecipeCommand = new RecipeCommand();
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        Recipe noIdRecipe = new Recipe();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeCommandToRecipe.convert(noIdRecipeCommand)).thenReturn(noIdRecipe);
        when(recipeRepository.save(noIdRecipe)).thenReturn(recipe);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(noIdRecipeCommand);

        assertNotNull(savedCommand);
        assertEquals(id, savedCommand.getId());
    }

    @Test
    void testFindCommonById() {
        Long id = 4L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);

        RecipeCommand foundRecipe = recipeService.findCommandById(id);

        assertNotNull(foundRecipe);
        assertEquals(id, foundRecipe.getId());
    }

    @Test
    void testDeleteById() {
        //given
        Long id = 1L;

        //when
        recipeService.deleteById(id);

        //then
        verify(recipeRepository, times(1)).deleteById(id);
    }
}