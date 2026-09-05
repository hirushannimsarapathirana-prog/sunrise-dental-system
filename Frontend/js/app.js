const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const username =
        document.getElementById("username").value.trim();

    const password =
        document.getElementById("password").value;

    const loginMessage =
        document.getElementById("loginMessage");

    const user = {
        username: username,
        password: password
    };

    try {

        const response = await fetch(
            "http://localhost:8080/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            }
        );

        const responseText = await response.text();

        let result;

        try {
            result = JSON.parse(responseText);
        } catch (error) {
            result = {
                message: responseText
            };
        }

        if (response.ok) {

            loginMessage.textContent =
                result.message || "Login Successful";

            loginMessage.style.color = "green";

            if (result.role) {

                sessionStorage.setItem(
                    "username",
                    result.username || username
                );

                sessionStorage.setItem(
                    "role",
                    result.role
                );

                setTimeout(function () {

                    if (result.role === "ADMIN") {

                        window.location.href =
                            "dashboard.html";

                    } else if (result.role === "DENTIST") {

                        window.location.href =
                            "dentist-dashboard.html";

                    } else {

                        loginMessage.textContent =
                            "Invalid user role.";

                        loginMessage.style.color =
                            "red";

                        sessionStorage.clear();
                    }

                }, 1000);

            } else {

                loginMessage.textContent =
                    "Role was not returned by the server.";

                loginMessage.style.color =
                    "red";
            }

        } else {

            loginMessage.textContent =
                result.message || responseText;

            loginMessage.style.color =
                "red";
        }

    } catch (error) {

        console.error(
            "Login Error:",
            error
        );

        loginMessage.textContent =
            "Unable to connect to server.";

        loginMessage.style.color =
            "red";
    }

});
