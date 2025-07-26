package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "supplier_addresses")
public class SupplierAddress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Size(max = 200)
    @Column(name = "address_line1")
    private String addressLine1;

    @Size(max = 200)
    @Column(name = "address_line2")
    private String addressLine2;

    @Size(max = 100)
    @Column(name = "city")
    private String city;

    @Size(max = 100)
    @Column(name = "state")
    private String state;

    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;

    @Size(max = 100)
    @Column(name = "country")
    private String country;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    // Constructors
    public SupplierAddress() {}

    public SupplierAddress(Supplier supplier, AddressType addressType) {
        this.supplier = supplier;
        this.addressType = addressType;
    }

    // Getters and Setters
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (addressLine1 != null) sb.append(addressLine1);
        if (addressLine2 != null) sb.append(", ").append(addressLine2);
        if (city != null) sb.append(", ").append(city);
        if (state != null) sb.append(", ").append(state);
        if (postalCode != null) sb.append(" ").append(postalCode);
        if (country != null) sb.append(", ").append(country);
        return sb.toString();
    }
}