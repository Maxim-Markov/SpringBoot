package com.maxmarkovdev.springboot.controllers.rest;

import com.maxmarkovdev.springboot.configs.SwaggerConfig;
import com.maxmarkovdev.springboot.model.Answer;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.dto.AnswerDto;
import com.maxmarkovdev.springboot.model.vote.VoteType;
import com.maxmarkovdev.springboot.service.interfaces.AnswerService;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import com.maxmarkovdev.springboot.service.interfaces.VoteAnswerService;
import com.maxmarkovdev.springboot.service.interfaces.dto.AnswerDtoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.maxmarkovdev.springboot.model.vote.VoteType.DOWN_VOTE;
import static com.maxmarkovdev.springboot.model.vote.VoteType.UP_VOTE;


@Api(tags = {SwaggerConfig.ANSWER_CONTROLLER})
@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
public class AnswerResourceController {

    private final AnswerDtoService answerDtoService;
    private final VoteAnswerService voteAnswerService;
    private final UserService userService;

    public AnswerResourceController(AnswerDtoService answerDtoService,
                                    VoteAnswerService voteAnswerService, UserService userService) {
        this.answerDtoService = answerDtoService;
        this.voteAnswerService = voteAnswerService;
        this.userService = userService;
    }

    @Operation(summary = "Get list of answers by question id", responses = {
            @ApiResponse(description = "Got list of answers", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AnswerDto.class)))),
            @ApiResponse(description = "No answers with such question id - return empty list", responseCode = "200"),
            @ApiResponse(description = "No question id", responseCode = "404"),
            @ApiResponse(description = "Wrong type of question id", responseCode = "400")
    })
    @GetMapping
    public ResponseEntity<?> getAnswerByQuestionId(@PathVariable Long questionId) {
        return  ResponseEntity.ok(answerDtoService.getAnswerById(questionId));
    }

    @Operation(summary = "Vote up for answer", responses = {
            @ApiResponse(responseCode = "200", description = "Vote up successful. Author's reputation increased"),
            @ApiResponse(responseCode = "400", description = "Cannot vote")})
    @PostMapping("{answerId}/upVote")
    public ResponseEntity<?> upVote(@PathVariable final Long questionId,
                                    @PathVariable final Long answerId) {
        return vote(answerId, UP_VOTE);
    }

    @Operation(summary = "Vote down for answer", responses = {
            @ApiResponse(responseCode = "200", description = "Vote down successful. Author's reputation decreased"),
            @ApiResponse(responseCode = "400", description = "Cannot vote")})
    @PostMapping("{answerId}/downVote")
    public ResponseEntity<?> downVote(@PathVariable final Long questionId,
                                      @PathVariable final Long answerId) {
        return vote(answerId, DOWN_VOTE);
    }

    private ResponseEntity<?> vote(@PathVariable Long answerId, VoteType voteType) {
        String username = ((UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
        User user = userService.findByName(username).orElseThrow();
        if (voteAnswerService.isUserNonVoted(answerId, user.getId())) {
            Long totalCount = voteAnswerService.vote(answerId, user, voteType);
            return totalCount >= 0 ?
                    ResponseEntity.ok(totalCount) :
                    ResponseEntity.badRequest().body("Answer with this id does not exist");
        }
        return ResponseEntity.badRequest().body("User is already voted");
    }
}

