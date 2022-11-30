package com.grpcjava.grpc.greeting.server;

import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    //implementation

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // Request sent by client, we have to make changes to stream observer


        // extract the fields we need
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String lastName = greeting.getLastName();

        // creating response
        String result = "Hello " + firstName;
        GreetResponse response = GreetResponse.newBuilder().setResult(result).build();

        //send response to client
        responseObserver.onNext(response);

        //complete RPC call
        responseObserver.onCompleted();

        //super.greet(request, responseObserver);
    }


    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {

        String firstName = request.getGreeting().getFirstName();
        try {
            for (int i = 0; i < 10; i++) {
                String result = "hello " + firstName + ", response no: " + i;
                GreetManyTimesResponse response = GreetManyTimesResponse
                        .newBuilder()
                        .setResult(result)
                        .build();


                responseObserver.onNext(response);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();

        }
    }
}
