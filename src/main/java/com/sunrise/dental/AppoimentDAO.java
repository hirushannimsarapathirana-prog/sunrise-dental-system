package com.sunrise.dental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppoimentDAO {

    public String getNextAppointmentNumber() {

        String sql = "SELECT MAX(CAST(SUBSTRING(appointment_number, 4) AS UNSIGNED)) AS max_number FROM appointments";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                int maxNumber = resultSet.getInt("max_number");

                if (resultSet.wasNull()) {
                    return "APT001";
                }

                return String.format("APT%03d", maxNumber + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "APT001";
    }

    public boolean createAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments (appointment_number, patient_name, address, contact_number, " + "dentist_name, treatment_type, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointment.getAppointmentNumber());
            statement.setString(2, appointment.getPatientName());
            statement.setString(3, appointment.getAddress());
            statement.setString(4, appointment.getContactNumber());
            statement.setString(5, appointment.getDentistName());
            statement.setString(6, appointment.getTreatmentType());
            statement.setString(7, appointment.getAppointmentDate());
            statement.setString(8, appointment.getAppointmentTime());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Appointment findByAppointmentNumber(String appointmentNumber) {

        String sql = "SELECT appointment_number, patient_name, address, contact_number, dentist_name, " + "treatment_type, appointment_date, appointment_time FROM appointments " + "WHERE appointment_number = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Appointment(resultSet.getString("appointment_number"), resultSet.getString("patient_name"), resultSet.getString("address"), resultSet.getString("contact_number"), resultSet.getString("dentist_name"), resultSet.getString("treatment_type"), resultSet.getString("appointment_date"), resultSet.getString("appointment_time"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateAppointment(Appointment appointment) {

        String sql = "UPDATE appointments SET patient_name = ?, address = ?, contact_number = ?, " + "dentist_name = ?, treatment_type = ?, appointment_date = ?, appointment_time = ? " + "WHERE appointment_number = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointment.getPatientName());
            statement.setString(2, appointment.getAddress());
            statement.setString(3, appointment.getContactNumber());
            statement.setString(4, appointment.getDentistName());
            statement.setString(5, appointment.getTreatmentType());
            statement.setString(6, appointment.getAppointmentDate());
            statement.setString(7, appointment.getAppointmentTime());
            statement.setString(8, appointment.getAppointmentNumber());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAppointment(String appointmentNumber) {

        String sql = "DELETE FROM appointments WHERE appointment_number = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}