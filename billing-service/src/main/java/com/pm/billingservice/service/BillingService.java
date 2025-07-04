package com.pm.billingservice.service;

import com.pm.billingservice.model.Bill;
import com.pm.billingservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    // Save a new bill or update an existing one
    @Transactional
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    // Get a bill by ID
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    // Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // Get bills by patient ID
    public List<Bill> getBillsByPatientId(Long patientId) {
        return billRepository.findByPatientId(patientId);
    }

    // Delete a bill by ID
    @Transactional
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    // Update bill details
    @Transactional
    public Bill updateBill(Long id, Bill billDetails) {
        return billRepository.findById(id)
                .map(bill -> {
                    bill.setPatientId(billDetails.getPatientId());
                    bill.setServiceType(billDetails.getServiceType());
                    bill.setAmount(billDetails.getAmount());
                    bill.setBillDate(billDetails.getBillDate());
                    bill.setPaid(billDetails.getPaid());
                    return billRepository.save(bill);
                }).orElseThrow(() -> new RuntimeException("Bill not found with id " + id));
    }

    // Mark a bill as paid
    @Transactional
    public Bill markBillAsPaid(Long id) {
        return billRepository.findById(id)
                .map(bill -> {
                    bill.setPaid(true);
                    return billRepository.save(bill);
                }).orElseThrow(() -> new RuntimeException("Bill not found with id " + id));
    }
}
