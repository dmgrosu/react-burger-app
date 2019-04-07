package com.example.reactburgerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private LocalDateTime dateTime = LocalDateTime.now();
    private Client client;
    private Burger burger;
    private Double price;
    private String deliveryMethod;

}
