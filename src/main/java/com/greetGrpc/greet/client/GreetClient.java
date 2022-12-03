package com.greetGrpc.greet.client;

import com.proto.greet.*;
import com.proto.uno.UnoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetClient {

    public static void main(String[] args) {
        System.out.println("Client Start");

        GreetClient main = new GreetClient();
        main.run();
    }

    private void run() {
        System.out.println("Creating Stub");
        ManagedChannel channel = ManagedChannelBuilder.
                forAddress("localhost", 50051).
                usePlaintext().
                build();


        doUnaryCall(channel);
        doServerStreamingCall(channel);


        System.out.println("Shutting Channel");
        channel.shutdown();

    }

    public void doUnaryCall(ManagedChannel channel) {
        // created  a greet service client (blocking = synchronous )
        GreetServiceGrpc.GreetServiceBlockingStub greetClient =
                GreetServiceGrpc.newBlockingStub(channel);

        //Unary

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
    }

    public void doServerStreamingCall(ManagedChannel channel) {
        // created  a greet service client (blocking = synchronous )
        GreetServiceGrpc.GreetServiceBlockingStub greetClient =
                GreetServiceGrpc.newBlockingStub(channel);

        // Server Streaming

        // prepare request
        GreetManyTimesRequest greetManyTimesRequest =
                GreetManyTimesRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder().setFirstName("Foo"))
                        .build();

        //stream response (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
                });
    }

}