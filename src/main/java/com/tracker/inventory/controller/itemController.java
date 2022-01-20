package com.tracker.inventory.controller;

import com.tracker.inventory.application.CsvExportationService;
import com.tracker.inventory.application.ItemService;
import com.tracker.inventory.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
  public List<Item> getItems() {
    return itemService.getItems();
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
      @PathVariable("itemId") UUID itemId, HttpServletResponse servletResponse) throws IOException {
    csvExportationService.writeItemToCsv(itemId, servletResponse);
  }

  @GetMapping(path = "/download")
  public void exportAllItemToCsv(HttpServletResponse servletResponse) throws IOException {
    csvExportationService.writeAllItemsToCsv(servletResponse);
  }
}
