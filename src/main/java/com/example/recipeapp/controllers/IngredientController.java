package com.example.recipeapp.controllers;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.services.IngredientService;
import com.example.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommonById(Long.valueOf(id));

        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {

        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("ingredient", ingredientCommand);

        return "/recipe/ingredient/view";
    }
}
