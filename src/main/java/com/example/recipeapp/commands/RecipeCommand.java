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

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(value = 100, message = "{0} must be less than {1}")
    private Integer servings;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @NotBlank
    @Size(min = 3, max = 2550)
    private String directions;

    @NotBlank
    @Size(min = 3, max = 255)
    private String source;

    @URL
    @Size(max = 255)
    private String url;

    @NotNull
    private Difficulty difficulty;

    private NoteCommand note;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}
