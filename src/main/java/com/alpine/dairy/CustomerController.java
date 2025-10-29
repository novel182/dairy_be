package com.alpine.dairy;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class CustomerController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping("customers")
    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }
    
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        return repository.findById(id)
            .map(customer -> {
                customer.setName(newCustomer.getName());
                customer.setPhoneNumber(newCustomer.getPhoneNumber());
                return repository.save(customer);
            })
            .orElseGet(() -> {
                newCustomer.setId(id);
                return repository.save(newCustomer);
            });
    }
    
}
