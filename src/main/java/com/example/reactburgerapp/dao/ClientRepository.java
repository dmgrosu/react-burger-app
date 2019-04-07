package com.example.reactburgerapp.dao;

import com.example.reactburgerapp.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClientRepository extends MongoRepository<Client, String> {

    Client findClientByFullName(String fullName);

    List<Client> findAllByLastName(String lastName);

}
