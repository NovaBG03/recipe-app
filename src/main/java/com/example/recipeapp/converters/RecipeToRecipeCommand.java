package com.example.recipeapp.converters;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NoteToNoteCommand noteConverter;
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(NoteToNoteCommand noteConverter, CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter) {
        this.noteConverter = noteConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {

        if (recipe == null) {
            return null;
        }


        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setImage(recipe.getImage());
        recipeCommand.setNote(noteConverter.convert(recipe.getNote()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0) {
            recipe.getCategories()
                    .forEach(category -> recipeCommand.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
            recipe.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipeCommand;
    }
}
