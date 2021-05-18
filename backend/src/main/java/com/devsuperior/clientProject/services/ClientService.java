package com.devsuperior.clientProject.services;

import com.devsuperior.clientProject.dto.ClientDTO;
import com.devsuperior.clientProject.entities.Client;
import com.devsuperior.clientProject.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<ClientDTO> findAll() {
        List<Client> clients = repository.findAll();

        return clients.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
}
