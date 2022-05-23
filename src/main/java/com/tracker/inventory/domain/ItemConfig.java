package com.tracker.inventory.domain;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemConfig {


    @Bean
    CommandLineRunner commandLineRunner(ItemRepository itemRepository) {

        return args -> {

            Item item = new Item("chair", 1, "Qc");
            Item item1 = new Item("desk", 2, "Tor");

            itemRepository.saveAll(List.of(item, item1));

        };


    }
}
