package com.erp.system.service;

import com.erp.system.entity.PurchaseOrder;
import com.erp.system.entity.PurchaseOrderItem;
import com.erp.system.entity.OrderStatus;
import com.erp.system.entity.InventoryMovement;
import com.erp.system.entity.MovementType;
import com.erp.system.repository.PurchaseOrderRepository;
import com.erp.system.repository.InventoryMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }
    
    public Optional<PurchaseOrder> findById(Long id) {
        return purchaseOrderRepository.findById(id);
    }
    
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }
    
    public void delete(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
    
    public void updateStatus(Long id, OrderStatus status) {
        Optional<PurchaseOrder> purchaseOrderOpt = purchaseOrderRepository.findById(id);
        if (purchaseOrderOpt.isPresent()) {
            PurchaseOrder purchaseOrder = purchaseOrderOpt.get();
            purchaseOrder.setStatus(status);
            purchaseOrderRepository.save(purchaseOrder);
        }
    }
    
    public void receiveOrder(Long id) {
        Optional<PurchaseOrder> purchaseOrderOpt = purchaseOrderRepository.findById(id);
        if (purchaseOrderOpt.isPresent()) {
            PurchaseOrder purchaseOrder = purchaseOrderOpt.get();
            
            // Update order status
            purchaseOrder.setStatus(OrderStatus.RECEIVED);
            purchaseOrderRepository.save(purchaseOrder);
            
            // Create inventory movements for each item
            for (PurchaseOrderItem item : purchaseOrder.getItems()) {
                InventoryMovement movement = new InventoryMovement();
                movement.setProduct(item.getProduct());
                movement.setMovementType(MovementType.IN);
                movement.setQuantity(item.getQuantity());
                movement.setUnitCost(item.getUnitPrice());
                movement.setTotalCost(item.getTotalAmount());
                movement.setMovementDate(LocalDateTime.now());
                movement.setReferenceNumber(purchaseOrder.getOrderNumber());
                movement.setReferenceType("PURCHASE_ORDER");
                // Note: InventoryMovement doesn't have setDescription method, using notes instead
                movement.setNotes("Purchase order received: " + purchaseOrder.getOrderNumber() + " - Auto-generated from purchase order receipt");
                inventoryMovementRepository.save(movement);
            }
        }
    }
    
    public List<PurchaseOrder> findByStatus(OrderStatus status) {
        return purchaseOrderRepository.findByStatus(status);
    }
    
    public List<PurchaseOrder> findBySupplierId(Long supplierId) {
        return purchaseOrderRepository.findBySupplierId(supplierId);
    }
}
