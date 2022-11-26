package com.sumGrpc.grpc.sum.server;

import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceGrpc.SumServiceImplBase {


    @Override
    public void sumRPC(SumRequest request, StreamObserver<SumResponse> responseObserver) {

        Sum sum = request.getSum();

        int firstNumber = sum.getFirstNumber();
        int secondNumber = sum.getSecondNumber();

        int result = firstNumber+secondNumber;
        SumResponse sumResponse = SumResponse.newBuilder().setResult(result).build();

        responseObserver.onNext(sumResponse);

        responseObserver.onCompleted();
    }
}
