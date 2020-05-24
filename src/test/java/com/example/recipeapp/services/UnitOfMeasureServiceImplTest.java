package com.example.recipeapp.services;

import com.example.recipeapp.commands.UnitOfMeasureCommand;
import com.example.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipeapp.domain.UnitOfMeasure;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureService unitOfMeasureService;

    public UnitOfMeasureServiceImplTest() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @BeforeEach
    void setUp() {
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUnitsOfMeasure() {
        //given
        Long uomId1 = 1L;
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(uomId1);

        Long uomId2 = 2L;
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure1.setId(uomId2);

        Long uomId3 = 3L;
        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure1.setId(uomId3);

        Set<UnitOfMeasure> set = new HashSet<>();
        set.add(unitOfMeasure1);
        set.add(unitOfMeasure2);
        set.add(unitOfMeasure3);

        when(unitOfMeasureRepository.findAll()).thenReturn(set);

        //when
        Set<UnitOfMeasureCommand> uomCommands = unitOfMeasureService.listAllUnitsOfMeasure();

        //then
        assertEquals(set.size(), uomCommands.size());

        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}