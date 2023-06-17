package com.hcmus.auction.service.definition;

import org.springframework.data.domain.Page;

public interface PaginationService<T> {
    Page<T> getAll(Integer page, Integer size);
}
