package com.computeAverage.client;

import com.proto.computeAverage.ComputeAverage;
import com.proto.computeAverage.ComputeAverageRequest;
import com.proto.computeAverage.ComputeAverageResponse;
import com.proto.computeAverage.ComputeAverageServiceGrpc;
import com.proto.greet.GreetServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ComputeAverageClient {
    public static void main(String[] args) {
        System.out.println("Client Start");

        ComputeAverageClient obj = new ComputeAverageClient();
        obj.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 3000).usePlaintext().build();


        doClientStreamCall(channel);

    }

    private void doClientStreamCall(ManagedChannel channel) {
        System.out.println("Creating Stub");

        ComputeAverageServiceGrpc.ComputeAverageServiceStub asyncClient = ComputeAverageServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        //generate request

        StreamObserver<ComputeAverageRequest> requestObserver = asyncClient.average(new StreamObserver<ComputeAverageResponse>() {
            @Override
            public void onNext(ComputeAverageResponse value) {

                System.out.println("Received response from server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("Server has completed sending");
                latch.countDown();
            }
        });

        System.out.println("Sending number #1");
        requestObserver.onNext(
                ComputeAverageRequest.newBuilder().setComputeAverage(
                        ComputeAverage.newBuilder().setNumber(1).build()
                ).build()
        );

        System.out.println("Sending number #2");
        requestObserver.onNext(
                ComputeAverageRequest.newBuilder().setComputeAverage(
                        ComputeAverage.newBuilder().setNumber(2).build()
                ).build()
        );


        System.out.println("Sending number #3");
        requestObserver.onNext(
                ComputeAverageRequest.newBuilder().setComputeAverage(
                        ComputeAverage.newBuilder().setNumber(3).build()
                ).build()
        );

        System.out.println("Sending number #4");
        requestObserver.onNext(
                ComputeAverageRequest.newBuilder().setComputeAverage(
                        ComputeAverage.newBuilder().setNumber(4).build()
                ).build()
        );


        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
