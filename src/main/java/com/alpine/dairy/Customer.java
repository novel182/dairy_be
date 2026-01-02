package com.alpine.dairy;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue
    private Long customerId;
    private String name;
    private String phoneNumber;
    private String address;

    public Customer() {}
    Customer(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getCustomerId() { return this.customerId; }
    public String getName() { return this.name; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getAddress() { return this.address; }

    public void setCustomerId( Long customerId ) { this.customerId = customerId; }
    public void setName( String name ) { this.name = name; }
    public void setPhoneNumber( String phoneNumber ) { this.phoneNumber = phoneNumber; }
    public void setAddress( String address ) { this.address=address; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Customer)) return false;
        Customer c = (Customer) o;
        return Objects.equals(this.customerId, c.customerId)
            && Objects.equals(this.name, c.name)
            && Objects.equals(this.phoneNumber, c.phoneNumber)
            && Objects.equals(this.address, c.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.customerId, this.name, this.phoneNumber, this.address);
    }

    @Override
    public String toString() {
        return "Customer{"
        + "customerId=" + this.customerId
        + ", name=" + this.name
        + ", phoneNumber=" + this.phoneNumber
        + ", address=" + this.address
        + "}";
    }
}
