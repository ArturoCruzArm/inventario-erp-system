package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "payment_number", unique = true)
    private String paymentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "notes", length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.COMPLETED;

    // Constructors
    public Payment() {
        this.paymentDate = LocalDate.now();
    }

    public Payment(Customer customer, BigDecimal amount, PaymentMethod paymentMethod) {
        this();
        this.customer = customer;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}