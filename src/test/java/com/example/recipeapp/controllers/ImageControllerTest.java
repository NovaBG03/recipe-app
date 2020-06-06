package com.example.recipeapp.controllers;

import com.example.recipeapp.commands.RecipeCommand;
import com.example.recipeapp.services.ImageService;
import com.example.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void testGetUploadForm() throws Exception {
        Long recipeId = 1L;

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        when(recipeService.findCommandById(recipeId)).thenReturn(recipeCommand);

        mvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(recipeId);
    }


    @Test
    void testGetUploadFormNumbersFormatException() throws Exception {
        String id = "some string";

        mvc.perform(get("/recipe/" + id + "/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void testPostImage() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "test.txt", "text/plain",
                        "Content".getBytes());
        Long id = 1L;

        mvc.perform(multipart("/recipe/" + id + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + id + "/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any(MultipartFile.class));
    }

    @Test
    void testRenderImageFromDb() throws Exception {
        Long id = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        String str = "this is the image";
        Byte[] image = new Byte[str.getBytes().length];

        for (int i = 0; i < image.length; i++) {
            image[i] = str.getBytes()[i];
        }
        recipeCommand.setImage(image);

        when(recipeService.findCommandById(id)).thenReturn(recipeCommand);

        MockHttpServletResponse response = mvc.perform(get("/recipe/" + id + "/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] bytes = response.getContentAsByteArray();
        assertEquals(image.length, bytes.length);
    }
}