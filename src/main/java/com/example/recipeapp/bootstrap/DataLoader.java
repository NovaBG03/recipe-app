package com.example.recipeapp.bootstrap;

import com.example.recipeapp.domain.*;
import com.example.recipeapp.repositories.CategoryRepository;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Recipe> recipes = getRecipes();
        recipeRepository.saveAll(recipes);
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure piece = unitOfMeasureRepository.findByDescription("Piece").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();

        Category mexican = categoryRepository.findByName("Mexican").get();

        Recipe recipe = new Recipe();
        recipe.setDescription("Perfect Guacamole");
        recipe.setPrepTime(10);
        recipe.setCookTime(0);
        recipe.setServings(4);
        recipe.setSource("simply recipes");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setDirections("mash");

        Note note = new Note();
        note.setNotes("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.  Place in a bowl." +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        recipe.setNote(note);
        note.setRecipe(recipe);

        Ingredient avocado = new Ingredient();
        avocado.setDescription("ripe avocados");
        avocado.setAmount(new BigDecimal(2));
        avocado.setUnitOfMeasure(piece);
        avocado.setRecipe(recipe);
        recipe.getIngredients().add(avocado);

        Ingredient salt = new Ingredient();
        salt.setDescription("salt, more to taste");
        salt.setAmount(new BigDecimal(0.25));
        salt.setUnitOfMeasure(teaspoon);
        salt.setRecipe(recipe);
        recipe.getIngredients().add(salt);

        Ingredient limeJuice = new Ingredient();
        limeJuice.setDescription("fresh lime juice or lemon juice");
        limeJuice.setAmount(new BigDecimal(1));
        limeJuice.setUnitOfMeasure(tablespoon);
        limeJuice.setRecipe(recipe);
        recipe.getIngredients().add(salt);

        Ingredient redOnion = new Ingredient();
        redOnion.setDescription("minced red onion or thinly sliced green onion");
        redOnion.setAmount(new BigDecimal(2));
        redOnion.setUnitOfMeasure(tablespoon);
        redOnion.setRecipe(recipe);
        recipe.getIngredients().add(redOnion);

        Ingredient chiles = new Ingredient();
        chiles.setDescription("serrano chiles, stems and seeds removed, minced");
        chiles.setAmount(new BigDecimal(1));
        chiles.setUnitOfMeasure(piece);
        chiles.setRecipe(recipe);
        recipe.getIngredients().add(chiles);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        cilantro.setAmount(new BigDecimal(2));
        cilantro.setUnitOfMeasure(tablespoon);
        cilantro.setRecipe(recipe);
        recipe.getIngredients().add(cilantro);

        Ingredient blackPepper = new Ingredient();
        blackPepper.setDescription("freshly grated black pepper");
        blackPepper.setAmount(new BigDecimal(1));
        blackPepper.setUnitOfMeasure(dash);
        blackPepper.setRecipe(recipe);
        recipe.getIngredients().add(blackPepper);

        Ingredient tomato = new Ingredient();
        tomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
        tomato.setAmount(new BigDecimal(0.5));
        tomato.setUnitOfMeasure(piece);
        tomato.setRecipe(recipe);
        recipe.getIngredients().add(tomato);

        Ingredient redRadishes = new Ingredient();
        redRadishes.setDescription("red radishes or jicama, to garnish");
        redRadishes.setAmount(new BigDecimal(1));
        redRadishes.setUnitOfMeasure(piece);
        redRadishes.setRecipe(recipe);
        recipe.getIngredients().add(redRadishes);

        Ingredient tortilla = new Ingredient();
        tortilla.setDescription("tortilla chips, to serve");
        tortilla.setAmount(new BigDecimal(1));
        tortilla.setUnitOfMeasure(piece);
        tortilla.setRecipe(recipe);
        recipe.getIngredients().add(tortilla);

        recipe.setDifficulty(Difficulty.EASY);

        recipe.getCategories().add(mexican);

        recipes.add(recipe);

        System.out.println("Loading Recipes...");
        return recipes;
    }
}
