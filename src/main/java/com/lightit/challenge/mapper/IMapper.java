package com.lightit.challenge.mapper;

public interface IMapper<T, DTO> {

    DTO toDto(T entity);

    T toEntity(DTO dto);

}
