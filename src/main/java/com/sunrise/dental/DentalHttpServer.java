package com.sunrise.dental;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DentalHttpServer implements HttpHandler {

    AppointmentService appointmentService = new AppointmentService(new AppoimentDAO());

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

                Appointment appointment = gson.fromJson(stringBuilder.toString(), Appointment.class);

                boolean saved = appointmentService.createAppointment(appointment);

                if (saved) {

                    String response = gson.toJson(new AppointmentResponse("Appointment Saved Successfully", appointment.getAppointmentNumber()));

                    sendResponse(exchange, 200, response);

                } else {

                    String response = gson.toJson(new AppointmentResponse("Failed to Save Appointment", null));

                    sendResponse(exchange, 500, response);
                }
            }

        } else if (method.equalsIgnoreCase("GET")) {

            String query = exchange.getRequestURI().getQuery();

            if (query == null || query.isBlank()) {

                String appointmentNumber = appointmentService.getNextAppointmentNumber();

                String response = gson.toJson(new AppointmentResponse("Next Appointment Number", appointmentNumber));

                sendResponse(exchange, 200, response);

                return;
            }

            String[] data = query.split("=");

            if (data.length < 2) {

                sendResponse(exchange, 400, gson.toJson(new AppointmentResponse("Appointment Number is required", null)));

                return;
            }

            String appointmentNumber = data[1];

            Appointment appointment = appointmentService.findAppointment(appointmentNumber);

            if (appointment == null) {

                sendResponse(exchange, 404, gson.toJson(new AppointmentResponse("Appointment Not Found", null)));

                return;
            }

            String response = gson.toJson(appointment);

            sendResponse(exchange, 200, response);

        } else if (method.equalsIgnoreCase("PUT")) {

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line);
                }

                Appointment appointment = gson.fromJson(stringBuilder.toString(), Appointment.class);

                boolean updated = appointmentService.updateAppointment(appointment);

                String result = updated ? "Appointment Updated Successfully" : "Appointment Update Failed";

                int statusCode = updated ? 200 : 500;

                sendResponse(exchange, statusCode, gson.toJson(new AppointmentResponse(result, null)));
            }

        } else if (method.equalsIgnoreCase("DELETE") && exchange.getRequestURI().getQuery() != null) {

            String query = exchange.getRequestURI().getQuery();

            String[] data = query.split("=");

            if (data.length < 2) {

                sendResponse(exchange, 400, gson.toJson(new AppointmentResponse("Appointment Number is required", null)));

                return;
            }

            String appointmentNumber = data[1];

            boolean deleted = appointmentService.deleteAppointment(appointmentNumber);

            String response = deleted ? "Appointment Deleted Successfully" : "Appointment Delete Failed";

            int statusCode = deleted ? 200 : 500;

            sendResponse(exchange, statusCode, gson.toJson(new AppointmentResponse(response, null)));
        }
    }

    static class AppointmentResponse {

        private String message;

        private String appointmentNumber;

        public AppointmentResponse(String message, String appointmentNumber) {

            this.message = message;
            this.appointmentNumber = appointmentNumber;
        }
    }
}