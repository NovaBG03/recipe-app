package com.example.recipeapp.services;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.converters.RecipeCommandToRecipe;
import com.example.recipeapp.converters.RecipeToRecipeCommand;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "new description";
    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Transactional
    @Test
    void testSaveRecipeCommand() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        recipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, recipeRepository.findById(recipe.getId()).get().getDescription());
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
