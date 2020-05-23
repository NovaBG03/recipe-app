package com.example.recipeapp.converters;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.uomConverter = unitOfMeasureCommandToUnitOfMeasure;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {

        if (ingredientCommand == null) {
            return null;
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));

        return ingredient;
    }
}
