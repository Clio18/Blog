package com.luxcampus.Blog.service;

import com.luxcampus.Blog.domain.Client;
import com.luxcampus.Blog.domain.Role;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);
    Role saveRole(Role role);
    void addRoleToClient(String clientname, String roleName);
    Client getClient(String clientname);
    List<Client> getClients();


}
