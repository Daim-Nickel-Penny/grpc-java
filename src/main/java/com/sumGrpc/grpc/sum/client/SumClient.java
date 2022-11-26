package com.sumGrpc.grpc.sum.client;

import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SumClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.
                forAddress("localhost", 3000)
                .usePlaintext()
                .build();

        System.out.println("Creating Stub");

        SumServiceGrpc.SumServiceBlockingStub sumClient = SumServiceGrpc.newBlockingStub(channel);

        Sum sum = Sum.newBuilder()
                .setFirstNumber(2)
                .setSecondNumber(3)
                .build();


        SumRequest sumRequest = SumRequest
                .newBuilder()
                .setSum(sum)
                .build();


        SumResponse sumResponse = sumClient.sumRPC(sumRequest);

        System.out.println(sumResponse.getResult());

        System.out.println("Channel Shutdown");
        channel.shutdown();
    }
}
