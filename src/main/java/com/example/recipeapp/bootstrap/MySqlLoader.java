package com.example.recipeapp.bootstrap;

import com.example.recipeapp.domain.*;
import com.example.recipeapp.repositories.CategoryRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class MySqlLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public MySqlLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryRepository.count() == 0) {
            log.debug("Loading Categories...");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0) {
            log.debug("Loading UOMs...");
            loadUOMs();
        }

    }

    private void loadCategories() {
        categoryRepository.save(new Category("American"));
        categoryRepository.save(new Category("Italian"));
        categoryRepository.save(new Category("Mexican"));
        categoryRepository.save(new Category("Fast Food"));
    }

    private void loadUOMs() {
        unitOfMeasureRepository.save(new UnitOfMeasure("Teaspoon"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Tablespoon"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Cup"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Pinch"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Ounce"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Piece"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Dash"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Each"));
        unitOfMeasureRepository.save(new UnitOfMeasure("Pint"));
    }

}
