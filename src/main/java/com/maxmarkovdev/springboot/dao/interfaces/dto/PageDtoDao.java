package com.maxmarkovdev.springboot.dao.interfaces.dto;

import java.util.List;
import java.util.Map;

public interface PageDtoDao<T> {
    List<T> getItems(Map<Object, Object> param);
    long getTotalResultCount(Map<Object, Object> param);
}
