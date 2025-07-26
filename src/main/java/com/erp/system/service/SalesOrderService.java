package com.erp.system.service;

import com.erp.system.entity.Customer;
import com.erp.system.entity.OrderStatus;
import com.erp.system.entity.SalesOrder;
import com.erp.system.entity.SalesOrderItem;
import com.erp.system.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private InventoryService inventoryService;

    public List<SalesOrder> findAll() {
        return salesOrderRepository.findByActiveTrue();
    }

    public Optional<SalesOrder> findById(Long id) {
        return salesOrderRepository.findById(id);
    }

    public Optional<SalesOrder> findByOrderNumber(String orderNumber) {
        return salesOrderRepository.findByOrderNumber(orderNumber);
    }

    public SalesOrder save(SalesOrder salesOrder) {
        calculateTotals(salesOrder);
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder createSalesOrder(Customer customer) {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNumber(generateOrderNumber());
        salesOrder.setCustomer(customer);
        salesOrder.setOrderDate(LocalDate.now());
        salesOrder.setStatus(OrderStatus.PENDING);
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder updateSalesOrder(Long id, SalesOrder salesOrderDetails) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + id));
        
        salesOrder.setCustomer(salesOrderDetails.getCustomer());
        salesOrder.setDeliveryDate(salesOrderDetails.getDeliveryDate());
        salesOrder.setStatus(salesOrderDetails.getStatus());
        salesOrder.setNotes(salesOrderDetails.getNotes());
        
        calculateTotals(salesOrder);
        return salesOrderRepository.save(salesOrder);
    }

    public void deleteSalesOrder(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + id));
        
        if (salesOrder.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Cannot delete a sales order that is not in pending status");
        }
        
        salesOrder.setActive(false);
        salesOrderRepository.save(salesOrder);
    }

    public SalesOrder confirmOrder(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + id));
        
        if (salesOrder.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be confirmed");
        }
        
        // Check stock availability
        for (SalesOrderItem item : salesOrder.getItems()) {
            Integer availableStock = inventoryService.getCurrentStock(item.getProduct().getId());
            if (availableStock < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + item.getProduct().getName() 
                    + ". Available: " + availableStock + ", Required: " + item.getQuantity());
            }
        }
        
        salesOrder.setStatus(OrderStatus.CONFIRMED);
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder shipOrder(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + id));
        
        if (salesOrder.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed orders can be shipped");
        }
        
        // Update inventory
        for (SalesOrderItem item : salesOrder.getItems()) {
            inventoryService.stockOut(item.getProduct(), item.getQuantity(), 
                salesOrder.getOrderNumber(), "SALES_ORDER");
            item.setShippedQuantity(item.getQuantity());
        }
        
        salesOrder.setStatus(OrderStatus.SHIPPED);
        return salesOrderRepository.save(salesOrder);
    }

    private void calculateTotals(SalesOrder salesOrder) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        
        for (SalesOrderItem item : salesOrder.getItems()) {
            item.calculateTotalAmount();
            subtotal = subtotal.add(item.getTotalAmount().subtract(item.getTaxAmount()));
            taxAmount = taxAmount.add(item.getTaxAmount());
        }
        
        salesOrder.setSubtotal(subtotal);
        salesOrder.setTaxAmount(taxAmount);
        salesOrder.setTotalAmount(subtotal.add(taxAmount).subtract(salesOrder.getDiscountAmount()));
    }

    public List<SalesOrder> findByCustomer(Long customerId) {
        return salesOrderRepository.findByCustomerId(customerId);
    }

    public List<SalesOrder> findByStatus(OrderStatus status) {
        return salesOrderRepository.findByStatus(status);
    }

    public List<SalesOrder> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesOrderRepository.findByOrderDateBetween(startDate, endDate);
    }

    public List<SalesOrder> searchOrders(String searchTerm) {
        return salesOrderRepository.findBySearchTerm(searchTerm);
    }

    public String generateOrderNumber() {
        long count = salesOrderRepository.count();
        return "SO" + String.format("%08d", count + 1);
    }
}