package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.QuestionDao;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.QuestionService;
import com.maxmarkovdev.springboot.service.interfaces.TagService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final QuestionDao questionDao;
    private final TagService tagService;


    public QuestionServiceImpl(QuestionDao questionDao, TagService tagService) {
        super(questionDao);
        this.tagService = tagService;
        this.questionDao = questionDao;
    }

    @Override
    public Long countQuestions() { return questionDao.countQuestions();}

    @Override
    public void persist(Question question){

        Set<String> tagNames = question.getTags().stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> existedTags = tagService.getByAllNames(tagNames);
        Set<String> existedTagNames = existedTags.stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> tagsToPersist = question.getTags();
        tagsToPersist.removeIf(tag -> existedTagNames.contains(tag.getName()));

        if(!tagsToPersist.isEmpty()) {
            tagService.persistAll(tagsToPersist);
        }
        List<Tag> managedTags = new ArrayList<>(tagsToPersist);
        managedTags.addAll(existedTags);
        question.setTags(managedTags);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        question.setUser(user);

        super.persist(question);
    }

}
