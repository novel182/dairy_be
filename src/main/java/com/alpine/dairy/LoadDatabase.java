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
    CommandLineRunner initDatabase(CustomerRepository repository) {
  
      return (args) -> {
        log.info("Preloading " + repository.save(new Customer("Bilbo Baggins", "2368798348")));
        log.info("Preloading " + repository.save(new Customer("Frodo Baggins", "6723849509")));
      };
    }
}
