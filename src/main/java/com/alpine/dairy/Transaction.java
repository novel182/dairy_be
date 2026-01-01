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
    private Long customerId;
    private Long orderRequestId;

//    @Size(min=4, max=4)
//    @Column(length=4)
//     private String cardEndDigits;

    public Transaction() {}
    public Transaction(Long customerId, Long orderRequestId, String cardEndDigits) {
        this.customerId = customerId;
        this.orderRequestId = orderRequestId;
    }

    public Long getId() { return this.id; }
    public Long getCustomerId() { return this.customerId; }
    public Long getOrderRequestId() { return this.orderRequestId; }

    public void setId( Long id ) { this.id = id; }
    public void setCustomerId( Long customerId ) { this.customerId = customerId; }
    public void setOrderRequestId( Long orderRequestId ) { this.orderRequestId = orderRequestId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Transaction)) return false;
        Transaction t = (Transaction) o;
        return Objects.equals(this.id, t.id)
            && Objects.equals(this.customerId, t.customerId)
            && Objects.equals(this.orderRequestId, t.orderRequestId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.customerId, this.orderRequestId);
    }

    @Override
    public String toString() {
        return "Transaction{"
        + "id=" + this.id
        + ", customerId=" + this.customerId
        + ", orderRequestId=" + this.orderRequestId
        + "}";
    }
}

