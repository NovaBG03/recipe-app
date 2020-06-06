package com.example.recipeapp.commands;

import com.example.recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @Length(min = 0, max = 255)
    private String source;

    @NotEmpty
    @Length(min = 0, max = 255)
    private String description;

    @NotEmpty
    @Length(min = 0, max = 255)
    private String directions;

    @URL
    private String url;

    @NotNull
    private Difficulty difficulty;

    private NoteCommand note;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
}
