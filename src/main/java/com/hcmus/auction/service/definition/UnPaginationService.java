package com.hcmus.auction.service.definition;

import java.util.List;

public interface UnPaginationService<T> {
    List<T> getAll();
}
