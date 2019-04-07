package com.example.reactburgerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private String email;
    private String userId;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
