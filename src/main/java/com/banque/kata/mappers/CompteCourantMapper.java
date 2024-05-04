package com.banque.kata.mappers;

import com.banque.kata.dtos.CompteCourantDTO;
import com.banque.kata.entities.CompteCourant;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompteCourantMapper extends GenericMapper<CompteCourantDTO, CompteCourant>  {
}
