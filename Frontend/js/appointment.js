const appointmentForm = document.getElementById("appointmentForm");

window.addEventListener("DOMContentLoaded", async function () {

    try {

        const response = await fetch(
            "http://localhost:8080/appointment"
        );

        const result = await response.json();

        console.log("GET response:", result);

        if (response.ok) {

            document.getElementById("appointmentNumber").value =
                result.appointmentNumber;

        }

    } catch (error) {

        console.error("Unable to get appointment number:", error);
    }
});


appointmentForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const patientName =
        document.getElementById("patientName").value;

    const address =
        document.getElementById("address").value;

    const contactNumber =
        document.getElementById("contactNumber").value;

    const dentistName =
        document.getElementById("dentistName").value;

    const treatmentType =
        document.getElementById("treatmentType").value;

    const appointmentDate =
        document.getElementById("appointmentDate").value;

    const appointmentTime =
        document.getElementById("appointmentTime").value;


    const appointment = {

        patientName: patientName,
        address: address,
        contactNumber: contactNumber,
        dentistName: dentistName,
        treatmentType: treatmentType,
        appointmentDate: appointmentDate,
        appointmentTime: appointmentTime
    };


    const appointmentMessage =
        document.getElementById("appointmentMessage");


    try {

        const response = await fetch(
            "http://localhost:8080/appointment",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(appointment)
            }
        );


        const result = await response.json();

        console.log("POST response:", result);


        if (response.ok) {

            document.getElementById("appointmentNumber").value =
                result.appointmentNumber;

            appointmentMessage.textContent =
                result.message;

            appointmentMessage.style.color = "green";

        } else {

            appointmentMessage.textContent =
                result.message;

            appointmentMessage.style.color = "red";
        }


    } catch (error) {

        appointmentMessage.textContent =
            "Unable to connect to server.";

        appointmentMessage.style.color = "red";
    }
});