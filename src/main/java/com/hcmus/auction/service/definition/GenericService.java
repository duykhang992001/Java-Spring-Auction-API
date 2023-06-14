package com.hcmus.auction.service.definition;

import org.springframework.data.domain.Page;

public interface GenericService<T, ID> {
    Page<T> getAll(Integer page, Integer size);
    T getById(ID id);
}
