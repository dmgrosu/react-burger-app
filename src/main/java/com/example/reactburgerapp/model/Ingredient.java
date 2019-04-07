package com.example.reactburgerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    private String id;
    private String name;
    private Integer qty;

}
