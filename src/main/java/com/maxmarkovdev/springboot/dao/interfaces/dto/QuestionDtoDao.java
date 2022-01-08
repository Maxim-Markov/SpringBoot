package com.maxmarkovdev.springboot.dao.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.QuestionDto;

import java.util.Optional;

public interface QuestionDtoDao {

    Optional<QuestionDto> getQuestionDtoById(long id);
}
