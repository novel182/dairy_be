// package com.alpine.dairy;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class LoadCustomerDatabase {

//     @Bean
//     CommandLineRunner initCustomerDatabase(CustomerRepository customerRepository) {
  
//       return (args) -> {
//         System.out.println("Preloading " + customerRepository.save(new Customer("John Doe", "1234567890", "123 Main St")));
//         System.out.println("Preloading " + customerRepository.save(new Customer("Jane Smith", "9876543210", "456 Oak Ave")));
//       };
//     }

// }