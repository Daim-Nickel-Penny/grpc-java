package com.sumGrpc.grpc.sum.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SumServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sum Server Start");

        Server server = ServerBuilder.forPort(3000)
                .addService(new SumServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( ()->{
            System.out.println("Received shutdown");
            server.shutdown();
            System.out.println("Server Closed");
        }));

        server.awaitTermination();
    }
}
