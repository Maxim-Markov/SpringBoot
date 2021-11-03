package com.maxmarkovdev.springboot;

import com.maxmarkovdev.springboot.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://91.241.64.178:7081/api/users" , HttpMethod.GET, entity,String.class);
        String result = response.getBody();
        String sessionId = response.getHeaders().get("set-cookie").get(0);

        headers.set("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> PostEntity = new HttpEntity<>(new User(3L,"James","Brown", (byte) 30), headers);
        String result1 = restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.POST, PostEntity, String.class).getBody();

        HttpEntity<User> PutEntity = new HttpEntity<>(new User(3L,"Thomas","Shelby", (byte) 30), headers);
        String result2 = restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.PUT, PutEntity, String.class).getBody();

        HttpEntity<User> DeleteEntity = new HttpEntity<>(headers);
        String result3 = restTemplate.exchange("http://91.241.64.178:7081/api/users/3", HttpMethod.DELETE, DeleteEntity, String.class).getBody();
        System.out.println(result1+result2+result3);
        System.out.println();
    }

}
