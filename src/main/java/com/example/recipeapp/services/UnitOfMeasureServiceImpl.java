package com.example.recipeapp.services;

import com.example.recipeapp.commands.UnitOfMeasureCommand;
import com.example.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipeapp.domain.UnitOfMeasure;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasure() {
        Iterable<UnitOfMeasure> uoms = unitOfMeasureRepository.findAll();

        Set<UnitOfMeasureCommand> uomCommands = StreamSupport.stream(uoms.spliterator(), false)
                .map(unitOfMeasure -> unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure))
                .collect(Collectors.toSet());

        return uomCommands;
    }
}
