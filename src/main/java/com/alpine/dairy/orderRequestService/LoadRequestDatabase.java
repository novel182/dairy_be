// package com.alpine.dairy.orderRequestService;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.Map;
// import java.util.HashMap;

// @Configuration
// public class LoadRequestDatabase {
    
//     @Bean
//     CommandLineRunner initRequestDatabase(OrderRequestRepository repository) {
      
//       return (args) -> {
//         Map<String, Integer> items1 = new HashMap<>();
//         items1.put("mozzarella", 2);
//         items1.put("paneer", 5);
//         items1.put("kanchan", 1);
//         System.out.println("Preloading " + repository.save(new OrderRequest(items1, "pending", Long.valueOf(1))));

//         Map<String, Integer> items2 = new HashMap<>();
//         items2.put("mozzarella", 3);
//         items2.put("paneer", 10);
//         items2.put("kanchan", 2);
//         System.out.println("Preloading " + repository.save(new OrderRequest(items2, "pending", Long.valueOf(2))));
//       };
//     }
// }
