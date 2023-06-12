package com.hcmus.auction.model.mapper;

public interface GenericMapper<T, DTO> {
    T toT(DTO object);
    DTO toDTO(T object);
}
