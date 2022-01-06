package com.maxmarkovdev.springboot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    public QuestionDto(Long id, String title, Long authorId,
                       String authorName, String authorImage, Long authorReputation,
                       String description, LocalDateTime persistDateTime,
                       LocalDateTime lastUpdateDateTime, Long viewCount,
                       Long countAnswer, Long countValuable) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.authorReputation = authorReputation;
        this.description = description;
        this.persistDateTime = persistDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.viewCount = viewCount;
        this.countAnswer = countAnswer;
        this.countValuable = countValuable;
    }

    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private String authorImage;
    private String description;
    private Long viewCount;
    private Long countAnswer;
    private Long countValuable;
    private Long authorReputation;
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<TagDto> listTagDto = new ArrayList<>();

    public QuestionDto(Long id, String title, Long authorId, String authorName, String authorImage, String description,
                       Long viewCount, Long countAnswer, Long countValuable, Long authorReputation,
                       LocalDateTime persistDateTime, LocalDateTime lastUpdateDateTime) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.description = description;
        this.viewCount = viewCount;
        this.countAnswer = countAnswer;
        this.countValuable = countValuable;
        this.authorReputation = authorReputation;
        this.persistDateTime = persistDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
