package com.maxmarkovdev.springboot.service.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.TagDtoDao;
import com.maxmarkovdev.springboot.model.dto.PageDto;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImpl extends PageDtoServiceImpl<QuestionDto> implements QuestionDtoService {

    private final TagDtoDao tagDtoDao;

    public QuestionDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
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
}
