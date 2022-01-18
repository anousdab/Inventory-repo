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

            Item bus = new Item("bus", 1, "Qc");
            Item hamid = new Item("hamid", 1, "ot");

            itemRepository.saveAll(List.of(bus, hamid));

        };


    }
}
