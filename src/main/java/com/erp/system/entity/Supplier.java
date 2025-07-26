package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "supplier_code", unique = true, nullable = false)
    private String supplierCode;

    @NotBlank
    @Size(max = 200)
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Size(max = 100)
    @Column(name = "contact_person")
    private String contactPerson;

    @Email
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    @Size(max = 20)
    @Column(name = "mobile")
    private String mobile;

    @Size(max = 20)
    @Column(name = "fax")
    private String fax;

    @Size(max = 100)
    @Column(name = "website")
    private String website;

    @Size(max = 50)
    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "payment_terms")
    private Integer paymentTerms = 30;

    @Size(max = 500)
    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SupplierAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    // Constructors
    public Supplier() {}

    public Supplier(String supplierCode, String companyName) {
        this.supplierCode = supplierCode;
        this.companyName = companyName;
    }

    // Getters and Setters
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Integer getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Integer paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<SupplierAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<SupplierAddress> addresses) {
        this.addresses = addresses;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}