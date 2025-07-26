package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType = CustomerType.REGULAR;

    @Column(name = "credit_limit")
    private Double creditLimit = 0.0;

    @Column(name = "credit_days")
    private Integer creditDays = 30;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SalesOrder> salesOrders = new ArrayList<>();

    // Constructors
    public Customer() {}

    public Customer(String customerCode, String companyName) {
        this.customerCode = customerCode;
        this.companyName = companyName;
    }

    // Getters and Setters
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }

    public List<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public List<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    public void setSalesOrders(List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }
}