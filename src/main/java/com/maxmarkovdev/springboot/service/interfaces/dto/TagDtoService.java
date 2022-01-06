package com.maxmarkovdev.springboot.service.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.TagDto;

import java.util.List;

public interface TagDtoService {
    List<TagDto> getTagsByLetters(String letters);
}
