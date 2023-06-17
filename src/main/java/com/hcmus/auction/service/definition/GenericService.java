package com.hcmus.auction.service.definition;

public interface GenericService<T, ID> {
    T getById(ID id);
}
