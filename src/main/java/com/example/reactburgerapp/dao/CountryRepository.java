package com.example.reactburgerapp.dao;

import com.example.reactburgerapp.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepository extends MongoRepository<Country, String> {

}
