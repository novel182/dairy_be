package com.alpine.dairy.inventoryManagementService;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alpine.dairy.orderRequestService.OrderRequest;

@RestController
public class InventoryManagementController {
    private final InventoryManager inventoryManager;

    InventoryManagementController(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @PostMapping("inventory/fulfill")
    public List<InventoryItem> fulfillDeliveredRequest(@RequestBody OrderRequest deliveredRequest) {
        boolean isDeliveryAccounted = inventoryManager.fulfillDeliveredRequest(deliveredRequest);
        if(!isDeliveryAccounted){
            return null;
        }
        return inventoryManager.getAllItems();
    }
    
}
