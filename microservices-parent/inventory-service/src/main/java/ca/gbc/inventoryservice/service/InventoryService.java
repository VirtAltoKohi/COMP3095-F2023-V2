package ca.gbc.inventoryservice.service;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.dto.InventoryResponse;
import ca.gbc.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> IsInStock(List<InventoryRequest> requests);

}
