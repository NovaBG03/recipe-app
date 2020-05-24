package com.example.recipeapp.controllers;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController controller;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetRecipe() throws Exception {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);

        mvc.perform(get("/recipe/" + id + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attribute("recipe", recipe));
    }

    @Test
    void testNewRecipe() throws Exception {

        mvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testSaveOfUpdate() throws Exception{
        Long id = 2L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + id + "/show/"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        Long id = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        when(recipeService.findCommonById(anyLong())).thenReturn(recipeCommand);

        mvc.perform(get("/recipe/" + id + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        Long id = 1L;

        mvc.perform(get("/recipe/" + id + "/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(id);
    }
}