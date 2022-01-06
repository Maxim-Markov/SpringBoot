package com.maxmarkovdev.springboot.controllers.rest;

import com.maxmarkovdev.springboot.configs.SwaggerConfig;
import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.TagDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = SwaggerConfig.RESOURCE_TAG_CONTROLLER)
@RestController
@RequestMapping("/api/user/tag")
public class TagResourceController {
    private final TagDtoService tagService;

    public TagResourceController(TagDtoService tagService) {
        this.tagService = tagService;
    }

    @Operation(summary = "Get 6 tags by it's first letters", responses = {
            @ApiResponse(description = "successfully get tags from DB", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Tag.class)))
    })
    @PostMapping("/letters")
    public ResponseEntity<?> getTagsByLetters(
            @ApiParam(value = "A JSON object containing letters for the next desired tag to attach to question", required = true)
            @RequestBody Map<String, String> json) {
        List<TagDto> tags = tagService.getTagsByLetters(json.get("letters"));
        return ResponseEntity.ok(tags);
    }
}
