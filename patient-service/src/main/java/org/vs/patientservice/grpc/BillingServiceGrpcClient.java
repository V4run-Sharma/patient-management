package org.vs.patientservice.grpc;

import billing.BillingServiceGrpc;
import billing.CreateBillingAccountRequest;
import billing.CreateBillingAccountResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

  private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
  private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

  // localhost:9001/BillingService/CreateBillingAccount
  // aws.grpc:123123/BillingService/CreateBillingAccount
  public BillingServiceGrpcClient(
      @Value("${billing.service.address:localhost}") String billingServiceAddress,
      @Value("${billing.service.grpc.port:9001}") int billingServicePort
  ) {
    log.info("Connecting to Billing gRPC Service at {}:{}", billingServiceAddress, billingServicePort);

    ManagedChannel channel = ManagedChannelBuilder
        .forAddress(billingServiceAddress, billingServicePort)
        .usePlaintext() // Use plaintext for simplicity; consider using TLS in production
        .build();

    billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
  }

  public void createBillingAccount(
      String patientId, String name, String email) {
    log.info("Creating billing account for patient: {}", patientId);

    CreateBillingAccountRequest request = CreateBillingAccountRequest.newBuilder()
        .setPatientId(patientId)
        .setName(name)
        .setEmail(email)
        .build();

    CreateBillingAccountResponse response = billingServiceBlockingStub.createBillingAccount(request);

    log.info("Received response from Billing Service: {}", response);
  }
}
