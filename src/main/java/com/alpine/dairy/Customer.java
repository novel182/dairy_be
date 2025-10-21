package com.alpine.dairy;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Customer)) return false;
        Customer c = (Customer) o;
        return Objects.equals(this.id, c.id)
            && Objects.equals(this.name, c.name)
            && Objects.equals(this.phoneNumber, c.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer{"
        + "id=" + this.id
        + ", name=" + this.name
        + ", phone_number=" + this.phoneNumber
        + "}";
    }
}
