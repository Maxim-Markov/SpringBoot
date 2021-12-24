package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

public interface QuestionService extends ReadWriteService<Question, Long> {

    Long countQuestions();
}
