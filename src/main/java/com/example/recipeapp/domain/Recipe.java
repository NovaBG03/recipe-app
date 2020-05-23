package com.example.recipeapp.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"note", "ingredients", "categories"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public void setNote(Note note) {
        if (note == null) {
            if (this.note != null) {
                this.note.setRecipe(null);
                this.note = null;
            }
            return;
        }

        this.note = note;
        note.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
