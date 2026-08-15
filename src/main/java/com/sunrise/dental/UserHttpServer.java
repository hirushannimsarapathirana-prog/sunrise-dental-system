package com.sunrise.dental;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UserHttpServer implements HttpHandler {

    private void sendResponse(HttpExchange exchange, int statusCode, String response)
            throws IOException {



        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        UserService userService = new UserService(new UserDAO());
        Gson gson = new Gson();

        String method = exchange.getRequestMethod();
        if (!method.equalsIgnoreCase("POST")){
            sendResponse(exchange,405,"Method Not Found");
            return;

        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (exchange.getRequestBody(),StandardCharsets.UTF_8))){

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine() )!= null){
                stringBuilder.append(line);

            }
            User user = gson.fromJson(stringBuilder.toString(),User.class);

            boolean login = userService.login(user);
             String response = login ? "Login Successful" : "Invalid Username or Password";
             int statusCode =  login ? 200 : 401;
             sendResponse(exchange,statusCode,response);


        }



    }
}
