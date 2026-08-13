package com.sunrise.dental;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/appointment", new DentalHttpServer());
            server.createContext("/bill", new BillHttpServer());
            server.createContext("/login",new UserHttpServer());

            server.start();

            System.out.println("Dental Server started on port 8080");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}