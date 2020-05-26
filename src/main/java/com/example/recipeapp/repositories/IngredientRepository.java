package com.example.recipeapp.repositories;

import com.example.recipeapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}
