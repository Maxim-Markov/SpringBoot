package com.maxmarkovdev.springboot.controllers.rest;

import com.maxmarkovdev.springboot.configs.SwaggerConfig;
import com.maxmarkovdev.springboot.mappers.QuestionMapper;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.dto.PageDto;
import com.maxmarkovdev.springboot.model.dto.QuestionCreateDto;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import com.maxmarkovdev.springboot.service.interfaces.QuestionService;
import com.maxmarkovdev.springboot.service.interfaces.dto.QuestionDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {SwaggerConfig.QUESTION_CONTROLLER})
@RestController
@RequestMapping("/api/user/question")
@Validated
public class QuestionResourceController {

    private final QuestionMapper questionMapper;

    private final QuestionService questionService;

    private final QuestionDtoService questionDtoService;

    public QuestionResourceController(QuestionMapper questionMapper,
                                      QuestionService questionService,
                                      QuestionDtoService questionDtoService) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.questionDtoService = questionDtoService;
    }

    @Operation(summary = "add new question", responses = {
            @ApiResponse(description = "Question was added to DB", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class))),
            @ApiResponse(description = "Received tags, description or title were empty or null", responseCode = "400")
    })
    @PostMapping()
    public ResponseEntity<QuestionDto> addQuestion(
            @ApiParam(value = "A JSON object containing question title, description and tags", required = true)
            @Valid @RequestBody final QuestionCreateDto questionCreateDto) {
        Question question = questionMapper.toModel(questionCreateDto);
        questionService.persist(question);
        return ResponseEntity.status(HttpStatus.OK).body(questionMapper.persistConvertToDto(question));
    }

    @Operation(summary = "counts all questions", responses = {
            @ApiResponse(description = "Question was counted", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("count")
    public ResponseEntity<Long> count() {
        Long count = questionService.countQuestions();
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Get list of questions by date", responses = {
            @ApiResponse(description = "Got list of questions", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = QuestionDto.class)))),
            @ApiResponse(description = "Too much value of parameters currPage or items - return empty List<QuestionDto>",
                    responseCode = "200"),
            @ApiResponse(description = "Wrong parameters or absent current page", responseCode = "400", content = @Content)
    })
    @GetMapping("new")
    public ResponseEntity<?> getQuestionByDate(@RequestParam int currPage,
                                 @RequestParam(required = false, defaultValue = "10") int items,
                                 @RequestParam(required = false, defaultValue = "0") List<Long> ignoredTags,
                                 @RequestParam(required = false) List<Long> trackedTags) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "QuestionByDate");
        map.put("ignoredTags", ignoredTags);
        map.put("trackedTags", trackedTags);
        return ResponseEntity.ok(questionDtoService.getPage(currPage, items, map));
    }


    @Operation(summary = "Get page with pagination by users' persist datetime", responses = {
            @ApiResponse(description = " success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class))),
            @ApiResponse(description = "there isn`t curPage parameter in url or parameters in url are not positives numbers", responseCode = "400")
    })
    @GetMapping()
    public ResponseEntity<?> getAllQuestionDtos(
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam @Positive(message = "current page must be positive number") int currPage,
            @ApiParam(value = "positive number representing number of items to show on page")
            @RequestParam(required = false, defaultValue = "10") @Positive(message = "items must be positive number") int items,
            @ApiParam(value = "list of tracked tags attached to question")
            @RequestParam(required = false)  List<@Positive(message = "ids of tracked tags must be positive numbers") Long> trackedId,
            @ApiParam(value = "list of ignored tags attached to question")
            @RequestParam(required = false) List<@Positive(message = "ids of ignored tags must be positive numbers") Long> ignoredId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "AllQuestions");
        map.put("trackedIds", trackedId);
        map.put("ignoredIds", ignoredId);
        PageDto<QuestionDto> page = questionDtoService.getPage(currPage, items, map);
        return ResponseEntity.ok(page);
    }
}
