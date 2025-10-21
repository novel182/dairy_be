package com.alpine.dairy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phoneNumber;

    Customer() {}
    Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getPhoneNumber() { return this.phoneNumber; }

    public void setId( Long id ) { this.id = id; }
    public void setName( String name ) { this.name = name; }
    public void setPhoneNumber( String phoneNumber ) { this.phoneNumber = phoneNumber; }

    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Customer)) return false;
        
    }

}
