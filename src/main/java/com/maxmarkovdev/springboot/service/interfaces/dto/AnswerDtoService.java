package com.maxmarkovdev.springboot.service.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.AnswerDto;

import java.util.List;

public interface AnswerDtoService {
    List<AnswerDto> getAnswerById(Long id);
}
