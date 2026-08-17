package com.sunrise.dental;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BillHttpServer implements HttpHandler {

    BillService billService = new BillService(new BillDAO());
    Gson gson = new Gson();

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {

            outputStream.write(responseBytes);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");

        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {

            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line);
                }

                Bill bill = gson.fromJson(stringBuilder.toString(), Bill.class);

                boolean saved = billService.createBill(bill);

                String response = saved ? "Bill Saved Successfully" : "Failed to Save Bill";

                int statusCode = saved ? 200 : 500;

                sendResponse(exchange, statusCode, gson.toJson(response));
            }

        } else if (method.equalsIgnoreCase("GET")) {

            String query = exchange.getRequestURI().getQuery();

            if (query == null || query.isBlank()) {

                String billNumber = billService.getNextBillNumber();

                String response = gson.toJson(new BillNumberResponse("Next Bill Number", billNumber));

                sendResponse(exchange, 200, response);

                return;
            }

            String[] data = query.split("=");

            if (data.length < 2) {

                sendResponse(exchange, 400, gson.toJson(new BillNumberResponse("Bill Number is Required", null)));

                return;
            }

            String billNumber = data[1];

            Bill bill = billService.findBill(billNumber);

            if (bill == null) {

                sendResponse(exchange, 404, gson.toJson(new BillNumberResponse("Bill Not Found", null)));

                return;
            }

            sendResponse(exchange, 200, gson.toJson(bill));

        } else if (method.equalsIgnoreCase("PUT")) {

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line);
                }

                Bill bill = gson.fromJson(stringBuilder.toString(), Bill.class);

                boolean updated = billService.updateBill(bill);

                String response = updated ? "Bill Updated Successfully" : "Bill Update Failed";

                int statusCode = updated ? 200 : 500;

                sendResponse(exchange, statusCode, gson.toJson(response));
            }

        } else if (method.equalsIgnoreCase("DELETE") && exchange.getRequestURI().getQuery() != null) {

            String query = exchange.getRequestURI().getQuery();

            String[] data = query.split("=");

            if (data.length < 2) {

                sendResponse(exchange, 400, gson.toJson(new BillNumberResponse("Bill Number is Required", null)));

                return;
            }

            String billNumber = data[1];

            boolean deleted = billService.deleteBill(billNumber);

            String response = deleted ? "Bill Deleted Successfully" : "Bill Delete Failed";

            int statusCode = deleted ? 200 : 500;

            sendResponse(exchange, statusCode, gson.toJson(response));
        }
    }

    static class BillNumberResponse {

        private String message;

        private String billNumber;

        public BillNumberResponse(String message, String billNumber) {

            this.message = message;

            this.billNumber = billNumber;
        }
    }
}