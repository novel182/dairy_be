// package com.alpine.dairy.orderRequestService;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class LoadRequestDatabase {
    
//     @Bean
//     CommandLineRunner initRequestDatabase(OrderRequestRepository repository) {
      
//       return (args) -> {
//         System.out.println("Preloading " + repository.save(new OrderRequest(2, 5, 1, "pending", Long.valueOf(45))));
//         System.out.println("Preloading " + repository.save(new OrderRequest(3, 10, 2, "pending", Long.valueOf(90))));
//       };
//     }
// }
