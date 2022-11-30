package com.primeGrpc.prime.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class PrimeServer {
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(3000).build();
    }
}
