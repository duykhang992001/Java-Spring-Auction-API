package com.hcmus.auction.controller.definition;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UnPaginationController<T> {
    ResponseEntity<List<T>> getAll();
}
