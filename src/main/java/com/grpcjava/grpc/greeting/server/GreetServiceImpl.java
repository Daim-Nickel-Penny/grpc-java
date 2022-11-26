package com.grpcjava.grpc.greeting.server;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
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


}
