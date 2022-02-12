package com.luxcampus.Blog.repository;

import com.luxcampus.Blog.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientname(String clientname);
}
