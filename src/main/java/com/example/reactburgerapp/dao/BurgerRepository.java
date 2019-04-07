package com.example.reactburgerapp.dao;

import com.example.reactburgerapp.model.Burger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
public interface BurgerRepository extends MongoRepository<Burger, String> {

}
