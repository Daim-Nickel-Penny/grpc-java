package com.primeGrpc.prime.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrimeServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(3000).
                addService(new PrimeServerImpl())
                .build();

        server.start();
        System.out.println("Prime Server Start");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Received shutdown request");
            server.shutdown();
            System.out.println("Prime Server Stopped");
        }));

        server.awaitTermination();
    }
}
