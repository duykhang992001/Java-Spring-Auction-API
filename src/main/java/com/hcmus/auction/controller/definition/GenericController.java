package com.hcmus.auction.controller.definition;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface GenericController<T, ID> {
    ResponseEntity<Page<T>> getAll(Integer page, Integer size);
    ResponseEntity<?> getById(ID id);
}
