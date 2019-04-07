package com.example.reactburgerapp.dao;

import com.example.reactburgerapp.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByClientFullName(String fullName);

    List<Order> findAllByClientAddressStreet(String street);

    List<Order> findAllByClientUserId(@Param("userId") String userId);

}
