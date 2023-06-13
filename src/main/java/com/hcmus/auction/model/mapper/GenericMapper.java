package com.hcmus.auction.model.mapper;

public interface GenericMapper<T, DTO> {
    T toEntity(DTO object);
    DTO toDTO(T object);
}
