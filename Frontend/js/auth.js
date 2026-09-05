const INACTIVITY_TIME = 5 * 60 * 1000;

let inactivityTimer;

function resetInactivityTimer() {

    clearTimeout(inactivityTimer);

    inactivityTimer = setTimeout(function () {

        sessionStorage.removeItem("jwtToken");

        alert("Your session has expired. Please login again.");

        window.location.href = "index.html";

    }, INACTIVITY_TIME);
}

document.addEventListener("mousemove", resetInactivityTimer);
document.addEventListener("keydown", resetInactivityTimer);
document.addEventListener("click", resetInactivityTimer);
document.addEventListener("scroll", resetInactivityTimer);

resetInactivityTimer();