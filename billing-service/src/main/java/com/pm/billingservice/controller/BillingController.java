package com.pm.billingservice.controller;

import com.pm.billingservice.model.Bill;
import com.pm.billingservice.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillingController {

    @Autowired
    private BillingService billingService;

    // Create a new bill
    @PostMapping
    public ResponseEntity<?> createBill(@Valid @RequestBody Bill bill) {
        Bill savedBill = billingService.saveBill(bill);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    // Get a bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billingService.getBillById(id)
                .map(bill -> new ResponseEntity<>(bill, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all bills
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billingService.getAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Get bills by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getBillsByPatientId(@PathVariable Long patientId) {
        List<Bill> bills = billingService.getBillsByPatientId(patientId);
        if (bills.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Update bill details
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @Valid @RequestBody Bill billDetails) {
        try {
            Bill updatedBill = billingService.updateBill(id, billDetails);
            return new ResponseEntity<>(updatedBill, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a bill by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBill(@PathVariable Long id) {
        try {
            billingService.deleteBill(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark a bill as paid
    @PatchMapping("/{id}/pay")
    public ResponseEntity<Bill> markBillAsPaid(@PathVariable Long id) {
        try {
            Bill updatedBill = billingService.markBillAsPaid(id);
            return new ResponseEntity<>(updatedBill, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
