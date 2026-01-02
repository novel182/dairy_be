package com.alpine.dairy.orderRequestService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpine.dairy.inventoryManagementService.InventoryItem;

@RestController
public class OrderRequestController {
    private final OrderRequestManager orderRequestManager;

    OrderRequestController(OrderRequestManager orderRequestManager) {
        this.orderRequestManager = orderRequestManager;
    }

    @GetMapping("/orderRequests")
    public List<OrderRequest> getMethodName() {
        return orderRequestManager.getAllOrderRequests();
    }

    @GetMapping("/orderRequests/{id}")
    public OrderRequest getMethodName(@PathVariable Long id) {
        return orderRequestManager.getOrderRequestById(id);
    }

    @PostMapping("/orderRequests")
    public Long postMethodName(@RequestBody OrderRequest newRequest) {
        return orderRequestManager.addNewOrderRequest(newRequest);
    }

    @GetMapping("/orderRequests/fulfill/{id}")
    public List<InventoryItem> fulfillDeliveredRequest(@PathVariable Long id) {
        return orderRequestManager.fulfillDeliveredRequest(id);
    }
}
