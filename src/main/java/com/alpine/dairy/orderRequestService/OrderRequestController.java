package com.alpine.dairy.orderRequestService;

import com.alpine.dairy.inventoryManagementService.InventoryManager;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class OrderRequestController {
    private InventoryManager inventoryManager;

    @GetMapping("/orderRequest/{id}")
    public String getMethodName(@PathVariable Long id) {
        return "orderRequestService: " + id;
    }

    @PostMapping("path")
    public Long postMethodName(@RequestBody OrderRequest newRequest) {
        //TODO: post the order request to inventory manager
        
        return newRequest.getRequestId();
    }
    
    
}
