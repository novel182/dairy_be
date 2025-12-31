package com.alpine.dairy.orderRequestService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpine.dairy.inventoryManagementService.InventoryManager;

@RestController
public class OrderRequestController {
    private final InventoryManager inventoryManager;
    private final OrderRequestRepository orderRequestRepository;

    OrderRequestController(InventoryManager inventoryManager, OrderRequestRepository orderRequestRepository) {
        this.inventoryManager = inventoryManager;
        this.orderRequestRepository = orderRequestRepository;
    }

    @GetMapping("/orderRequest")
    public List<OrderRequest> getMethodName() {
        return orderRequestRepository.findAll();
    }

    @GetMapping("/orderRequest/{id}")
    public OrderRequest getMethodName(@PathVariable Long id) {
        return orderRequestRepository.findById(id).orElse(null);
    }

    @PostMapping("/orderRequest")
    public Long postMethodName(@RequestBody OrderRequest newRequest) {
        boolean canFulfill = inventoryManager.canFulfill(newRequest);
        if(canFulfill){
            newRequest.setStatus("pending");
        } else {
            newRequest.setStatus("unable");
        }
        orderRequestRepository.save(newRequest);
        return newRequest.getRequestId();
    }
}
