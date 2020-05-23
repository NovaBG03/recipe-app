package com.example.recipeapp.converters;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NoteCommandToNote noteConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(NoteCommandToNote noteConverter, CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.noteConverter = noteConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {

        if (recipeCommand == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setSource(recipeCommand.getSource());
        recipe.setServings(recipeCommand.getServings());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setImage(recipeCommand.getImage());
        recipe.setNote(noteConverter.convert(recipeCommand.getNote()));

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0) {
            recipeCommand.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
