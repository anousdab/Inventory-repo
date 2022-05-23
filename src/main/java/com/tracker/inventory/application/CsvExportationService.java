package com.tracker.inventory.application;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracker.inventory.domain.Item;

@Service
public class CsvExportationService {

  public static final String[] CSV_HEADER = {"ID", "Name", "Quantity", "Location"};
  public static final String CONTENT_TYPE = "text/csv";
  public static final String CONTENT_DISPOSITION = "Content-Disposition";
  public static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT;
  private final ItemService itemService;

  @Autowired
  public CsvExportationService(ItemService itemService) {
    this.itemService = itemService;
  }

  public void writeItemToCsv(UUID itemId, HttpServletResponse response) {

    Item itemToExport = itemService.getItem(itemId);

    response.setContentType(CONTENT_TYPE);
    response.addHeader(CONTENT_DISPOSITION, "attachment; filename=\"item.csv\"");

    try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSV_FORMAT)) {
      csvPrinter.printRecord(CSV_HEADER);
      csvPrinter.printRecord(
          itemToExport.getId(),
          itemToExport.getName(),
          itemToExport.getQuantity(),
          itemToExport.getLocation());

    } catch (IOException e) {
      throw new IllegalStateException("Error While writing CSV ", e);
    }
  }

  public void writeAllItemsToCsv(HttpServletResponse response) {

    response.setContentType(CONTENT_TYPE);
    response.addHeader(CONTENT_DISPOSITION, "attachment; filename=\"items.csv\"");

    try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT)) {
      csvPrinter.printRecord(CSV_HEADER);

      for (Item item : itemService.getItems()) {
        csvPrinter.printRecord(
            item.getId(), item.getName(), item.getQuantity(), item.getLocation());
      }

    } catch (IOException e) {
      throw new IllegalStateException("Error While writing CSV ", e);
    }
  }
}
