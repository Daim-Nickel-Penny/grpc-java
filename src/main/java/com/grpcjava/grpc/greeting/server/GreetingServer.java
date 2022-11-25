package com.grpcjava.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;


import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("hello");

        Server server = ServerBuilder.forPort(50051).build();


        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Recieved shutdown");
            server.shutdown();
            System.out.println("Server Stopped");
        }));
        server.awaitTermination();

    }
}
