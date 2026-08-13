package com.sunrise.dental;

public class Bill {
    private String billNumber;
    private String appointmentNumber;
    private String treatmentType;
    private double treatmentCost;
    private double consultationFee;
    private double totalCost;

    public  Bill(){

    }

    public Bill(String billNumber, String appointmentNumber, String treatmentType, double treatmentCost, double consultationFee, double totalCost) {
        this.billNumber = billNumber;
        this.appointmentNumber = appointmentNumber;
        this.treatmentType = treatmentType;
        this.treatmentCost = treatmentCost;
        this.consultationFee = consultationFee;
        this.totalCost = totalCost;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
