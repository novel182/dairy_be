package com.alpine.dairy;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }
    
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        Customer updatedCustomer = repository.findById(id)
            .map(customer -> {
                customer.setName(newCustomer.getName());
                customer.setPhoneNumber(newCustomer.getPhoneNumber());
                return repository.save(customer);
            })
            .orElseGet(() -> {
                return repository.save(newCustomer);
            });
        updatedCustomer.setCustomerId(id);  //Hibernate does not allow 'GeneratedValue' field to be set manually during save
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}
