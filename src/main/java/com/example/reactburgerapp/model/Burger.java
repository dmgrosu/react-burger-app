package com.example.reactburgerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Burger {

    @Id
    private String id;
    private Set<Ingredient> ingredients;

}
