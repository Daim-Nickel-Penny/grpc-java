package com.grpcjava.grpc.greeting.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.uno.UnoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Client Start");

        ManagedChannel channel = ManagedChannelBuilder.
                forAddress("localhost", 50051).
                usePlaintext().
                build();
        System.out.println("Creating Stub");

        // old code
        //UnoServiceGrpc.UnoServiceBlockingStub syncClient = UnoServiceGrpc.newBlockingStub(channel);

        // created  a greet service client (blocking = synchronous )
        GreetServiceGrpc.GreetServiceBlockingStub greetClient =
                GreetServiceGrpc.newBlockingStub(channel);

        // created a protocol buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Foo")
                .setLastName("Bar")
                .build();
        // do same for Greet request
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();


        // call the RPC and get back a GreetResponse (protocol buffers )
        // this greet is coming from greetServer
        GreetResponse greetResponse = greetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        System.out.println("Shutting Channel");
        channel.shutdown();

    }
}
