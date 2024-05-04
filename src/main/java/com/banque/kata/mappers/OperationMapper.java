package com.banque.kata.mappers;

import com.banque.kata.dtos.OperationDTO;
import com.banque.kata.entities.Operation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OperationMapper extends GenericMapper<OperationDTO, Operation>{

}
