package com.example.recipeapp.services;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.commands.UnitOfMeasureCommand;
import com.example.recipeapp.converters.*;
import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.domain.UnitOfMeasure;
import com.example.recipeapp.repositories.IngredientRepository;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    RecipeToRecipeCommand recipeToRecipeCommand;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {

        this.ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        this.ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientRepository, unitOfMeasureRepository,
                ingredientToIngredientCommand, ingredientCommandToIngredient);
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

    @Test
    void testUpdateIngredientCommand() {
        //given
        Long ingredientId = 1L;
        Long uomId = 2L;
        Long recipeId = 3L;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        ingredientCommand.getUnitOfMeasure().setId(uomId);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomId);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(recipeId);
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(ingredientId);
        savedRecipe.getIngredients().iterator().next().setUnitOfMeasure(uom);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(savedRecipe));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        when(unitOfMeasureRepository.findById(uomId)).thenReturn(Optional.of(uom));

        //when
        IngredientCommand savedIngredient = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(ingredientId, savedIngredient.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testSaveNewIngredientCommand() {
        //given
        String description = "description";
        BigDecimal amount = new BigDecimal(1);

        Long ingredientId = null;
        Long newIngredientId = 5L;
        Long uomId = 2L;
        Long recipeId = 3L;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        ingredientCommand.getUnitOfMeasure().setId(uomId);
        ingredientCommand.setDescription(description);
        ingredientCommand.setAmount(amount);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomId);

        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(newIngredientId);
        newIngredient.setUnitOfMeasure(uom);
        newIngredient.setDescription(description);
        newIngredient.setAmount(amount);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(recipeId);
        savedRecipe.addIngredient(newIngredient);

        Recipe initialRecipe = new Recipe();
        initialRecipe.setId(recipeId);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(initialRecipe));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedIngredient = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(newIngredientId, savedIngredient.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDeleteIngredientById() {
        //given
        Long ingredientId = 1L;
        Long recipeId = 2L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        //when
        ingredientService.deleteIngredientById(recipeId, ingredientId);

        //then
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).save(recipe);
    }
}