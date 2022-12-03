package com.greetGrpc.greet.server;
import io.grpc.Server;
import io.grpc.ServerBuilder;


import java.io.IOException;
public class GreetServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server Start");

        Server server = ServerBuilder.forPort(50051).
                addService(new GreetServiceImpl()).
                build();


        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received shutdown");
            server.shutdown();
            System.out.println("Server Stopped");
        }));
        server.awaitTermination();

    }
}
