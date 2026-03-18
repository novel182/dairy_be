package com.alpine.dairy.orderRequestService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue
    private Long requestId;
    
    @ElementCollection
    @CollectionTable(name = "order_request_items", joinColumns = @JoinColumn(name = "order_request_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<String, Integer> items = new HashMap<>();

    private String requestStatus;
    private Long customerId;

    public OrderRequest() {}
    
    public OrderRequest(Map<String, Integer> items, String requestStatus, Long customerId) {
        this.items = items;
        this.requestStatus = requestStatus;
        this.customerId = customerId;
    }

    public Long getRequestId() { return requestId; }
    public String getRequestStatus() { return requestStatus; }
    public Long getCustomerId() { return customerId; }
    public Map<String, Integer> getItems() { return items; }
    
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public void setRequestStatus(String requestStatus) { this.requestStatus = requestStatus; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setItems(Map<String, Integer> items) { this.items = items; }

    // Helper methods for backward compatibility or easier access during transition
    public int getMozzarella() { return items.getOrDefault("mozzarella", 0); }
    public int getPaneer() { return items.getOrDefault("paneer", 0); }
    public int getKanchan() { return items.getOrDefault("kanchan", 0); }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof OrderRequest)) return false;
        OrderRequest or = (OrderRequest) o;
        return Objects.equals(this.requestId, or.requestId)
            && Objects.equals(this.items, or.items)
            && Objects.equals(this.requestStatus, or.requestStatus)
            && Objects.equals(this.customerId, or.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.requestId, this.items, this.requestStatus, this.customerId);
    }

    @Override
    public String toString() {
        return "OrderRequest{"
        + "id=" + this.requestId
        + ", items=" + this.items.toString()
        + ", requestStatus=" + this.requestStatus
        + ", customerId=" + this.customerId
        + "}";
    }
}
