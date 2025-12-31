package com.alpine.dairy.orderRequestService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue
    private Long requestId;
    private int mozzarella;
    private int paneer;
    private int kanchan;
    private String status;
    private Long inventoryTransactionId;

    public OrderRequest() {}
    public OrderRequest(int mozzarella, int paneer, int kanchan, String status, Long inventoryTransactionId) {
        this.mozzarella = mozzarella;
        this.paneer = paneer;
        this.kanchan = kanchan;
        this.status = status;
        this.inventoryTransactionId = inventoryTransactionId;
    }

    public Long getRequestId() { return requestId; }
    public int getMozzarella() { return mozzarella; }
    public int getPaneer() { return paneer; }
    public int getKanchan() { return kanchan; }
    public String getStatus() { return status; }
    public Long getInventoryTransactionId() { return inventoryTransactionId; }
    
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public void setMozzarella(int mozzarella) { this.mozzarella = mozzarella; }
    public void setPaneer(int paneer) { this.paneer = paneer; }
    public void setKanchan(int kanchan) { this.kanchan = kanchan; }
    public void setStatus(String status) { this.status = status; }
    public void setInventoryTransactionId(Long inventoryTransactionId) { this.inventoryTransactionId = inventoryTransactionId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof OrderRequest)) return false;
        OrderRequest or = (OrderRequest) o;
        return this.requestId.equals(or.requestId)
            && this.mozzarella == or.mozzarella
            && this.paneer == or.paneer
            && this.kanchan == or.kanchan
            && this.status.equals(or.status)
            && this.inventoryTransactionId.equals(or.inventoryTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.requestId, this.mozzarella, this.paneer, this.kanchan, this.status, this.inventoryTransactionId);
    }

    @Override
    public String toString() {
        return "OrderRequest{"
        + "id=" + this.requestId
        + ", mozzarella=" + this.mozzarella
        + ", paneer=" + this.paneer
        + ", kanchan=" + this.kanchan
        + ", status=" + this.status
        + ", inventoryTransactionId=" + this.inventoryTransactionId
        + "}";
    }
}
