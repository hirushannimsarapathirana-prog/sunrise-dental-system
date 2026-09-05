document.addEventListener("DOMContentLoaded", function () {

    const appointmentList =
        document.getElementById("appointmentList");

    const username =
        sessionStorage.getItem("username");

    function normalizeName(name) {
        return name
            .toLowerCase()
            .replace(/dr\.?/g, "")
            .replace(/\s+/g, "")
            .trim();
    }

    function isDentistAppointment(appointment) {

        if (!username || !appointment.dentistName) {
            return false;
        }

        const dentistName =
            normalizeName(appointment.dentistName);

        const loggedUser =
            normalizeName(username);

        return dentistName.includes(loggedUser);
    }

    async function loadAppointments() {

        if (!username) {
            showMessage(
                "User not logged in",
                "Please login again"
            );
            return;
        }

        try {

            const response = await fetch(
                "http://localhost:8080/appointment?dentistName=" +
                encodeURIComponent(username)
            );

            if (!response.ok) {
                showMessage(
                    "Unable to load appointments",
                    "Please make sure the server is running"
                );
                return;
            }

            const result = await response.json();

            if (!Array.isArray(result)) {
                showMessage(
                    "No Appointments",
                    "There are no appointments available"
                );
                return;
            }

            const appointments =
                result.filter(function (appointment) {
                    return isDentistAppointment(appointment);
                });

            displayAppointments(appointments);

        } catch (error) {

            console.error(
                "Error loading appointments:",
                error
            );

            showMessage(
                "Unable to load appointments",
                "Please make sure the server is running"
            );
        }
    }

    function displayAppointments(appointments) {

        if (appointments.length === 0) {
            showMessage(
                "No Appointments",
                "There are no appointments assigned to you"
            );
            return;
        }

        appointmentList.innerHTML = "";

        appointments.forEach(function (appointment) {

            const appointmentCard =
                document.createElement("div");

            appointmentCard.className =
                "quick-action appointment-card";

            appointmentCard.innerHTML = `
                <div class="quick-icon">
                    <i class="fa-solid fa-user"></i>
                </div>

                <div class="appointment-details">

                    <strong>
                        Appointment ${appointment.appointmentNumber}
                    </strong>

                    <span>
                        Patient: ${appointment.patientName}
                    </span>

                    <span>
                        Contact: ${appointment.contactNumber}
                    </span>

                    <span>
                        Dentist: ${appointment.dentistName}
                    </span>

                    <span>
                        Treatment: ${appointment.treatmentType}
                    </span>

                    <span>
                        Date: ${appointment.appointmentDate}
                    </span>

                    <span>
                        Time: ${appointment.appointmentTime}
                    </span>

                </div>

                <button
                    type="button"
                    class="done-button management-button edit-button">

                    <i class="fa-solid fa-check"></i>
                    Done

                </button>
            `;

            appointmentList.appendChild(
                appointmentCard
            );

            const doneButton =
                appointmentCard.querySelector(
                    ".done-button"
                );

            doneButton.addEventListener(
                "click",
                function () {

                    const confirmed = confirm(
                        "Have you completed checking this patient?"
                    );

                    if (!confirmed) {
                        return;
                    }

                    doneButton.disabled = true;

                    doneButton.innerHTML =
                        '<i class="fa-solid fa-check"></i> Completed';
                }
            );
        });
    }

    function showMessage(title, message) {

        appointmentList.innerHTML = `
            <div class="quick-action">

                <div class="quick-icon">
                    <i class="fa-solid fa-calendar-xmark"></i>
                </div>

                <div>
                    <strong>${title}</strong>
                    <span>${message}</span>
                </div>

            </div>
        `;
    }

    loadAppointments();

    setInterval(function () {
        loadAppointments();
    }, 5000);

});