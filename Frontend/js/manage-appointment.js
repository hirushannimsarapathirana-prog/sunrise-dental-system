const searchAppointmentNumber = document.getElementById("searchAppointmentNumber");
const viewAppointmentButton = document.getElementById("viewAppointmentButton");
const appointmentListMessage = document.getElementById("appointmentListMessage");
const appointmentDetails = document.getElementById("appointmentDetails");
const updateAppointmentForm = document.getElementById("updateAppointmentForm");
const editAppointmentButton = document.getElementById("editAppointmentButton");
const updateAppointmentButton = document.getElementById("updateAppointmentButton");
const deleteAppointmentButton = document.getElementById("deleteAppointmentButton");

const editAppointmentNumber = document.getElementById("editAppointmentNumber");
const editPatientName = document.getElementById("editPatientName");
const editAddress = document.getElementById("editAddress");
const editContactNumber = document.getElementById("editContactNumber");
const editDentistName = document.getElementById("editDentistName");
const editTreatmentType = document.getElementById("editTreatmentType");
const editAppointmentDate = document.getElementById("editAppointmentDate");
const editAppointmentTime = document.getElementById("editAppointmentTime");

const API_URL = "http://localhost:8080/appointment";

viewAppointmentButton.addEventListener("click", async function() {
    const appointmentNumber = searchAppointmentNumber.value.trim();
    appointmentListMessage.textContent = "";
    appointmentDetails.style.display = "none";

    if (appointmentNumber === "") {
        appointmentListMessage.textContent = "Please enter an appointment number.";
        appointmentListMessage.style.color = "#dc2626";
        return;
    }

    try {
        viewAppointmentButton.disabled = true;
        viewAppointmentButton.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Loading...';

        const response = await fetch(API_URL + "?appointmentNumber=" + encodeURIComponent(appointmentNumber));
        const result = await response.json();

        console.log("GET response:", result);

        if (!response.ok) {
            appointmentListMessage.textContent = result.message || "Appointment not found.";
            appointmentListMessage.style.color = "#dc2626";
            return;
        }

        editAppointmentNumber.value = result.appointmentNumber || "";
        editPatientName.value = result.patientName || "";
        editAddress.value = result.address || "";
        editContactNumber.value = result.contactNumber || "";
        editDentistName.value = result.dentistName || "";
        editTreatmentType.value = result.treatmentType || "";
        editAppointmentDate.value = result.appointmentDate || "";
        editAppointmentTime.value = result.appointmentTime || "";

        appointmentDetails.style.display = "block";
        appointmentListMessage.textContent = "Appointment found successfully.";
        appointmentListMessage.style.color = "#0f766e";

        setEditMode(false);
    } catch (error) {
        console.error("GET error:", error);
        appointmentListMessage.textContent = "Unable to connect to server.";
        appointmentListMessage.style.color = "#dc2626";
    } finally {
        viewAppointmentButton.disabled = false;
        viewAppointmentButton.innerHTML = '<i class="fa-solid fa-magnifying-glass"></i> View';
    }
});

editAppointmentButton.addEventListener("click", function() {
    setEditMode(true);
});

function setEditMode(editing) {
    editPatientName.readOnly = !editing;
    editAddress.readOnly = !editing;
    editContactNumber.readOnly = !editing;
    editDentistName.disabled = !editing;
    editTreatmentType.disabled = !editing;
    editAppointmentDate.readOnly = !editing;
    editAppointmentTime.readOnly = !editing;

    if (editing) {
        editAppointmentButton.style.display = "none";
        updateAppointmentButton.style.display = "inline-flex";
        editPatientName.focus();
    } else {
        editAppointmentButton.style.display = "inline-flex";
        updateAppointmentButton.style.display = "none";
    }
}

updateAppointmentForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    const appointment = {
        appointmentNumber: editAppointmentNumber.value,
        patientName: editPatientName.value.trim(),
        address: editAddress.value.trim(),
        contactNumber: editContactNumber.value.trim(),
        dentistName: editDentistName.value,
        treatmentType: editTreatmentType.value,
        appointmentDate: editAppointmentDate.value,
        appointmentTime: editAppointmentTime.value
    };

    try {
        updateAppointmentButton.disabled = true;
        updateAppointmentButton.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Updating...';

        const response = await fetch(API_URL, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointment)
        });

        const result = await response.json();

        console.log("PUT response:", result);

        if (response.ok) {
            appointmentListMessage.textContent = result.message || "Appointment Updated Successfully.";
            appointmentListMessage.style.color = "#0f766e";
            setEditMode(false);
        } else {
            appointmentListMessage.textContent = result.message || "Appointment Update Failed.";
            appointmentListMessage.style.color = "#dc2626";
        }
    } catch (error) {
        console.error("PUT error:", error);
        appointmentListMessage.textContent = "Unable to connect to server.";
        appointmentListMessage.style.color = "#dc2626";
    } finally {
        updateAppointmentButton.disabled = false;
        updateAppointmentButton.innerHTML = '<i class="fa-solid fa-floppy-disk"></i> Update';
    }
});

deleteAppointmentButton.addEventListener("click", async function() {
    const appointmentNumber = editAppointmentNumber.value.trim();

    if (appointmentNumber === "") {
        appointmentListMessage.textContent = "No appointment selected.";
        appointmentListMessage.style.color = "#dc2626";
        return;
    }

    const confirmed = confirm("Are you sure you want to delete appointment " + appointmentNumber + "?");

    if (!confirmed) {
        return;
    }

    try {
        deleteAppointmentButton.disabled = true;
        deleteAppointmentButton.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Deleting...';

        const response = await fetch(API_URL + "?appointmentNumber=" + encodeURIComponent(appointmentNumber), {
            method: "DELETE"
        });

        const result = await response.json();

        console.log("DELETE response:", result);

        if (response.ok) {
            appointmentListMessage.textContent = result.message || "Appointment Deleted Successfully.";
            appointmentListMessage.style.color = "#0f766e";
            updateAppointmentForm.reset();
            appointmentDetails.style.display = "none";
            searchAppointmentNumber.value = "";
        } else {
            appointmentListMessage.textContent = result.message || "Appointment Delete Failed.";
            appointmentListMessage.style.color = "#dc2626";
        }
    } catch (error) {
        console.error("DELETE error:", error);
        appointmentListMessage.textContent = "Unable to connect to server.";
        appointmentListMessage.style.color = "#dc2626";
    } finally {
        deleteAppointmentButton.disabled = false;
        deleteAppointmentButton.innerHTML = '<i class="fa-solid fa-trash"></i> Delete';
    }
});

searchAppointmentNumber.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        viewAppointmentButton.click();
    }
});