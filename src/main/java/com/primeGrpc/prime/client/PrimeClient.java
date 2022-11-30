package com.primeGrpc.prime.client;

import com.proto.prime.Prime;
import com.proto.prime.PrimeRequest;
import com.proto.prime.PrimeResponse;
import com.proto.prime.PrimeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PrimeClient {
    public static void main(String[] args) {
        System.out.println("Prime Client Start");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",3000)
                .usePlaintext()
                .build();
        System.out.println("Creating Stub");

        PrimeServiceGrpc.PrimeServiceBlockingStub primeClient =
                PrimeServiceGrpc.newBlockingStub(channel);

        PrimeRequest prime = PrimeRequest.newBuilder().setPrime(Prime.newBuilder().setNumber(120)).build();

        primeClient.primeNumber(prime).forEachRemaining(primeResponse -> {
            System.out.println(primeResponse.getResult());
        });

        System.out.println("Client shutting down");
        channel.shutdown();
    }
}
