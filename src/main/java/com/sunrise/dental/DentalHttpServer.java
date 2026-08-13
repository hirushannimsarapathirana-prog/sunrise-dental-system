package com.sunrise.dental;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sunrise.dental.Services.AppointmentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DentalHttpServer implements HttpHandler {
    AppointmentService appointmentService = new AppointmentService(new AppoimentDAO());
    Gson gson = new Gson();

    private void sendResponse(HttpExchange exchange, int statusCode, String response)
            throws IOException {

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/plain; charset=UTF-8"
        );

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
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

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Appointment appointment = gson.fromJson(stringBuilder.toString(), Appointment.class);

                boolean saved = appointmentService.createAppointment(appointment);

                String response = saved ? "Appointment Saved Successfully" : "Failed to Save Appointment";

                int statusCode = saved ? 200 : 500;

                sendResponse(exchange, statusCode, response);

            }

        } else if (method.equalsIgnoreCase("GET") && exchange.getRequestURI().getQuery() != null) {

            String query = exchange.getRequestURI().getQuery();
            String[] data = query.split("=");
            if (data.length < 2) {
                String response = "Appointment Number is required";
                sendResponse(exchange, 400, response);
                return;
            }
            String appointmentNumber = data[1];
            Appointment appointment = appointmentService.findAppointment(appointmentNumber);
            if (appointment == null) {
                String response = "Appointment Not Found";
                sendResponse(exchange, 404, response);
            }
            String response = gson.toJson(appointment);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream outputStream = exchange.getResponseBody()) {

                outputStream.write(response.getBytes());
            }


        } else if (method.equalsIgnoreCase("PUT")) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(),
                    StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }
            String reader = stringBuilder.toString();
            Appointment appointment = gson.fromJson(reader, Appointment.class);
            boolean response = appointmentService.updateAppointment(appointment);
            String result = response ? "Appointment Updated Successfully" : "Appointment Update Failed";
            int statusCode = response ? 200 : 500;

            sendResponse(exchange, statusCode, result);


        } else if ((method.equalsIgnoreCase("DELETE") && exchange.getRequestURI().getQuery() != null)) {

            String query = exchange.getRequestURI().getQuery();
            String[] data = query.split("=");
            String appointmentNumber = data[1];
            boolean deleted = appointmentService.deleteAppointment(appointmentNumber);
            String response = deleted ? "Appointment Deleted Successfully" : "Appointment Delete Failed";
            int statusCode = deleted ? 200 : 500;

            sendResponse(exchange, statusCode, response);

        }

    }
}
