// package com.alpine.dairy.inventoryManagementService;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class LoadInventoryDatabase {

//     @Bean
//     CommandLineRunner initInventoryDatabase(InventoryRepository repository) {
//         return (args) -> {
//             System.out.println("Preloading " + repository.save(new InventoryItem("mozzarella", "Mozzarella Cheese", 100, 5, 0)));
//             System.out.println("Preloading " + repository.save(new InventoryItem("paneer", "Paneer Cheese", 200, 15, 0)));
//             System.out.println("Preloading " + repository.save(new InventoryItem("kanchan", "Kanchan Cheese", 150, 3, 0)));
//         };
//     }
// }