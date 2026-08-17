const billForm = document.getElementById("billingForm");

const billNumberInput = document.getElementById("billNumber");
const appointmentNumberInput = document.getElementById("appointmentNumber");
const treatmentTypeInput = document.getElementById("treatmentType");
const treatmentCostInput = document.getElementById("treatmentCost");
const consultationFeeInput = document.getElementById("consultationFee");
const totalCostInput = document.getElementById("totalCost");
const printBillButton = document.getElementById("printBillButton");

const treatmentCosts = {
    "Dental Cleaning": 5000,
    "Teeth Whitening": 15000,
    "Dental Filling": 8000,
    "Root Canal Treatment": 25000,
    "Tooth Extraction": 7000,
    "Dental Crown": 30000,
    "Dental Bridge": 35000,
    "Dental Checkup": 3000,
    "Braces Consultation": 5000,
    "Dental Implant": 60000,
    "Gum Treatment": 12000,
    "Emergency Dental Treatment": 10000
};

async function loadNextBillNumber() {
    try {
        const response = await fetch("http://localhost:8080/bills");

        if (!response.ok) {
            throw new Error("Failed to get bill number");
        }

        const data = await response.json();

        billNumberInput.value = data.billNumber;

    } catch (error) {
        console.error(error);
        billNumberInput.value = "Unable to Generate";
    }
}

treatmentTypeInput.addEventListener("change", function() {

    const selectedTreatment = treatmentTypeInput.value;

    const treatmentCost = treatmentCosts[selectedTreatment];

    treatmentCostInput.value =
        treatmentCost !== undefined ? treatmentCost : "";

    calculateTotalCost();

});

function calculateTotalCost() {

    const treatmentCost =
        Number(treatmentCostInput.value) || 0;

    const consultationFee =
        Number(consultationFeeInput.value) || 0;

    const totalCost =
        treatmentCost + consultationFee;

    totalCostInput.value = totalCost;

}

consultationFeeInput.addEventListener(
    "input",
    calculateTotalCost
);

treatmentCostInput.addEventListener(
    "input",
    calculateTotalCost
);

billForm.addEventListener("submit", async function(event) {

    event.preventDefault();

    calculateTotalCost();

    const bill = {

        billNumber: billNumberInput.value,

        appointmentNumber:
            appointmentNumberInput.value,

        treatmentType:
            treatmentTypeInput.value,

        treatmentCost:
            Number(treatmentCostInput.value),

        consultationFee:
            Number(consultationFeeInput.value),

        totalCost:
            Number(totalCostInput.value)

    };

    if (
        !bill.billNumber ||
        bill.billNumber === "Unable to Generate"
    ) {

        alert("Bill Number is not available");

        return;

    }

    if (!bill.appointmentNumber) {

        alert("Please enter Appointment Number");

        return;

    }

    if (!bill.treatmentType) {

        alert("Please select Treatment Type");

        return;

    }

    if (bill.treatmentCost <= 0) {

        alert("Please enter a valid Treatment Cost");

        return;

    }

    try {

        const response = await fetch(
            "http://localhost:8080/bills",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(bill)
            }
        );

        const result =
            await response.text();

        if (response.ok) {

            localStorage.setItem(
                "printBillData",
                JSON.stringify(bill)
            );

            alert("Bill Saved Successfully");

            window.location.href =
                "billing-print.html";

        } else {

            alert(
                result || "Failed to Save Bill"
            );

        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server"
        );

    }

});

if (printBillButton) {

    printBillButton.addEventListener(
        "click",
        function() {

            calculateTotalCost();

            const billNumber =
                billNumberInput.value;

            const appointmentNumber =
                appointmentNumberInput.value;

            const treatmentType =
                treatmentTypeInput.value;

            const treatmentCost =
                treatmentCostInput.value;

            const consultationFee =
                consultationFeeInput.value;

            const totalCost =
                totalCostInput.value;

            if (
                !billNumber ||
                billNumber === "Unable to Generate"
            ) {

                alert(
                    "Bill Number is not available"
                );

                return;

            }

            if (!appointmentNumber) {

                alert(
                    "Please enter Appointment Number"
                );

                return;

            }

            if (!treatmentType) {

                alert(
                    "Please select Treatment Type"
                );

                return;

            }

            if (!totalCost || Number(totalCost) <= 0) {

                alert(
                    "Please complete the bill before printing."
                );

                return;

            }

            const printData = {

                billNumber:
                    billNumber,

                appointmentNumber:
                    appointmentNumber,

                treatmentType:
                    treatmentType,

                treatmentCost:
                    treatmentCost,

                consultationFee:
                    consultationFee,

                totalCost:
                    totalCost

            };

            localStorage.setItem(
                "printBillData",
                JSON.stringify(printData)
            );

            window.location.href =
                "billing-print.html";

        }
    );

}
const clearBillButton = document.getElementById("clearBillButton");

clearBillButton.addEventListener("click", function() {

    billForm.reset();

    treatmentCostInput.value = "";
    consultationFeeInput.value = "";
    totalCostInput.value = "";

    document.getElementById("billingMessage").textContent = "";

    loadNextBillNumber();

});

loadNextBillNumber();

calculateTotalCost();