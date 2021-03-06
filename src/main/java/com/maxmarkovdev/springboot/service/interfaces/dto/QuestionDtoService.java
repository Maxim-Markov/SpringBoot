package com.maxmarkovdev.springboot.service.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.QuestionDto;

import java.util.Optional;

public interface QuestionDtoService extends PageDtoService<QuestionDto> {
    Optional<QuestionDto> getQuestionDtoById(long id);
}
