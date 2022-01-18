package com.tracker.inventory.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.inventory.application.ItemService;
import com.tracker.inventory.domain.Item;

@RestController
@RequestMapping(path = "inventory")
public class itemController {

    private final ItemService itemService;

    @Autowired // Magically Instantiated
    public itemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping(path = "{itemId}")
    public ResponseEntity<Optional<Item>> getItem(@PathVariable("itemId") UUID itemId) {
        Optional<Item> item = itemService.getItem(itemId);

        return ResponseEntity.ok().body(item);
    }


    @PostMapping //get it from the response body
    @ResponseStatus(HttpStatus.CREATED)
    public void RegisterNewItem(@RequestBody Item item) {
        itemService.addNewItem(item);
    }

    @PutMapping(path = "{itemId}")
    public void updateItem(@PathVariable("itemId") UUID itemId, @RequestBody(required = false) Item item) {
        itemService.updateItem(itemId, item);
    }

    @DeleteMapping(path = "{itemId}")
    public void deleteItem(@PathVariable("itemId") UUID itemId) {
        itemService.deleteItem(itemId);
    }


}
