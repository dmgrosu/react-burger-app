package com.example.reactburgerapp.dao;

import com.example.reactburgerapp.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
public interface IngredientRepository extends MongoRepository<Ingredient, String> {

}
