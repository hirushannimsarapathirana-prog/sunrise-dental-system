package com.sunrise.dental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillDAO {
    public String getNextBillNumber() {

        String sql = "SELECT bill_number FROM bills ORDER BY bill_number DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String lastBillNumber =
                        resultSet.getString("bill_number");

                int number =
                        Integer.parseInt(lastBillNumber.substring(3));

                number++;

                return String.format("BIL%03d", number);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "BIL001";
    }
    public boolean createBill(Bill bill) {


        String sql = "INSERT INTO bills (bill_number, appointment_number, treatment_type," +
                " treatment_cost, consultation_fee, total_cost) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bill.getBillNumber());
            statement.setString(2, bill.getAppointmentNumber());
            statement.setString(3, bill.getTreatmentType());
            statement.setDouble(4, bill.getTreatmentCost());
            statement.setDouble(5, bill.getConsultationFee());
            statement.setDouble(6, bill.getTotalCost());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bill findByBillNumber(String billNumber) {

        String sql = "SELECT bill_number, appointment_number, treatment_type, treatment_cost," +
                " consultation_fee, total_cost FROM bills WHERE bill_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, billNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Bill(
                        resultSet.getString("bill_number"),
                        resultSet.getString("appointment_number"),
                        resultSet.getString("treatment_type"),
                        resultSet.getDouble("treatment_cost"),
                        resultSet.getDouble("consultation_fee"),
                        resultSet.getDouble("total_cost")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateBill(Bill bill) {

        String sql = "UPDATE bills SET appointment_number = ?, treatment_type = ?, treatment_cost = ?," +
                " consultation_fee = ?, total_cost = ? WHERE bill_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bill.getAppointmentNumber());
            statement.setString(2, bill.getTreatmentType());
            statement.setDouble(3, bill.getTreatmentCost());
            statement.setDouble(4, bill.getConsultationFee());
            statement.setDouble(5, bill.getTotalCost());
            statement.setString(6, bill.getBillNumber());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBill(String billNumber) {

        String sql = "DELETE FROM bills WHERE bill_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, billNumber);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
