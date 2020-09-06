package com.ryan.configuration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
//    private String pwd;
    private String password;
}
