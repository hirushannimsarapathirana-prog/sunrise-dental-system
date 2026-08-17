const appointmentForm = document.getElementById("appointmentForm");
const appointmentNumberInput =
    document.getElementById("appointmentNumber");
const appointmentMessage =
    document.getElementById("appointmentMessage");

async function loadAppointmentNumber() {

    try {

        const response = await fetch(
            "http://localhost:8080/appointment",
            {
                method: "GET"
            }
        );

        const result = await response.json();

        if (response.ok) {

            appointmentNumberInput.value =
                result.appointmentNumber;

        } else {

            appointmentNumberInput.value = "Unable to generate";

            console.error(result.message);
        }

    } catch (error) {

        console.error("Appointment Number Error:", error);

        appointmentNumberInput.value =
            "Unable to connect";
    }
}


loadAppointmentNumber();


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


        if (response.ok) {

           
            appointmentNumberInput.value =
                result.appointmentNumber;


            appointmentMessage.textContent =
                result.message +
                " - " +
                result.appointmentNumber;

            appointmentMessage.style.color = "green";


            
            document.getElementById("patientName").value = "";
            document.getElementById("address").value = "";
            document.getElementById("contactNumber").value = "";
            document.getElementById("dentistName").value = "";
            document.getElementById("treatmentType").value = "";
            document.getElementById("appointmentDate").value = "";
            document.getElementById("appointmentTime").value = "";


            
            await loadAppointmentNumber();


        } else {

            appointmentMessage.textContent =
                result.message;

            appointmentMessage.style.color = "red";
        }


    } catch (error) {

        console.error("Appointment Error:", error);

        appointmentMessage.textContent =
            "Unable to connect to server.";

        appointmentMessage.style.color = "red";
    }

});