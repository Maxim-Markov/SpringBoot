package com.maxmarkovdev.springboot.service.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.QuestionDtoDao;
import com.maxmarkovdev.springboot.dao.interfaces.dto.TagDtoDao;
import com.maxmarkovdev.springboot.model.dto.PageDto;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImpl extends PageDtoServiceImpl<QuestionDto> implements QuestionDtoService {

    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;

    public QuestionDtoServiceImpl(TagDtoDao tagDtoDao, QuestionDtoDao questionDtoDao) {
        this.tagDtoDao = tagDtoDao;
        this.questionDtoDao = questionDtoDao;
    }

    @Override
    @Transactional
    public PageDto<QuestionDto> getPage(int currentPageNumber, int itemsOnPage, Map<Object, Object> map) {

        PageDto<QuestionDto> pageDto = super.getPage(currentPageNumber,itemsOnPage,map);
        List<QuestionDto> questionDtos = pageDto.getItems();

        List<Long> questionIds = questionDtos.stream()
                .map(QuestionDto::getId)
                .collect(Collectors.toList());

        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getMapTagsByQuestionIds(questionIds);
        for (QuestionDto questionDto : questionDtos) {
            questionDto.setListTagDto(tagsMap.get(questionDto.getId()));
        }

        pageDto.setItems(questionDtos);
        return pageDto;
    }

    @Override
    @Transactional
    public Optional<QuestionDto> getQuestionDtoById(long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getQuestionDtoById(id);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagDtoListByQuestionId(id)));
        return questionDto;
    }
}
