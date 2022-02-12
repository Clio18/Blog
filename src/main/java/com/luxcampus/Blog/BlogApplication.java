package com.luxcampus.Blog;

import com.luxcampus.Blog.domain.Client;
import com.luxcampus.Blog.domain.Role;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.ClientService;
import com.luxcampus.Blog.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    CommandLineRunner run(ClientService clientService, PostService postService){
        return args -> {
          clientService.saveRole(new Role(null, "ROLE_USER"));
          clientService.saveRole(new Role(null, "ROLE_ADMIN"));

          clientService.saveClient(new Client(null, "Anton", "clio", "1234", new ArrayList<>()));
          clientService.saveClient(new Client(null, "Ivan", "ivan", "1234", new ArrayList<>()));

          clientService.addRoleToClient("clio", "ROLE_ADMIN");
          clientService.addRoleToClient("ivan", "ROLE_USER");

          postService.save(new Post(null, "sport", "football", false, new ArrayList<>(), new HashSet<>()));

        };
    }
}
