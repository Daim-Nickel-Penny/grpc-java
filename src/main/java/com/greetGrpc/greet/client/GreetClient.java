package com.greetGrpc.greet.client;

import com.proto.greet.*;
import com.proto.uno.UnoServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetClient {

    public static void main(String[] args) {
        System.out.println("Client Start");

        GreetClient main = new GreetClient();
        main.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.
                forAddress("localhost", 50051).
                usePlaintext().
                build();


        //doUnaryCall(channel);
        //doServerStreamingCall(channel);
        doClientStreamingCall(channel);


        System.out.println("Shutting Channel");
        channel.shutdown();

    }

    public void doUnaryCall(ManagedChannel channel) {
        System.out.println("Creating Stub");

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
        System.out.println("Creating Stub");

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


    public void doClientStreamingCall(ManagedChannel channel) {
        System.out.println("Creating Stub");

        //create client(stub)

        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver = asyncClient.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                // we get response from server
                //onNext will be called once

                System.out.println("Received response from server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // we get error from server
            }

            @Override
            public void onCompleted() {
                //server is done
                //onCompleted will be called right after onNext()
                System.out.println("Server has completed sending");
                latch.countDown();
            }
        });
        // streaming message #1
        System.out.println("sending message 1");
        requestObserver.onNext(
                LongGreetRequest.newBuilder().
                        setGreeting(Greeting.newBuilder().
                                setFirstName("Foo").
                                build())
                        .build());

        // streaming message #2
        System.out.println("sending message 2");
        requestObserver.onNext(
                LongGreetRequest.newBuilder().
                        setGreeting(Greeting.newBuilder().
                                setFirstName("Bar").
                                build())
                        .build());

        // streaming message #3
        System.out.println("sending message 3");
        requestObserver.onNext(
                LongGreetRequest.newBuilder().
                        setGreeting(Greeting.newBuilder().
                                setFirstName("John").
                                build())
                        .build());


        //we tell server that client is done sending data
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
         e.printStackTrace();
        }


    }
}