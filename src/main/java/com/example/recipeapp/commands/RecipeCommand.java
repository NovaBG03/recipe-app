package com.example.recipeapp.commands;

import com.example.recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private Set<CategoryCommand> categories = new HashSet<>();

    @Min(0)
    @Max(999)
    private Integer cookTime;

    @Min(0)
    @Max(999)
    private Integer prepTime;

    @Min(0)
    @Max(100)
    private Integer servings;

    @Size(max = 255)
    private String source;

    @NotEmpty
    @Size(max = 255)
    private String description;

    @NotEmpty
    @Size(max = 255)
    private String directions;

    @URL
    @Size(max = 255)
    private String url;

    @NotNull
    private Difficulty difficulty;

    private NoteCommand note;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
}
