package com.hcmus.auction.service.definition;

import java.util.List;

public interface GenericService<T, ID> {
    List<T> getAll();
    T getById(ID id);
}
