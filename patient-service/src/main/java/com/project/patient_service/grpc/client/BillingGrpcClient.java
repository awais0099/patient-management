package com.project.patient_service.grpc.client;

import billing.BillRequest;
import billing.BillResponse;
import billing.BillingServiceGrpcGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(
            BillingGrpcClient.class);
    private final BillingServiceGrpcGrpc.BillingServiceGrpcBlockingStub blockingStub;

    public BillingGrpcClient(@Value("${grpc.billing.service.host}") String grpcServerHost,
                             @Value("${grpc.billing.service.port}") int grpcServerPort) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort).usePlaintext().build();
        blockingStub = BillingServiceGrpcGrpc.newBlockingStub(managedChannel);
    }

    public BillResponse CreateBill(BillRequest request) {
        log.info("Creating bill for patient with ID: {}", request.getPatientId());
        BillResponse response = blockingStub.createBill(request);
        log.info("Bill created successfully with ID: {}", response.getId());
        return response;
    }
}
