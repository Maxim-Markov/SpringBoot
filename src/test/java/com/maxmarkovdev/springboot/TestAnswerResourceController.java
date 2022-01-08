package com.maxmarkovdev.springboot;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.maxmarkovdev.springboot.abstracts.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAnswerResourceController extends AbstractTestApi {

    private static final String USER_HAS_ROLE_ENTITY = "dataset/questionResourceController/users_has_roles.yml";
    private static final String VOTE_USER_ENTITY = "dataset/answerResourceController/vote/user_entity.yml";
    private static final String ROLE = "dataset/answerResourceController/vote/role.yml";
    private static final String ANSWER = "dataset/answerResourceController/vote/answer.yml";
    private static final String REPUTATION = "dataset/answerResourceController/vote/shouldNotVoteAgainOnAnswerByThisUser/reputation.yml";
    private static final String VOTE_ANSWER = "dataset/answerResourceController/vote/shouldNotVoteAgainOnAnswerByThisUser/votes_on_answers.yml";
    private static final String EMPTY_REPUTATION = "dataset/answerResourceController/vote/shouldNotVoteOnAnswerWithNonExistentId/reputation.yml";
    private static final String EMPTY_VOTE_ANSWER = "dataset/answerResourceController/vote/shouldNotVoteOnAnswerWithNonExistentId/votes_on_answers.yml";

    private static final String EXPECTED_REPUTATION_VOTED = "dataset/expected/answerResourceController/reputation.yml";
    private static final String EXPECTED_VOTE_ANSWER_VOTED = "dataset/expected/answerResourceController/votes_on_answers.yml";

    private final String urlAnswer = "/api/user/question/100/answer";

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {VOTE_USER_ENTITY, USER_HAS_ROLE_ENTITY, ROLE, ANSWER, EMPTY_VOTE_ANSWER, EMPTY_REPUTATION}, disableConstraints = true)
    @ExpectedDataSet(value = {ANSWER, EXPECTED_REPUTATION_VOTED, EXPECTED_VOTE_ANSWER_VOTED})
    public void shouldVoteForUnvotedAnswerByThisUser() throws Exception {
        mvc.perform(post(urlAnswer + "/100/upVote").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        mvc.perform(post(urlAnswer + "/101/downVote").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {VOTE_USER_ENTITY, USER_HAS_ROLE_ENTITY, ROLE, ANSWER, REPUTATION, VOTE_ANSWER}, disableConstraints = true)
    @ExpectedDataSet(value = {ANSWER, VOTE_ANSWER, REPUTATION})
    public void shouldNotVoteAgainOnAnswerByThisUser() throws Exception {
        mvc.perform(post(urlAnswer + "/100/upVote").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User is already voted"));

        mvc.perform(post(urlAnswer + "/100/downVote").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User is already voted"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {VOTE_USER_ENTITY, USER_HAS_ROLE_ENTITY, ROLE, ANSWER, EMPTY_VOTE_ANSWER, EMPTY_REPUTATION}, disableConstraints = true)
    @ExpectedDataSet(value = {ANSWER, EMPTY_VOTE_ANSWER, EMPTY_REPUTATION})
    public void shouldNotVoteOnAnswerWithNonExistentId() throws Exception {
        mvc.perform(post(urlAnswer + "/999/upVote").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Answer with this id does not exist"));
    }
}