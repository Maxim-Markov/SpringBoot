package com.maxmarkovdev.springboot.mappers;


import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.dto.QuestionCreateDto;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {TagMapper.class})
public abstract class QuestionMapper {

    @Mapping(source = "question.tags", target = "listTagDto")
    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")
    @Mapping(target = "countAnswer", constant = "0L")
    @Mapping(target = "countValuable", constant = "0L")
    @Mapping(target = "viewCount", constant = "0L")
    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.user.name", target = "authorName")
    @Mapping(source = "question.user.imageLink", target = "authorImage")
    @Mapping(target = "authorReputation", constant = "0L")
    public abstract QuestionDto persistConvertToDto(Question question);

    public abstract Question toModel(QuestionCreateDto questionCreateDto);
    
}
