package com.sunrise.dental.Services;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sunrise.dental.BillService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BillHttpServer implements HttpHandler {

    BillService billService = new BillService(new BillDAO());
    Gson gson = new Gson();

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {

        byte[] responseByte = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type",
                "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(statusCode, responseByte.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseByte);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");

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

                sendResponse(exchange, statusCode, response);

            }
        } else if ((method.equalsIgnoreCase("GET") && exchange.getRequestURI().getQuery() != null)) {

            String query = exchange.getRequestURI().getQuery();
            String[] data = query.split("=");
            if (data.length < 2) {
                String response = "Bill Number is Required ";
                sendResponse(exchange, 404, response);
                return;
            }
            String billNumber = data[1];
            Bill bill = billService.findBill(billNumber);

            if (bill == null) {
                String response = "Bill Not Found";
                sendResponse(exchange, 404, response);
                return;
            }
            String response = gson.toJson(bill);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream outputStream = exchange.getResponseBody()) {

                outputStream.write(response.getBytes());
            }

        } else if (method.equalsIgnoreCase("PUT")) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(),StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!= null){

                stringBuilder.append(line);
            }
            String reader = stringBuilder.toString();
            Bill bill = gson.fromJson(reader,Bill.class);
            boolean saved = billService.updateBill(bill);
            String result = saved ? "Bill Updated Successfully" : "Bill Update Failed";
            int statusCode = saved ? 200 : 500;
            sendResponse(exchange,statusCode,result);

        }
        else if ((method.equalsIgnoreCase("DELETE"))&& exchange.getRequestURI().getQuery() !=null){

            String query = exchange.getRequestURI().getQuery();
            String [] data = query.split("=");
            if (data.length < 2){
                String response = "Bill Number is Required ";
                sendResponse(exchange,404,response);
                return;
            }
            String billNumber = data[1];
            boolean deleted = billService.deleteBill(billNumber);
            String response = deleted ?"Bill Deleted Successfully" : "Bill Delete Failed";
            int statusCode = deleted ? 200 : 500;
            sendResponse(exchange,statusCode,response);
        }

    }

}
