package com.hcmus.auction.controller.definition;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericController<T, ID> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> getById(ID id);
}
