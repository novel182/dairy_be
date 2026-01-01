package com.alpine.dairy.orderSubmissionService;

import com.alpine.dairy.orderRequestService.OrderRequest;
import com.alpine.dairy.Customer;

public class Order {
    private OrderRequest orderRequest;
    private Customer customer;

    public Order(OrderRequest orderRequest, Customer customer) {
        this.orderRequest = orderRequest;
        this.customer = customer;
    }

    public OrderRequest getOrderRequest() { return orderRequest; }
    public Customer getCustomer() { return customer; }

    public void setOrderRequest(OrderRequest orderRequest) { this.orderRequest = orderRequest; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "Order{"
        + "orderRequest=" + this.orderRequest.toString()
        + ", customer=" + this.customer.toString()
        + "}";
    }
}
