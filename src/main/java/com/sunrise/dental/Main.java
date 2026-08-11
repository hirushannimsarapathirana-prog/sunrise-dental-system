package com.sunrise.dental;

public class Main {
    public static void main(String[] args) {
        AppoimentDAO dao = new AppoimentDAO();
        Appointment found = dao.findByAppointmentNumber("APT001");

        if (found != null) {

            System.out.println("Appointment found!");
            System.out.println("Patient: " + found.getPatientName());
            System.out.println("Dentist: " + found.getDentistName());
            System.out.println("Treatment: " + found.getTreatmentType());
            System.out.println("Date: " + found.getAppointmentDate());
            System.out.println("Time: " + found.getAppointmentTime());

        } else {

            System.out.println("Appointment not found.");
        }
    }
}
