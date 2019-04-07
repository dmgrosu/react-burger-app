package com.example.reactburgerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestApiAnswer {

    private String userEmail;
    private String userId;
    private String token;
    private String error;

}
