package com.alpine.dairy.orderSubmissionService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alpine.dairy.orderRequestService.OrderRequest;
import com.alpine.dairy.CustomerRepository;
import com.alpine.dairy.Customer;
import com.alpine.dairy.orderRequestService.OrderRequestManager;



@RestController
public class OrderSubmissionController {
    private final CustomerRepository customerRepository;
    private final OrderRequestManager orderRequestManager;

    OrderSubmissionController(CustomerRepository customerRepository, OrderRequestManager orderRequestManager) {
        this.customerRepository = customerRepository;
        this.orderRequestManager = orderRequestManager;
    }
    
    @PostMapping("/submitOrder")
    public Order placeOrder(@RequestBody Order orderDetails) {
        OrderRequest orderRequest = orderDetails.getOrderRequest();
        Customer customer = orderDetails.getCustomer();

        Customer newCustomer = customerRepository.save(customer);
        OrderRequest newRequest = orderRequestManager.fulfillHoldRequest(orderRequest);
        Order newOrder = new Order(newRequest, newCustomer);
        return newOrder;
    }
    
    //only for testing
    //DELETE LATER
    @GetMapping("/submitOrder/{id}")
    public OrderRequest orderWithRequestId(@PathVariable Long id) {
        OrderRequest newRequest = orderRequestManager.getOrderRequestById(id);
        OrderRequest orderRequest = orderRequestManager.fulfillHoldRequest(newRequest);
        return orderRequest;
    }
    
}
