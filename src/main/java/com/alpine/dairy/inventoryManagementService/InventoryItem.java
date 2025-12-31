package com.alpine.dairy.inventoryManagementService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class InventoryItem {
    @Id
    private String productId;
    private String productName;
    private int totalQuantity;
    private int quantityHold;

    public InventoryItem() {}
    public InventoryItem(String productId, String productName, int totalQuantity, int quantityHold) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.quantityHold = quantityHold;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getTotalQuantity() { return totalQuantity; }
    public int getQuantityHold() { return quantityHold; }

    public void setProductId(String productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setTotalQuantity(int quantityAvailable) { this.totalQuantity = quantityAvailable; }
    public void setQuantityHold(int quantityHold) { this.quantityHold = quantityHold; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof InventoryItem)) return false;
        InventoryItem ii = (InventoryItem) o;
        return this.productId.equals(ii.productId)
            && this.productName.equals(ii.productName)
            && this.totalQuantity == ii.totalQuantity
            && this.quantityHold == ii.quantityHold;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.productId, this.productName, this.totalQuantity, this.quantityHold);
    }

    @Override
    public String toString() {
        return "InventoryItem{"
            + "productId='" + this.productId
            + ", productName='" + this.productName
            + ", totalQuantity=" + this.totalQuantity
            + ", quantityHold=" + this.quantityHold
            + '}';
    }
}
