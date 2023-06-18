package com.hcmus.auction.controller.definition;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PaginationController<T> {
    ResponseEntity<Page<T>> getAll(Integer page, Integer size, String sortBy, String orderBy);
}
