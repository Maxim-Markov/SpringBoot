package com.maxmarkovdev.springboot.dao.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagDtoDao {
    List<TagDto> getTagsByLetters(String letters);

    Map<Long, List<TagDto>> getMapTagsByQuestionIds(List<Long> questionIds);
}
