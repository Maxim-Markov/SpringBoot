package com.maxmarkovdev.springboot;

import com.github.database.rider.core.api.dataset.DataSet;
import com.maxmarkovdev.springboot.abstracts.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestUserResourceController extends AbstractTestApi {
    private static final String USER_ENTITY = "dataset/userResourceController/userDto.yml";
    private static final String ROLE_REP_ENTITY = "dataset/userResourceController/roleReputation.yml";
    private static final String REPUTATION_ENTITY = "dataset/userResourceController/reputation.yml";
    private static final String QUESTION_ENTITY = "dataset/userResourceController/Question.yml";
    private static final String ANSWER_ENTITY = "dataset/userResourceController/answer.yml";
    private static final String USER_BY_PERSIST_DATE = "dataset/userResourceController/paginationByPersistDate/user.yml";
    private static final String ROLE_ENTITY = "dataset/userResourceController/role.yml";
    private static final String REPUTATION_BY_PERSIST_DATE = "dataset/userResourceController/paginationByPersistDate/reputation.yml";

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_ENTITY, ROLE_REP_ENTITY, REPUTATION_ENTITY, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoById() throws Exception {

        //user exist
        mvc.perform(get("/api/user/103").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.id", is(103)))
                .andExpect(jsonPath("$.fullName", is("Roman")))
                .andExpect(jsonPath("$.email", is("Rom@ya.ru")))
                .andExpect(jsonPath("$.city", is("Surgut")))
                .andExpect(jsonPath("$.linkImage", nullValue()))
                .andExpect(jsonPath("$.reputation", is(41)));

        //id is absent
        mvc.perform(get("/api/user/").with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.fullName").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.city").doesNotExist())
                .andExpect(jsonPath("$.linkImage").doesNotExist())
                .andExpect(jsonPath("$.reputation").doesNotExist())
                .andExpect(jsonPath("$").doesNotExist());

        //user is absent
        mvc.perform(get("/api/user/1000").with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.fullName").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.city").doesNotExist())
                .andExpect(jsonPath("$.linkImage").doesNotExist())
                .andExpect(jsonPath("$.reputation").doesNotExist())
                .andExpect(jsonPath("$").value("User is absent or wrong Id"));

        //wrong type id
        mvc.perform(get("/api/user/ggg").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.fullName").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.city").doesNotExist())
                .andExpect(jsonPath("$.linkImage").doesNotExist())
                .andExpect(jsonPath("$.reputation").doesNotExist())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /*
     * Тест пагинации userDto по reputation
     * */
    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_ENTITY, ROLE_REP_ENTITY, REPUTATION_ENTITY, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getReputation() throws Exception {

        // стандартный запрос
        mvc.perform(get("/api/user/reputation?currPage=2&items=3").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.currentPageNumber", is(2)))
                .andExpect(jsonPath("$.totalPageCount", is(2)))
                .andExpect(jsonPath("$.itemsOnPage", is(3)))
                .andExpect(jsonPath("$.totalResultCount", is(4)))
                .andExpect(jsonPath("$.items").isNotEmpty());

        // запрос на большее кол-во данных чем есть
        mvc.perform(get("/api/user/reputation?currPage=2&items=300").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.currentPageNumber", is(2)))
                .andExpect(jsonPath("$.totalPageCount", is(1)))
                .andExpect(jsonPath("$.itemsOnPage", is(300)))
                .andExpect(jsonPath("$.totalResultCount", is(4)))
                .andExpect(jsonPath("$.items").isEmpty());

        // нет обязательного параметра - текущей страницы
        mvc.perform(get("/api/user/reputation?items=4").with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

        // текущая страница велика
        mvc.perform(get("/api/user/reputation?currPage=3&items=3").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.currentPageNumber", is(3)))
                .andExpect(jsonPath("$.totalPageCount", is(2)))
                .andExpect(jsonPath("$.itemsOnPage", is(3)))
                .andExpect(jsonPath("$.totalResultCount", is(4)))
                .andExpect(jsonPath("$.items").isEmpty());

        // нет необязательного параметра - кол-во элементов на странице, по умолчанию 10
        mvc.perform(get("/api/user/reputation?currPage=1").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.currentPageNumber", is(1)))
                .andExpect(jsonPath("$.totalPageCount", is(1)))
                .andExpect(jsonPath("$.itemsOnPage", is(10)))
                .andExpect(jsonPath("$.totalResultCount", is(4)))
                .andExpect(jsonPath("$.items[*].reputation").value(containsInRelativeOrder(41, 22, 11, 10)));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDate_expectCorrectData() throws Exception {

        ResultActions response = mvc.perform(get("/api/user/new?currPage=2&items=5").with(csrf()));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", is(2)))
                .andExpect(jsonPath("$.totalPageCount", is(3)))
                .andExpect(jsonPath("$.itemsOnPage", is(5)))
                .andExpect(jsonPath("$.totalResultCount", is(14)))
                .andExpect(jsonPath("$.items").value(hasSize(5)))
                .andExpect(jsonPath("$.items[*].id").value(containsInRelativeOrder(104, 105, 106, 108, 109)));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateWithoutRequiredParam_expectBadRequest() throws Exception {


        // нет обязательного параметра - текущей страницы
        ResultActions response = mvc.perform(get("/api/user/new?items=4").with(csrf()));
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateWithoutNonRequiredParam_expectTenElementsOnPage() throws Exception {
        // нет необязательного параметра - кол-во элементов на странице по умолчанию 10
        ResultActions response = mvc.perform(get("/api/user/new?currPage=1").with(csrf()));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", is(1)))
                .andExpect(jsonPath("$.totalPageCount", is(2)))
                .andExpect(jsonPath("$.itemsOnPage", is(10)))
                .andExpect(jsonPath("$.totalResultCount", is(14)))
                .andExpect(jsonPath("$.items").value(hasSize(10)));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateWithPageNumberBiggerThanHaveItems_expectEmptyItemsArray() throws Exception {

        // страница больше, чем элементов
        mvc.perform(get("/api/user/new?currPage=2&items=100").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateZeroRequiredParam_expectBadRequest() throws Exception {
        // параметр страницы равен 0
        ResultActions response = mvc.perform(get("/api/user/new?currPage=0&items=100").with(csrf()));
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateZeroNonRequiredParam_expectBadRequest() throws Exception {
        // параметр элементов на странице равен 0
        ResultActions response = mvc.perform(get("/api/user/new?currPage=10&items=0").with(csrf()));
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_BY_PERSIST_DATE, ROLE_ENTITY, REPUTATION_BY_PERSIST_DATE, QUESTION_ENTITY, ANSWER_ENTITY}, disableConstraints = true)
    public void getUserDtoByPersistDateAllItemsPerOnePage_expectAllItemsFromDB() throws Exception {
        // хотим получить больше чем есть
        ResultActions response = mvc.perform(get("/api/user/new?currPage=1&items=100").with(csrf()));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", is(1)))
                .andExpect(jsonPath("$.totalPageCount", is(1)))
                .andExpect(jsonPath("$.itemsOnPage", is(100)))
                .andExpect(jsonPath("$.totalResultCount", is(14)))
                .andExpect(jsonPath("$.items").value(hasSize(14)));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DataSet(value = {USER_ENTITY, ROLE_ENTITY}, disableConstraints = true)
    public void changePassword() throws Exception {
        final String url = "/api/user/changePassword";
        final String CORRECT_PASS = "12345q!@#$%";
        final String TOO_SHORT_PASS = "12345";
        final String TOO_LONG_PASS = "12345qwertyui";
        final String BLANC_PASS = "      ";
        final String WRONG_CHARSET_PASS = "12345вася";

        ResultActions response = mvc.perform(put(url).with(csrf())
                .content(CORRECT_PASS));
        response.andExpect(status().isOk());

        response = mvc.perform(put(url).content(TOO_SHORT_PASS).with(csrf()));
        response.andExpect(status().isBadRequest()).andExpect(content()
                .string("Length of password from 6 to 12 symbols"));

        response = mvc.perform(put(url).content(TOO_LONG_PASS).with(csrf()));
        response.andExpect(status().isBadRequest()).andExpect(content()
                .string("Length of password from 6 to 12 symbols"));

        response = mvc.perform(put(url).content(BLANC_PASS).with(csrf()));
        response.andExpect(status().isBadRequest()).andExpect(content()
                .string("changePassword.password: Password cannot be empty"));

        response = mvc.perform(put(url).content(WRONG_CHARSET_PASS).with(csrf()));
        response.andExpect(status().isBadRequest()).andExpect(content()
                .string("Use only latin alphabet, numbers and special chars"));
    }

}