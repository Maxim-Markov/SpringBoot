package com.maxmarkovdev.springboot.service.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.PageDto;

import java.util.Map;

public interface PageDtoService<T> {
    PageDto<T> getPage(int currentPageNumber, int itemsOnPage,
                       Map<Object, Object> map);
}
