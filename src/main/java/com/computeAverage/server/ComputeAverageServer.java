package com.computeAverage.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ComputeAverageServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(3000).build();
        server.start();

        System.out.println("Server Started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received shutdown");
            server.shutdown();
            System.out.println("Server Stopped");
        }));

        server.awaitTermination();
    }
}
