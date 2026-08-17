const printBillData = JSON.parse(localStorage.getItem("printBillData"));

if (printBillData) {

    document.getElementById("printBillNumber").textContent =
        printBillData.billNumber;

    document.getElementById("printAppointmentNumber").textContent =
        printBillData.appointmentNumber;

    document.getElementById("printTreatmentType").textContent =
        printBillData.treatmentType;

    document.getElementById("printTreatmentCost").textContent =
        "Rs. " + Number(printBillData.treatmentCost).toFixed(2);

    document.getElementById("printConsultationFee").textContent =
        "Rs. " + Number(printBillData.consultationFee).toFixed(2);

    document.getElementById("printTotalCost").textContent =
        "Rs. " + Number(printBillData.totalCost).toFixed(2);

} else {

    alert("No bill data found.");

}