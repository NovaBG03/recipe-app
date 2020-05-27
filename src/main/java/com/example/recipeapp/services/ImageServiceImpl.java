package com.example.recipeapp.services;

import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepository.findById(recipeId).get();

        try {
            byte[] bytes = file.getBytes();
            int bytesLength = bytes.length;

            Byte[] image = new Byte[bytesLength];

            for (int i = 0; i < bytesLength; i++) {
                image[i] = bytes[i];
            }

            recipe.setImage(image);
        }
        catch (IOException e) {
            //todo catch exception

            e.printStackTrace();
        }

        recipeRepository.save(recipe);
    }
}
