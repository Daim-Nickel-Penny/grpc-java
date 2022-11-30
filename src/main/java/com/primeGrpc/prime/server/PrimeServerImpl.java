package com.primeGrpc.prime.server;

import com.proto.prime.Prime;
import com.proto.prime.PrimeRequest;
import com.proto.prime.PrimeResponse;
import com.proto.prime.PrimeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public class PrimeServerImpl extends PrimeServiceGrpc.PrimeServiceImplBase {

    @Override
    public void primeNumber(PrimeRequest request, StreamObserver<PrimeResponse> responseObserver) {

        Prime prime = request.getPrime();

        int number = prime.getNumber();

        // apply prime logic

        int c = 2;
        try {
            while (number > 1) {
                if (number % c == 0) {
                    PrimeResponse response = PrimeResponse.newBuilder().setResult(c).build();
                    responseObserver.onNext(response);
                    number /= c;
                    Thread.sleep(500L);
                }
                else
                    c++;
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            responseObserver.onCompleted();
        }

    }
}
