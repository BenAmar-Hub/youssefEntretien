package com.banque.kata.mappers;

import com.banque.kata.dtos.LivretDTO;
import com.banque.kata.entities.Livret;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LivretMapper extends GenericMapper<LivretDTO, Livret>  {
}
