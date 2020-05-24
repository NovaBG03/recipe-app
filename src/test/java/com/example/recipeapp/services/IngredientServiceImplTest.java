package com.example.recipeapp.services;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.converters.IngredientToIngredientCommand;
import com.example.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    @Test
    void testFindByRecipeIdAndIngredientId() {
        //given
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Long ingredientId1 = 1L;
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ingredientId1);

        Long ingredientId2 = 2L;
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredientId2);

        Long ingredientId3 = 3L;
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(ingredientId3);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);

        //when
        IngredientCommand foundIngredient = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId2);

        //then
        assertEquals(recipeId, foundIngredient.getRecipeId());
        assertEquals(ingredientId2, foundIngredient.getId());

        verify(recipeRepository, times(1)).findById(recipeId);
    }
}