package com.getourguide.interview.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        return entity == null ? null : modelMapper.map(entity, dtoClass);
    }

    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        return dto == null ? null : modelMapper.map(dto, entityClass);
    }

    public <E, D> List<D> convertListToDtoList(List<E> entities, Class<D> dtoClass) {
        return entities == null ? null : entities.stream()
                .map(entity -> convertToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <D, E> List<E> convertListToEntityList(List<D> dtos, Class<E> entityClass) {
        return dtos == null ? null : dtos.stream()
                .map(dto -> convertToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }
}