package com.alpine.dairy.inventoryManagementService;

import org.springframework.stereotype.Service;

import com.alpine.dairy.orderRequestService.OrderRequest;

@Service
public class InventoryManager {
    private final InventoryRepository inventoryRepository;

    InventoryManager(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean canFulfill(OrderRequest orderRequest){
        boolean canFulfill = hasSufficientItem("mozzarella", orderRequest.getMozzarella())
            && hasSufficientItem("paneer", orderRequest.getPaneer())
            && hasSufficientItem("kanchan", orderRequest.getKanchan());
        return canFulfill;
    }

    private boolean hasSufficientItem(String productId, int amount){
        InventoryItem requestedItem = inventoryRepository.findById(productId).orElse(null);
        if(requestedItem == null){
            return false;
        }
        int availableQuantity = requestedItem.getTotalQuantity() - requestedItem.getQuantityHold();
        if(availableQuantity < amount){
            return false;
        }
        return true;
    }
}
