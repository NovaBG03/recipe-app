package com.example.recipeapp.services;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.converters.IngredientCommandToIngredient;
import com.example.recipeapp.converters.IngredientToIngredientCommand;
import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.repositories.IngredientRepository;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientRepository ingredientRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (!optionalRecipe.isPresent()) {
            //todo manage error
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!optionalIngredientCommand.isPresent()) {
            //todo manage error
        }

        return optionalIngredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            //todo manage error
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient foundIngredient = ingredientOptional.get();

            foundIngredient.setDescription(ingredientCommand.getDescription());
            foundIngredient.setAmount(ingredientCommand.getAmount());
            foundIngredient.setUnitOfMeasure(unitOfMeasureRepository
                    .findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UnitOfMeasure not found"))); //todo fixed this
        }
        else {
            Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
            recipe.addIngredient(newIngredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredient = savedRecipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (!savedIngredient.isPresent()) {
            savedIngredient = savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId()
                            .equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedIngredient.get());
    }

    @Override
    @Transactional
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Invalid recipe id.");
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("Invalid ingredient id.");
        }

        Ingredient ingredient = ingredientOptional.get();

        recipe.removeIngredient(ingredient);

        recipeRepository.save(recipe);
        ingredientRepository.delete(ingredient);
    }
}
