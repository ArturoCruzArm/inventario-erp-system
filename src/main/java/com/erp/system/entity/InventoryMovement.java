package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
public class InventoryMovement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "reference_type")
    private String referenceType;

    @Column(name = "notes")
    private String notes;

    @Column(name = "movement_date")
    private LocalDateTime movementDate;

    // Constructors
    public InventoryMovement() {
        this.movementDate = LocalDateTime.now();
    }

    public InventoryMovement(Product product, MovementType movementType, Integer quantity) {
        this();
        this.product = product;
        this.movementType = movementType;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }
}