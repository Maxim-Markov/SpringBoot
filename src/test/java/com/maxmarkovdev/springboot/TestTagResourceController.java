package com.maxmarkovdev.springboot;

import com.github.database.rider.core.api.dataset.DataSet;
import com.maxmarkovdev.springboot.abstracts.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestTagResourceController extends AbstractTestApi {

    private static final String TAG = "dataset/tagResourceController/relatedTags/tag.yml";
    private static final String SPECIAL_LETTERS_TAG = "dataset/tagResourceController/tagsByLetters/specialLettersTag.yml";
    private static final String USER_ENTITY = "dataset/tagResourceController/relatedTags/user.yml";

    private static final String url = "/api/user/tag/letters";

    @Test
    @DataSet(value = {TAG, USER_ENTITY}, disableConstraints = true)
    public void getTagsByLetters_returnCorrectResult() throws Exception {
        ResultActions response = mvc.perform(post(url).with(csrf())
                .content("{\"letters\": \"en\"}")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("seven","ten","eleven","thirteen")));
    }

    @Test
    @DataSet(value = {SPECIAL_LETTERS_TAG, USER_ENTITY}, disableConstraints = true)
    public void getTagsByLetters_moreSixMatches_returnSixCorrectTags() throws Exception {

        ResultActions response = mvc.perform(post(url).with(csrf())
                .content("{\"letters\": \"se\"}")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        containsString("se"),
                        containsString("se"),
                        containsString("se"),
                        containsString("se"),
                        containsString("se"),
                        containsString("se"))));
    }
}