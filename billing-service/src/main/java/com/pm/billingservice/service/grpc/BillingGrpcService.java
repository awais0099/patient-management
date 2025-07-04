package com.pm.billingservice.service.grpc;

import billing.BillRequest;
import billing.BillResponse;
import billing.BillingServiceGrpcGrpc;
import com.pm.billingservice.model.Bill;
import com.pm.billingservice.service.BillingService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpcGrpc.BillingServiceGrpcImplBase {

    @Autowired
    private BillingService billingService;

    @Override
    public void createBill(BillRequest request, StreamObserver<BillResponse> responseObserver) {
        Bill bill = new Bill();
        bill.setPatientId(request.getPatientId());
        bill.setServiceType(request.getServiceType());
        bill.setAmount(request.getAmount());
        bill.setBillDate(request.getBillDate());
        bill.setPaid(request.getPaid());

        Bill savedBill = billingService.saveBill(bill);
        BillResponse response = BillResponse.newBuilder()
                .setId(savedBill.getId())
                .setPatientId(savedBill.getPatientId())
                .setServiceType(savedBill.getServiceType())
                .setAmount(savedBill.getAmount())
                .setBillDate(savedBill.getBillDate())
                .setPaid(savedBill.getPaid())
                .setFound(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
