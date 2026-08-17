package com.sunrise.dental;

public class BillService {

    private final BillDAO billDAO;

    public BillService(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    public boolean createBill(Bill bill) {

        if (bill == null) {
            return false;
        }

        if (bill.getBillNumber() == null || bill.getBillNumber().isBlank()) {
            return false;
        }

        if (bill.getAppointmentNumber() == null || bill.getAppointmentNumber().isBlank()) {
            return false;
        }

        if (bill.getTreatmentType() == null || bill.getTreatmentType().isBlank()) {
            return false;
        }

        if (bill.getTreatmentCost() < 0) {
            return false;
        }

        if (bill.getConsultationFee() < 0) {
            return false;
        }

        if (bill.getTotalCost() < 0) {
            return false;
        }

        return billDAO.createBill(bill);
    }

    public Bill findBill(String billNumber) {

        if (billNumber == null || billNumber.isBlank()) {
            return null;
        }

        return billDAO.findByBillNumber(billNumber);
    }

    public String getNextBillNumber() {

        return billDAO.getNextBillNumber();
    }

    public boolean updateBill(Bill bill) {

        if (bill == null) {
            return false;
        }

        if (bill.getBillNumber() == null || bill.getBillNumber().isBlank()) {
            return false;
        }

        if (bill.getAppointmentNumber() == null || bill.getAppointmentNumber().isBlank()) {
            return false;
        }

        if (bill.getTreatmentType() == null || bill.getTreatmentType().isBlank()) {
            return false;
        }

        if (bill.getTreatmentCost() < 0) {
            return false;
        }

        if (bill.getConsultationFee() < 0) {
            return false;
        }

        if (bill.getTotalCost() < 0) {
            return false;
        }

        return billDAO.updateBill(bill);
    }

    public boolean deleteBill(String billNumber) {

        if (billNumber == null || billNumber.isBlank()) {
            return false;
        }

        return billDAO.deleteBill(billNumber);
    }
}