document.addEventListener("DOMContentLoaded", function () {

    const currentAppointment =
        document.getElementById("currentAppointment");

    const nextAppointment =
        document.getElementById("nextAppointment");

    const doneButton =
        document.getElementById("doneButton");

    const username =
        sessionStorage.getItem("username");

    let appointments = [];

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
            currentAppointment.textContent =
                "User not logged in";

            nextAppointment.textContent =
                "Please login again";

            doneButton.disabled = true;

            return;
        }

        try {

            const response = await fetch(
                "http://localhost:8080/appointment?dentistName=" +
                encodeURIComponent(username)
            );

            if (!response.ok) {

                showNoAppointments();

                return;
            }

            const result =
                await response.json();

            if (!Array.isArray(result)) {

                showNoAppointments();

                return;
            }

            appointments =
                result.filter(function (appointment) {

                    return isDentistAppointment(
                        appointment
                    );

                });

            displayAppointments();

        } catch (error) {

            console.error(
                "Error loading appointments:",
                error
            );

            currentAppointment.textContent =
                "Unable to load appointment";

            nextAppointment.textContent =
                "Please make sure the server is running";

            doneButton.disabled = true;
        }
    }

    function displayAppointments() {

        if (appointments.length === 0) {

            showNoAppointments();

            return;
        }

        const current =
            appointments[0];

        const next =
            appointments[1];

        currentAppointment.textContent =
            current.appointmentNumber +
            " - " +
            current.patientName;

        doneButton.disabled = false;

        if (next) {

            nextAppointment.textContent =
                next.appointmentNumber +
                " - " +
                next.patientName;

        } else {

            nextAppointment.textContent =
                "No upcoming appointment";
        }

        doneButton.onclick = function () {

            const confirmed = confirm(
                "Have you completed checking this patient?"
            );

            if (!confirmed) {
                return;
            }

            appointments.shift();

            displayAppointments();
        };
    }

    function showNoAppointments() {

        currentAppointment.textContent =
            "No current appointment";

        nextAppointment.textContent =
            "No upcoming appointment";

        doneButton.disabled = true;
    }

    loadAppointments();

    setInterval(function () {

        loadAppointments();

    }, 5000);

});