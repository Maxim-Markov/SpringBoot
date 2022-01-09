package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.dto.AuthenticationRequestDto;
import com.maxmarkovdev.springboot.service.interfaces.RoleService;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TestDataInitService {

    private final List<AuthenticationRequestDto> permanentUserParameters = new ArrayList<>();

    //Amount of test data
    private final static int usersNum = 60;//first 50 with permanent email and password and has role USER, others with random parameters
    private final static int rolesNum = 7;



    //static fields for random values
    private static final Character[] alphabet = "abcdefghijklmnopqrstuvwxyz"
            .chars()
            .mapToObj(c -> (char) c).toArray(Character[]::new);

    private static final String[] firstNames = new String[]{
            "Harry", "Ross", "Bruce", "Cook", "Carolyn", "Morgan", "Albert",
            "Walker", "Randy", "Reed", "Larry", "Barnes", "Lois", "Wilson",
            "Jesse", "Campbell", "Ernest", "Rogers", "Theresa", "Patterson",
            "Henry", "Simmons", "Michelle", "Perry", "Frank", "Butler", "Shirley"};

    private static final String[] middleNames = new String[]{
            "Brooks", "Rachel", "Edwards", "Christopher", "Perez", "Thomas",
            "Baker", "Sara", "Moore", "Chris", "Bailey", "Roger", "Johnson",
            "Marilyn", "Thompson", "Anthony", "Evans", "Julie", "Hall",
            "Paula", "Phillips", "Annie", "Hernandez", "Dorothy", "Murphy",
            "Alice", "Howard"};

    private static final String[] lastNames = new String[]{
            "Ruth", "Jackson", "Debra", "Allen", "Gerald", "Harris", "Raymond",
            "Carter", "Jacqueline", "Torres", "Joseph", "Nelson", "Carlos",
            "Sanchez", "Ralph", "Clark", "Jean", "Alexander", "Stephen", "Roberts",
            "Eric", "Long", "Amanda", "Scott", "Teresa", "Diaz", "Wanda", "Thomas"};

    private static final String[] domains = new String[]{
            "mail", "email", "gmail", "vk", "msn", "yandex", "yahoo", "edu.spbstu", "swebhosting"};

    private static final String[] domainCodes = new String[]{
            "ru", "com", "biz", "info", "net", "su", "org"};

    private static final String[] cities = new String[]{
            "Saint-Petersburg", "Moscow", "Vologda", "Volgograd", "Murmansk", "Vladivostok", "Novgorod", "Tula"};

    private static final String[] abouts = new String[]{
            "student", "dentist", "engineer", "social worker", "nurse", "doctor"};

    private static final String[] roles = new String[]{
            "ROLE_ADMIN", "ROLE_USER", "ROLE_ANONYMOUS", "ROLE_GUEST", "ROLE_UNDEFINED", "ROLE_MAIN"};

    public TestDataInitService(UserService userService,
                               RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private final UserService userService;
    private final RoleService roleService;
    //fill related tables user_entity and role with test data
    public void fillTableWithTestData() {
        addRoles();
        addRandomUsersPermanentNamePassword();
    }


    private void addRandomUsersPermanentNamePassword() {
        fillPermanentUserParameters();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usersNum; i++) {
            String name = (i < permanentUserParameters.size())
                    ? permanentUserParameters.get(i).getUsername()
                    : getRand(firstNames) + getRand(middleNames) + getRand(lastNames);
        String email = getRand(firstNames).toLowerCase() + "@" +
                getRand(domains) + "." + getRand(domainCodes);
            String password = (i < permanentUserParameters.size())
                    ? permanentUserParameters.get(i).getPassword()
                    : getRandStr(4, 20);
        String city = getRand(cities);
        String linkSite = "https://" + getRandStr(10, 50);
        String linkGithub = "https://" + getRandStr(5, 30);
        String linkVk = "https://vk.com/" + getRandStr(1, 30);
        String about = getRand(abouts);
        String imageLink = getRandStr(10, 100);
        String nickname = email.substring(0, 3);
        byte age = (byte) getRandInt(1,120);

        users.add(new User(name, password, email, age, city,
                linkSite, linkGithub, linkVk, about, imageLink, nickname));
    }

    List<Role> existingRoles = roleService.getAll();
        users.get(0).setRoles(Collections.singleton(existingRoles.get(0)));
        for(int i = 1; i < usersNum; i++) {
            if ((i < permanentUserParameters.size())) {
                users.get(i).setRoles(Collections.singleton(existingRoles.get(1)));
            } else {
                users.get(i).setRoles(Collections.singleton(existingRoles.get(getRandInt(0, existingRoles.size()))));
            }
        }
        userService.persistAll(users);
}

    private void fillPermanentUserParameters() {
        permanentUserParameters.add(new AuthenticationRequestDto("user", "user"));
        permanentUserParameters.add(new AuthenticationRequestDto("test", "test"));
        permanentUserParameters.add(new AuthenticationRequestDto("commonUser", "common"));
        permanentUserParameters.add(new AuthenticationRequestDto("user@mail.ru", "somePass"));
        for (int i = 1; i <= 46; i++){
            permanentUserParameters.add(new AuthenticationRequestDto("user" + i, "user" + i));
        }
    }

    private void addRoles() {
        List<Role> testRoles = new ArrayList<>();
        int counter = 0;
        while (testRoles.size() < rolesNum - 1) {
            testRoles.add(new Role(roles[counter++]));
        }
        roleService.persistAll(testRoles);
    }


    private static int getRandInt(int lowBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowBound, upperBound);
    }

    private static <T> T getRand(T[] array) {
        return array[ThreadLocalRandom.current().nextInt(0, array.length)];
    }

    private static String getRandStr(int lowBound, int upperBound) {
        StringBuilder stringBuilder = new StringBuilder();
        int strLength = getRandInt(lowBound, upperBound);
        for (int i = 0; i < strLength; i++) {
            stringBuilder.append(getRand(alphabet));
        }
        return stringBuilder.toString();
    }
}
