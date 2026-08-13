package com.sunrise.dental.Services;

import com.sunrise.dental.AppoimentDAO;
import com.sunrise.dental.Appointment;

public class AppointmentService {
    private final AppoimentDAO appoimentDAO;

    public AppointmentService(AppoimentDAO appoimentDAO) {
        this.appoimentDAO = appoimentDAO;
    }

    public boolean createAppointment(Appointment appointment) {

        if (appointment == null) {

            return false;
        }

        if (appointment.getAppointmentNumber() == null || appointment.getAppointmentNumber().isBlank()) {

            return false;

        }

        if (appointment.getPatientName() == null || appointment.getPatientName().isBlank()) {

            return false;

        }
        if (appointment.getAddress() == null || appointment.getAddress().isBlank()) {
            return false;
        }

        if (appointment.getContactNumber() == null || appointment.getContactNumber().isBlank()) {
            return false;
        }

        if (appointment.getDentistName() == null || appointment.getDentistName().isBlank()) {
            return false;
        }

        if (appointment.getTreatmentType() == null || appointment.getTreatmentType().isBlank()) {
            return false;
        }

        if (appointment.getAppointmentDate() == null || appointment.getAppointmentDate().isBlank()) {
            return false;
        }

        if (appointment.getAppointmentTime() == null || appointment.getAppointmentTime().isBlank()) {
            return false;
        }
        return appoimentDAO.createAppointment(appointment);
    }

    public Appointment findAppointment(String appointmentNumber) {

        if (appointmentNumber == null || appointmentNumber.isBlank()) {

            return null;

        }
        return appoimentDAO.findByAppointmentNumber(appointmentNumber);
    }

    public boolean updateAppointment(Appointment appointment) {

            if (appointment == null) {
                return false;
            }

            if (appointment.getAppointmentNumber() == null ||
                    appointment.getAppointmentNumber().isBlank()) {
                return false;
            }

            if (appointment.getPatientName() == null ||
                    appointment.getPatientName().isBlank()) {
                return false;
            }

            if (appointment.getAddress() == null ||
                    appointment.getAddress().isBlank()) {
                return false;
            }

            if (appointment.getContactNumber() == null ||
                    appointment.getContactNumber().isBlank()) {
                return false;
            }

            if (appointment.getDentistName() == null ||
                    appointment.getDentistName().isBlank()) {
                return false;
            }

            if (appointment.getTreatmentType() == null ||
                    appointment.getTreatmentType().isBlank()) {
                return false;
            }

            if (appointment.getAppointmentDate() == null ||
                    appointment.getAppointmentDate().isBlank()) {
                return false;
            }

            if (appointment.getAppointmentTime() == null ||
                    appointment.getAppointmentTime().isBlank()) {
                return false;
            }

            return appoimentDAO.updateAppointment(appointment);

    }

    public boolean deleteAppointment(String appointmentNumber) {

        if (appointmentNumber == null || appointmentNumber.isBlank()) {

            return false;
        }
        return appoimentDAO.deleteAppointment(appointmentNumber);
    }

}
