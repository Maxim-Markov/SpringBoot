package com.maxmarkovdev.springboot.mappers;

import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toDto(Tag tag);

    Tag toModel(TagDto tagDto);

}
