package com.maxmarkovdev.springboot.service.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.TagDtoDao;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.TagDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    @Autowired
    private final TagDtoDao tagDao;

    public TagDtoServiceImpl(TagDtoDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<TagDto> getTagsByLetters(String letters) {
        return tagDao.getTagsByLetters(letters);
    }

}

