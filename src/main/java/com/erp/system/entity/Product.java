package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "product_code", unique = true, nullable = false)
    private String productCode;

    @NotBlank
    @Size(max = 200)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Size(max = 50)
    @Column(name = "unit")
    private String unit;

    @NotNull
    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice = BigDecimal.ZERO;

    @NotNull
    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice = BigDecimal.ZERO;

    @Column(name = "minimum_stock")
    private Integer minimumStock = 0;

    @Column(name = "maximum_stock")
    private Integer maximumStock = 0;

    @Column(name = "reorder_level")
    private Integer reorderLevel = 0;

    @Size(max = 200)
    @Column(name = "image_url")
    private String imageUrl;

    @Size(max = 100)
    @Column(name = "barcode")
    private String barcode;

    @Column(name = "taxable")
    private Boolean taxable = true;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InventoryMovement> inventoryMovements = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SalesOrderItem> salesOrderItems = new ArrayList<>();

    // Constructors
    public Product() {}

    public Product(String productCode, String name) {
        this.productCode = productCode;
        this.name = name;
    }

    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public Integer getMaximumStock() {
        return maximumStock;
    }

    public void setMaximumStock(Integer maximumStock) {
        this.maximumStock = maximumStock;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public List<InventoryMovement> getInventoryMovements() {
        return inventoryMovements;
    }

    public void setInventoryMovements(List<InventoryMovement> inventoryMovements) {
        this.inventoryMovements = inventoryMovements;
    }

    public List<PurchaseOrderItem> getPurchaseOrderItems() {
        return purchaseOrderItems;
    }

    public void setPurchaseOrderItems(List<PurchaseOrderItem> purchaseOrderItems) {
        this.purchaseOrderItems = purchaseOrderItems;
    }

    public List<SalesOrderItem> getSalesOrderItems() {
        return salesOrderItems;
    }

    public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
        this.salesOrderItems = salesOrderItems;
    }
}