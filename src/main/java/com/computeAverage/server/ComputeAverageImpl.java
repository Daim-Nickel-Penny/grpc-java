package com.computeAverage.server;

import com.proto.computeAverage.ComputeAverageRequest;
import com.proto.computeAverage.ComputeAverageResponse;
import com.proto.computeAverage.ComputeAverageServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ComputeAverageImpl extends ComputeAverageServiceGrpc.ComputeAverageServiceImplBase {


    @Override
    public StreamObserver<ComputeAverageRequest> average(StreamObserver<ComputeAverageResponse> responseObserver) {

        //first building request


        StreamObserver<ComputeAverageRequest> observerRequest = new StreamObserver<ComputeAverageRequest>() {
            int count = 0,sum=0; double result = 0.0d;

            @Override
            public void onNext(ComputeAverageRequest value) {
                sum+= value.getComputeAverage().getNumber();
                count++;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                double sD = sum;
                double cD = count;
                result = sD/cD;
                //System.out.println(count+"  "+sum+"  "+result);

                responseObserver.onNext(
                        ComputeAverageResponse.newBuilder()
                                .setResult(result)
                                .build()
                );

                responseObserver.onCompleted();
            }
        };


        return observerRequest;
    }
}
