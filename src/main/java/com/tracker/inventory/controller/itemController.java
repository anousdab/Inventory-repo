package com.tracker.inventory.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.inventory.application.CsvExportationService;
import com.tracker.inventory.application.ItemService;
import com.tracker.inventory.domain.Item;

@RestController
@RequestMapping(path = "inventory")
public class itemController {

    private final ItemService itemService;
    private final CsvExportationService csvExportationService;

    @Autowired
    public itemController(ItemService itemService, CsvExportationService csvExportationService) {
        this.itemService = itemService;
        this.csvExportationService = csvExportationService;
    }

    @GetMapping
    public List<Item> getItems(@RequestParam(value = "is_deleted", required = false, defaultValue = "false") boolean isDeleted) {

        return itemService.findAllFilter(isDeleted);
    }

    @GetMapping(path = "{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable("itemId") UUID itemId) {
        Item item = itemService.getItem(itemId);
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void RegisterNewItem(@RequestBody Item item) {
        itemService.addNewItem(item);
    }

    @PutMapping(path = "{itemId}")
    public void updateItem(
        @PathVariable("itemId") UUID itemId, @RequestBody(required = false) Item item) {
        itemService.updateItem(itemId, item);
    }

    @DeleteMapping(path = "{itemId}")
    public void deleteItem(@PathVariable("itemId") UUID itemId) {
        itemService.deleteItem(itemId);
    }

    @GetMapping(path = "{itemId}/download")
    public void exportItemToCsv(
        @PathVariable("itemId") UUID itemId, HttpServletResponse servletResponse) {
        csvExportationService.writeItemToCsv(itemId, servletResponse);
    }

    @GetMapping(path = "/download")
    public void exportAllItemToCsv(HttpServletResponse servletResponse) {
        csvExportationService.writeAllItemsToCsv(servletResponse);
    }
}
