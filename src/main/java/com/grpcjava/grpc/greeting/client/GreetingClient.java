package com.grpcjava.grpc.greeting.client;

import com.proto.uno.UnoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Client");

        ManagedChannel channel = ManagedChannelBuilder.
                forAddress("localhost", 50051).
                usePlaintext().
                build();
        System.out.println("Creating Stub");
        UnoServiceGrpc.UnoServiceBlockingStub syncClient = UnoServiceGrpc.newBlockingStub(channel);

        System.out.println("Shutting Channel");
        channel.shutdown();

    }
}
