package com.erp.system.service;

import com.erp.system.entity.InventoryMovement;
import com.erp.system.entity.MovementType;
import com.erp.system.entity.Product;
import com.erp.system.repository.InventoryMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public List<InventoryMovement> findAll() {
        return inventoryMovementRepository.findAll();
    }

    public Optional<InventoryMovement> findById(Long id) {
        return inventoryMovementRepository.findById(id);
    }

    public InventoryMovement save(InventoryMovement inventoryMovement) {
        return inventoryMovementRepository.save(inventoryMovement);
    }

    public InventoryMovement createMovement(Product product, MovementType movementType, Integer quantity, 
                                          BigDecimal unitCost, String referenceNumber, String referenceType, String notes) {
        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setMovementType(movementType);
        movement.setQuantity(quantity);
        movement.setUnitCost(unitCost);
        movement.setTotalCost(unitCost != null ? unitCost.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO);
        movement.setReferenceNumber(referenceNumber);
        movement.setReferenceType(referenceType);
        movement.setNotes(notes);
        movement.setMovementDate(LocalDateTime.now());
        
        return inventoryMovementRepository.save(movement);
    }

    public void stockIn(Product product, Integer quantity, BigDecimal unitCost, String referenceNumber, String referenceType) {
        createMovement(product, MovementType.IN, quantity, unitCost, referenceNumber, referenceType, "Stock In");
    }

    public void stockOut(Product product, Integer quantity, String referenceNumber, String referenceType) {
        Integer currentStock = getCurrentStock(product.getId());
        if (currentStock < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + currentStock + ", Required: " + quantity);
        }
        createMovement(product, MovementType.OUT, quantity, null, referenceNumber, referenceType, "Stock Out");
    }

    public void adjustStock(Product product, Integer adjustmentQuantity, String reason) {
        MovementType movementType = adjustmentQuantity > 0 ? MovementType.IN : MovementType.OUT;
        Integer quantity = Math.abs(adjustmentQuantity);
        createMovement(product, movementType, quantity, null, null, "ADJUSTMENT", reason);
    }

    public Integer getCurrentStock(Long productId) {
        Integer stock = inventoryMovementRepository.getCurrentStock(productId);
        return stock != null ? stock : 0;
    }

    public List<InventoryMovement> getMovementsByProduct(Long productId) {
        return inventoryMovementRepository.findByProductIdOrderByMovementDateDesc(productId);
    }

    public List<InventoryMovement> getMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return inventoryMovementRepository.findByMovementDateBetween(startDate, endDate);
    }

    public List<InventoryMovement> getMovementsByReference(String referenceNumber, String referenceType) {
        return inventoryMovementRepository.findByReferenceNumberAndType(referenceNumber, referenceType);
    }

    public List<InventoryMovement> getMovementsByType(MovementType movementType) {
        return inventoryMovementRepository.findByMovementType(movementType);
    }
}