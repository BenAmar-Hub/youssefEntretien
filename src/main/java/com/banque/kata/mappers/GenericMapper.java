package com.banque.kata.mappers;

import java.util.List;

public interface GenericMapper <T,I>{

    List<T> fromEntitiesToDtoList(List<I> i);

    List<I> fromDtoListToEntities(List<T> t);

    T fromEntityToDto(I i);

    I fromDtoToEntity(T t);
}
