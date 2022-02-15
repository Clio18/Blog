package com.luxcampus.Blog;

import com.luxcampus.Blog.domain.Client;
import com.luxcampus.Blog.domain.Role;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.ClientServiceInterface;
import com.luxcampus.Blog.service.impl.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(ClientServiceInterface clientServiceInterface, PostService postService){
        return args -> {
          clientServiceInterface.saveRole(new Role(null, "ROLE_USER"));
          clientServiceInterface.saveRole(new Role(null, "ROLE_ADMIN"));

          clientServiceInterface.saveClient(new Client(null, "Anton", "clio", "1234", new ArrayList<>()));
          clientServiceInterface.saveClient(new Client(null, "Ivan", "ivan", "1234", new ArrayList<>()));

          clientServiceInterface.addRoleToClient("clio", "ROLE_ADMIN");
          clientServiceInterface.addRoleToClient("ivan", "ROLE_USER");

          postService.save(new Post(null, "sport", "football", false, new ArrayList<>(), new HashSet<>()));
          postService.save(new Post(null, "sport", "apl", false, new ArrayList<>(), new HashSet<>()));

        };
    }
}
