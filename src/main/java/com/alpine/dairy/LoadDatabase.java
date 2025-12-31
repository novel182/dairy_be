package com.alpine.dairy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository) {
  
      return (args) -> {
        log.info("Preloading " + customerRepository.save(new Customer("John Doe", "1234567890")));
        log.info("Preloading " + customerRepository.save(new Customer("Jane Smith", "9876543210")));
      };
    }

}