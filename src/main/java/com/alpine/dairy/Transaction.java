package com.alpine.dairy;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
// import jakarta.validation.constraints.Size;
// import jakarta.persistence.Column;

@Entity
public class Transaction {
    
    @Id
    @GeneratedValue
    private Long id;
    private String name;  // change the name to customer id when we start storing that
    private String phoneNumber;
    private String address;
    private Long customerId;
    private Long orderRequestId;

//    @Size(min=4, max=4)
//    @Column(length=4)
//     private String cardEndDigits;

    public Transaction() {}
    public Transaction(String name, String phoneNumber, String address, Long customerId, Long orderRequestId, String cardEndDigits) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.customerId = customerId;
        this.orderRequestId = orderRequestId;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getAddress() { return this.address; }
    public Long getCustomerId() { return this.customerId; }
    public Long getOrderRequestId() { return this.orderRequestId; }

    public void setId( Long id ) { this.id = id; }
    public void setName( String name ) { this.name = name; }
    public void setPhoneNumber( String phoneNumber ) { this.phoneNumber = phoneNumber; }
    public void setAddress( String address ) { this.address=address; }
    public void setCustomerId( Long customerId ) { this.customerId = customerId; }
    public void setOrderRequestId( Long orderRequestId ) { this.orderRequestId = orderRequestId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Transaction)) return false;
        Transaction t = (Transaction) o;
        return Objects.equals(this.id, t.id)
            && Objects.equals(this.name, t.name)
            && Objects.equals(this.phoneNumber, t.phoneNumber)
            && Objects.equals(this.address, t.address)
            && Objects.equals(this.customerId, t.customerId)
            && Objects.equals(this.orderRequestId, t.orderRequestId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.phoneNumber, this.address, this.customerId, this.orderRequestId);
    }

    @Override
    public String toString() {
        return "Transaction{"
        + "id=" + this.id
        + ", name=" + this.name
        + ", phone_number=" + this.phoneNumber
        + ", address=" + this.address
        + ", customerId=" + this.customerId
        + ", orderRequestId=" + this.orderRequestId
        + "}";
    }
}

