package com.sunrise.dental;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/appointment",new DentalHttpServer());
            server.start();
            System.out.println("Dental Server started on port 8080");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}