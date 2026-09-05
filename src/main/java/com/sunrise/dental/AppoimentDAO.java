package com.sunrise.dental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppoimentDAO {

    public String getNextAppointmentNumber() {

        String sql = """
                SELECT appointment_number
                FROM appointments
                ORDER BY CAST(SUBSTRING(appointment_number, 4) AS UNSIGNED) DESC
                LIMIT 1
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {

                String lastNumber = resultSet.getString("appointment_number");

                int number = Integer.parseInt(lastNumber.substring(3));

                return String.format("APT%03d", number + 1);
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return "APT001";
    }

    public boolean createAppointment(Appointment appointment) {

        String sql = """
                INSERT INTO appointments
                (
                    appointment_number,
                    patient_name,
                    address,
                    contact_number,
                    dentist_name,
                    treatment_type,
                    appointment_date,
                    appointment_time
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointment.getAppointmentNumber());

            preparedStatement.setString(2, appointment.getPatientName());

            preparedStatement.setString(3, appointment.getAddress());

            preparedStatement.setString(4, appointment.getContactNumber());

            preparedStatement.setString(5, appointment.getDentistName());

            preparedStatement.setString(6, appointment.getTreatmentType());

            preparedStatement.setString(7, appointment.getAppointmentDate());

            preparedStatement.setString(8, appointment.getAppointmentTime());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Appointment findByAppointmentNumber(String appointmentNumber) {

        String sql = """
                SELECT
                    appointment_number,
                    patient_name,
                    address,
                    contact_number,
                    dentist_name,
                    treatment_type,
                    appointment_date,
                    appointment_time
                FROM appointments
                WHERE appointment_number = ?
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointmentNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    Appointment appointment = new Appointment();

                    appointment.setAppointmentNumber(resultSet.getString("appointment_number"));

                    appointment.setPatientName(resultSet.getString("patient_name"));

                    appointment.setAddress(resultSet.getString("address"));

                    appointment.setContactNumber(resultSet.getString("contact_number"));

                    appointment.setDentistName(resultSet.getString("dentist_name"));

                    appointment.setTreatmentType(resultSet.getString("treatment_type"));

                    appointment.setAppointmentDate(resultSet.getString("appointment_date"));

                    appointment.setAppointmentTime(resultSet.getString("appointment_time"));

                    return appointment;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Appointment> findByDentistName(String dentistName) {

        List<Appointment> appointments = new ArrayList<>();

        String sql = """
                SELECT
                    appointment_number,
                    patient_name,
                    address,
                    contact_number,
                    dentist_name,
                    treatment_type,
                    appointment_date,
                    appointment_time
                FROM appointments
                WHERE LOWER(dentist_name)
                      LIKE CONCAT('%', LOWER(?), '%')
                ORDER BY appointment_date ASC,
                         appointment_time ASC
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, dentistName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Appointment appointment = new Appointment();

                    appointment.setAppointmentNumber(resultSet.getString("appointment_number"));

                    appointment.setPatientName(resultSet.getString("patient_name"));

                    appointment.setAddress(resultSet.getString("address"));

                    appointment.setContactNumber(resultSet.getString("contact_number"));

                    appointment.setDentistName(resultSet.getString("dentist_name"));

                    appointment.setTreatmentType(resultSet.getString("treatment_type"));

                    appointment.setAppointmentDate(resultSet.getString("appointment_date"));

                    appointment.setAppointmentTime(resultSet.getString("appointment_time"));

                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public boolean updateAppointment(Appointment appointment) {

        String sql = """
                UPDATE appointments
                SET
                    patient_name = ?,
                    address = ?,
                    contact_number = ?,
                    dentist_name = ?,
                    treatment_type = ?,
                    appointment_date = ?,
                    appointment_time = ?
                WHERE appointment_number = ?
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointment.getPatientName());

            preparedStatement.setString(2, appointment.getAddress());

            preparedStatement.setString(3, appointment.getContactNumber());

            preparedStatement.setString(4, appointment.getDentistName());

            preparedStatement.setString(5, appointment.getTreatmentType());

            preparedStatement.setString(6, appointment.getAppointmentDate());

            preparedStatement.setString(7, appointment.getAppointmentTime());

            preparedStatement.setString(8, appointment.getAppointmentNumber());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAppointment(String appointmentNumber) {

        String sql = """
                DELETE FROM appointments
                WHERE appointment_number = ?
                """;

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointmentNumber);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}