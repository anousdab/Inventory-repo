package com.tracker.inventory.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    @Query("Select i from Item i where i.name = ?1")
    Optional<Item> findItemByName(String name);
}
