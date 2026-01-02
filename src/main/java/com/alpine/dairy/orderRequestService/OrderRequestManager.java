package com.alpine.dairy.orderRequestService;

import java.util.List;
import org.springframework.stereotype.Service;

import com.alpine.dairy.inventoryManagementService.InventoryManager;

@Service
public class OrderRequestManager {
    private final OrderRequestRepository orderRequestRepository;
    private final InventoryManager inventoryManager;

    OrderRequestManager(OrderRequestRepository orderRequestRepository, InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.orderRequestRepository = orderRequestRepository;
    }

    public OrderRequest getOrderRequestById(Long id){
        return orderRequestRepository.findById(id).orElse(null);
    }

    public List<OrderRequest> getAllOrderRequests(){
        return orderRequestRepository.findAll();
    }

    public Long addNewOrderRequest(OrderRequest newRequest){
        boolean canFulfill = inventoryManager.canFulfill(newRequest);
        if(canFulfill){
            newRequest.setStatus("pending");
        } else {
            newRequest.setStatus("unable");
        }
        OrderRequest lastOrder = orderRequestRepository.save(newRequest);
        return lastOrder.getRequestId();
    }

    public OrderRequest fulfilHoldRequest(OrderRequest orderRequest){
        boolean canFulfill = inventoryManager.canFulfill(orderRequest);
        OrderRequest existingRequest = orderRequestRepository.findById(orderRequest.getRequestId()).orElse(null);

        if(canFulfill && existingRequest != null){
            orderRequest.setStatus("fulfilled");
        } else {
            orderRequest.setStatus("unable");
            return orderRequest;
        }

        boolean fulfilSuccess = inventoryManager.fulfilHoldRequest(orderRequest);
        if(fulfilSuccess == false){
            orderRequest.setStatus("unable");
        }
        return orderRequestRepository.save(orderRequest);

    }
}
