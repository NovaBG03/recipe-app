package com.example.recipeapp.converters;

import com.example.recipeapp.commands.CategoryCommand;
import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.commands.NoteCommand;
import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.domain.Difficulty;
import com.example.recipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = 1;
    private static final String DESCRIPTION = "description";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final String DIRECTIONS = "directions";
    private static final Byte[] IMAGE = new Byte[0];
    private static final Integer PREP_TIME = 1;
    private static final Integer SERVINGS = 1;
    private static final String SOURCE = "source";
    private static final String URL = "url";
    private static final Long CATEGORY_ID_1 = 1L;
    private static final Long CATEGORY_ID_2 = 2L;
    private static final Long INGREDIENT_ID_1 = 1L;
    private static final Long INGREDIENT_ID_2 = 2L;
    private static final Long NOTE_ID = 1L;

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NoteCommandToNote(), new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setImage(IMAGE);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NoteCommand note = new NoteCommand();
        note.setId(NOTE_ID);
        recipeCommand.setNote(note);

        CategoryCommand category1 = new CategoryCommand();
        category1.setId(CATEGORY_ID_1);
        recipeCommand.getCategories().add(category1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CATEGORY_ID_2);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_ID_1);
        recipeCommand.getIngredients().add(ingredient1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_ID_2);
        recipeCommand.getIngredients().add(ingredient2);


        //when
        Recipe recipe = converter.convert(recipeCommand);

        //then
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(IMAGE, recipe.getImage());
        assertEquals(NOTE_ID, recipe.getNote().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}