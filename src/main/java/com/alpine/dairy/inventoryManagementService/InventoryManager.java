package com.alpine.dairy.inventoryManagementService;

import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpine.dairy.orderRequestService.OrderRequest;

@Service
public class InventoryManager {
    private final InventoryRepository inventoryRepository;

    InventoryManager(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean canFulfill(OrderRequest orderRequest){
        for (Map.Entry<String, Integer> entry : orderRequest.getItems().entrySet()) {
            if (!hasSufficientItem(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSufficientItem(String productId, int amount){
        InventoryItem requestedItem = inventoryRepository.findById(productId).orElse(null);
        if(requestedItem == null){
            return false;
        }
        int availableQuantity = requestedItem.getTotalQuantity() - requestedItem.getQuantityTemporaryHold() - requestedItem.getQuantityPromised();
        return availableQuantity >= amount;
    }

    /* Hold Requests */
    @Transactional
    public boolean addHoldQuantity(Map<String, Integer> items){
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            updateHoldQuantity(entry.getKey(), entry.getValue());
        }
        return true;
    }

    private void updateHoldQuantity(String productId, int quantity) {
        if (quantity == 0) return;
        inventoryRepository.findById(productId).ifPresent(item -> {
            item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() + quantity);
            inventoryRepository.save(item);
        });
    }

    @Transactional
    public boolean holdRequest(OrderRequest orderRequest){
        return addHoldQuantity(orderRequest.getItems());
    }

    @Transactional
    public boolean changeHoldRequest(OrderRequest oldRequest, OrderRequest newRequest){
        Set<String> allProductIds = new HashSet<>();
        allProductIds.addAll(oldRequest.getItems().keySet());
        allProductIds.addAll(newRequest.getItems().keySet());

        boolean changed = false;
        for (String productId : allProductIds) {
            int oldQty = oldRequest.getItems().getOrDefault(productId, 0);
            int newQty = newRequest.getItems().getOrDefault(productId, 0);
            int diff = newQty - oldQty;
            if (diff != 0) {
                updateHoldQuantity(productId, diff);
                changed = true;
            }
        }
        return changed;
    }


    /* Fulfillment of Hold Requests */
    @Transactional
    public boolean fulfillHoldRequest(OrderRequest orderRequest){
        Map<String, Integer> orderItems = orderRequest.getItems();
        
        // Validation phase
        for (String productId : orderItems.keySet()) {
            InventoryItem item = getItemById(productId);
            if (item == null || item.getQuantityTemporaryHold() < orderItems.get(productId)) {
                return false;
            }
        }

        // Execution phase
        for (String productId : orderItems.keySet()) {
            InventoryItem item = getItemById(productId);
            updateFulfillmentQuantities(item, orderItems.get(productId));
        }

        return true;
    }

    private void updateFulfillmentQuantities(InventoryItem item, int quantity) {
        if (quantity == 0) return;
        item.setQuantityPromised(item.getQuantityPromised() + quantity);
        item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() - quantity);
        inventoryRepository.save(item);
    }


    /* Fulfillment of Promised Requests */
    @Transactional
    public boolean fulfillDeliveredRequest(OrderRequest orderRequest){
        Map<String, Integer> orderItems = orderRequest.getItems();

        // Validation phase
        for (String productId : orderItems.keySet()) {
            InventoryItem item = getItemById(productId);
            if (item == null || item.getQuantityPromised() < orderItems.get(productId)) {
                return false;
            }
        }

        // Execution phase
        for (String productId : orderItems.keySet()) {
            InventoryItem item = getItemById(productId);
            updateDeliveryQuantities(item, orderItems.get(productId));
        }

        return true;
    }

    private void updateDeliveryQuantities(InventoryItem item, int quantity) {
        if (quantity == 0) return;
        item.setTotalQuantity(item.getTotalQuantity() - quantity);
        item.setQuantityPromised(item.getQuantityPromised() - quantity);
        inventoryRepository.save(item);
    }


    /* Basic operations */
    public List<InventoryItem> getAllItems(){
        return inventoryRepository.findAll();
    }

    public InventoryItem getItemById(String productId){
        return inventoryRepository.findById(productId).orElse(null);
    }

    public InventoryItem addProducedItem(String productId, int quantityProduced){
        InventoryItem existingItem = getItemById(productId);
        if (existingItem == null) {
            return null;
        }
        existingItem.setTotalQuantity(existingItem.getTotalQuantity() + quantityProduced);
        return inventoryRepository.save(existingItem);
    }
}
