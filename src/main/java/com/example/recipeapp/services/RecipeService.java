package com.example.recipeapp.services;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommonById(Long id);

    void deleteById(Long id);
}
