package com.devsuperior.clientProject.services;

import com.devsuperior.clientProject.dto.ClientDTO;
import com.devsuperior.clientProject.entities.Client;
import com.devsuperior.clientProject.repositories.ClientRepository;
import com.devsuperior.clientProject.services.exceptions.DatabaseException;
import com.devsuperior.clientProject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

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
        try {
            Client client = repository.getOne(id);
            client = copyDtoToEntity(dto, client);

            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Entity not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
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
