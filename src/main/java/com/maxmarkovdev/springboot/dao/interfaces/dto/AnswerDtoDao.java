package com.maxmarkovdev.springboot.dao.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.AnswerDto;

import java.util.List;

public interface AnswerDtoDao {
    List<AnswerDto> getAnswerById(Long id);
}
