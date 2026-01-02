package com.alpine.dairy.orderRequestService;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.alpine.dairy.inventoryManagementService.InventoryItem;
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
        boolean isRequestOnHold = inventoryManager.holdRequest(newRequest);
        if(isRequestOnHold){
            newRequest.setStatus("pending");
        } else {
            newRequest.setStatus("unable");
        }
        OrderRequest lastOrder = orderRequestRepository.save(newRequest);
        return lastOrder.getRequestId();
    }

    public OrderRequest fulfillHoldRequest(OrderRequest orderRequest){
        boolean canFulfill = inventoryManager.canFulfill(orderRequest);
        if(!canFulfill){
            orderRequest.setStatus("unable");
            return orderRequestRepository.save(orderRequest);
        }
        
        orderRequestRepository.findById(orderRequest.getRequestId())
            .ifPresent(oldRequest -> inventoryManager.changeHoldRequest(oldRequest, orderRequest));

        boolean fulfilSuccess = inventoryManager.fulfillHoldRequest(orderRequest);
        if(!fulfilSuccess){
            orderRequest.setStatus("unable");
        }
        else{
            orderRequest.setStatus("fulfilled");
        }
        return orderRequestRepository.save(orderRequest);
    }

    public List<InventoryItem> fulfillDeliveredRequest(@PathVariable Long id) {
        OrderRequest deliveredRequest = orderRequestRepository.findById(id).orElse(null);
        if(deliveredRequest == null || deliveredRequest.getStatus().equals("unable")){
            return List.of();
        }
        boolean isDeliveryAccounted = inventoryManager.fulfillDeliveredRequest(deliveredRequest);
        if(!isDeliveryAccounted){
            return List.of();
        }
        return inventoryManager.getAllItems();
    }
}
