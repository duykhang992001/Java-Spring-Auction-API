package com.hcmus.auction.controller.definition;

import org.springframework.http.ResponseEntity;

public interface GenericController<T, ID> {
    ResponseEntity<?> getById(ID id);
}
