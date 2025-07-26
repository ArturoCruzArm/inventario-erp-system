package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "received_quantity")
    private Integer receivedQuantity = 0;

    // Constructors
    public PurchaseOrderItem() {}

    public PurchaseOrderItem(PurchaseOrder purchaseOrder, Product product, Integer quantity, BigDecimal unitPrice) {
        this.purchaseOrder = purchaseOrder;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        calculateTotalAmount();
    }

    // Business methods
    public void calculateTotalAmount() {
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal netAmount = subtotal.subtract(discountAmount);
        this.totalAmount = netAmount.add(taxAmount);
    }

    // Getters and Setters
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        calculateTotalAmount();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        calculateTotalAmount();
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
        calculateTotalAmount();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        calculateTotalAmount();
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        calculateTotalAmount();
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        calculateTotalAmount();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }
}