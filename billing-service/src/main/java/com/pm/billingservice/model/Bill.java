package com.pm.billingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId; // Foreign key to Patient service

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String billDate; // For simplicity, using String. Consider LocalDate for production.

    @Column(nullable = false)
    private Boolean paid = false; // Default to not paid
}
