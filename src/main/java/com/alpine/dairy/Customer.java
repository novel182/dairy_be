package com.alpine.dairy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Customer {
    
    private @Id
    @GeneratedValue Long id;
    private String name;
    
}
