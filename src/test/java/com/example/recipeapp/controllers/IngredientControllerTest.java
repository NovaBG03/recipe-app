package com.example.recipeapp.controllers;

import com.example.recipeapp.commands.IngredientCommand;
import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.commands.UnitOfMeasureCommand;
import com.example.recipeapp.domain.UnitOfMeasure;
import com.example.recipeapp.services.IngredientService;
import com.example.recipeapp.services.RecipeService;
import com.example.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void listIngredients() throws Exception {
        Long id = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        when(recipeService.findCommandById(id)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + id + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(id);
    }

    @Test
    void testShowIngredient() throws Exception{
        Long recipeId = 1L;
        Long ingredientId = 1L;

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);

        recipeCommand.getIngredients().add(ingredientCommand);
        ingredientCommand.setRecipe(recipeCommand);
        ingredientCommand.setRecipeId(recipeId);

        when(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId)).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("/recipe/ingredient/view"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(recipeId, ingredientId);
    }

    @Test
    void testGetUpdateView() throws Exception {
        Long recipeId = 1L;
        Long ingredientId = 1L;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId(recipeId);

        when(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId)).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/update" ))
            .andExpect(status().isOk())
            .andExpect(view().name("/recipe/ingredient/ingredientform"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void testSaveIngredientCommand() throws Exception{
        Long recipeId = 1L;
        Long ingredientId = 3L;

        IngredientCommand savedIngredient = new IngredientCommand();
        savedIngredient.setRecipeId(recipeId);
        savedIngredient.setId(ingredientId);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(savedIngredient);

        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", ingredientId.toString())
            .param("description", "some description"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
    }

    @Test
    void testNewIngredientForm() throws Exception {
        Long recipeId = 1L;
        Long uomId = 2L;
        String description = "description";

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(uomId);
        unitOfMeasureCommand.setDescription(description);

        Set<UnitOfMeasureCommand> uoms = new HashSet<>();
        uoms.add(unitOfMeasureCommand);

        when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(uoms);
        when(recipeService.findCommandById(recipeId)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));;
    }

    @Test
    void testDeleteIngredient() throws Exception {
        Long ingredientId = 1L;
        Long recipeId = 2L;

        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));

        verify(ingredientService, times(1)).deleteIngredientById(recipeId, ingredientId);
    }
}