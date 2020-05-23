package com.example.recipeapp.commands;

import com.example.recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Integer cookTime;
    private String description;
    private Difficulty difficulty;
    private String directions;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private NoteCommand note;
    private Integer prepTime;
    private Integer servings;
    private String source;
    private String url;
}
