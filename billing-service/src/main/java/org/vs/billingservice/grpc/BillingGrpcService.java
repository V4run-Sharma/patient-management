package org.vs.billingservice.grpc;

import billing.BillingServiceGrpc;
import billing.CreateBillingAccountRequest;
import billing.CreateBillingAccountResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

  private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

  @Override
  public void createBillingAccount(
      CreateBillingAccountRequest createBillingAccountRequest,
      StreamObserver<CreateBillingAccountResponse> createBillingAccountResponseStreamObserver) {

    log.info("Received request to create billing account: {}", createBillingAccountRequest);

    // Business logic to create a billing account, i.e., interact with the database or other services
    CreateBillingAccountResponse response = CreateBillingAccountResponse.newBuilder()
        .setAccountId("12345") // This would be generated dynamically in a real application
        .setStatus("ACTIVE")
        .build();

    createBillingAccountResponseStreamObserver.onNext(response);
    createBillingAccountResponseStreamObserver.onCompleted();
  }
}
