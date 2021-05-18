package com.devsuperior.clientProject.services;

import com.devsuperior.clientProject.dto.ClientDTO;
import com.devsuperior.clientProject.entities.Client;
import com.devsuperior.clientProject.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        List<Client> clients = repository.findAll();

        return clients.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> client = repository.findById(id);
        Client entity = client.get();

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = new Client();
        client = copyDtoToEntity(dto, client);
        client = repository.save(client);

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = repository.getOne(id);
        client = copyDtoToEntity(dto, client);

        return new ClientDTO(client);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private Client copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

        return entity;
    }
}
