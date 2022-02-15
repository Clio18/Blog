package com.luxcampus.Blog.service.impl;

import com.luxcampus.Blog.domain.Client;
import com.luxcampus.Blog.domain.Role;
import com.luxcampus.Blog.repository.ClientRepository;
import com.luxcampus.Blog.repository.RoleRepository;
import com.luxcampus.Blog.service.ClientServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements ClientServiceInterface, UserDetailsService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Client saveClient(Client client) {
        log.info("Saving new client {} to the db", client.getClientname());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToClient(String clientname, String roleName) {
        log.info("Adding role {} to the client {}", roleName, clientname);
        Client client = clientRepository.findByClientname(clientname);
        Role role = roleRepository.findByName(roleName);
        client.getRoles().add(role);

    }

    @Override
    public Client getClient(String clientname) {
        log.info("Fetching client {}", clientname);
        return clientRepository.findByClientname(clientname);
    }

    @Override
    public List<Client> getClients() {
        log.info("Fetching  all clients");
        return clientRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByClientname(username);
        if (client==null){
            log.error("Client not found in the db");
            throw new UsernameNotFoundException("Client not found in the db");
        } else {
            log.info("Client found in the db: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Collection<Role> roles = client.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(client.getClientname(), client.getPassword(), authorities);
    }
}
