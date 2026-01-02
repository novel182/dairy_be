package com.alpine.dairy.orderSubmissionService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alpine.dairy.orderRequestService.OrderRequest;
import com.alpine.dairy.Customer;


@RestController
public class OrderSubmissionController {
    
    
    @PostMapping("/submitOrder")
    public Order placeOrder(@RequestBody Order orderDetails) {
        OrderRequest orderRequest = orderDetails.getOrderRequest();
        Customer customer = orderDetails.getCustomer();

    }
    
}
