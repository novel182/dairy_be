package com.alpine.dairy.orderRequestService;

import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
public class OrderRequest {
    
    @GeneratedValue
    private Long requestId;
    private Map<String, Integer> productMap; // product id to quantity
    private String status;
    private Long inventoryTransactionId;

    public OrderRequest() {}
    public OrderRequest(Long requestId, Map<String, Integer> productMap, String status, Long inventoryTransactionId) {
        this.requestId = requestId;
        this.productMap = productMap;
        this.status = status;
        this.inventoryTransactionId = inventoryTransactionId;
    }

    public Long getRequestId() { return requestId; }
    public Map<String, Integer> getProductMap() { return productMap; }
    public String getStatus() { return status; }
    public Long getInventoryTransactionId() { return inventoryTransactionId; }
    
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public void setProductMap(Map<String, Integer> productMap) { this.productMap = productMap; }
    public void setStatus(String status) { this.status = status; }
    public void setInventoryTransactionId(Long inventoryTransactionId) { this.inventoryTransactionId = inventoryTransactionId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof OrderRequest)) return false;
        OrderRequest or = (OrderRequest) o;
        return this.requestId.equals(or.requestId)
            && this.productMap.equals(or.productMap)
            && this.status.equals(or.status)
            && this.inventoryTransactionId.equals(or.inventoryTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.requestId, this.productMap, this.status, this.inventoryTransactionId);
    }

    @Override
    public String toString() {
        return "OrderRequest{"
        + "id=" + this.requestId
        + ", products=" + this.productMap.toString()
        + ", status=" + this.status
        + ", inventoryTransactionId=" + this.inventoryTransactionId
        + "}";
    }
}
