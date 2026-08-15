const loginForm = document.getElementById("loginForm")
loginForm.addEventListener("submit", async function (event){

event.preventDefault();
const username = document.getElementById("username").value;
const password = document.getElementById("password").value;
const user ={
    username :username,
    password :password
};
try{
     const response = await fetch("http://localhost:8080/login", {
        method : "POST",
        headers: {"Content-Type": "application/json"},
        body : JSON.stringify(user)
        });

        const result = await response.text();
        const loginMessage = document.getElementById("loginMessage");
    if (response.ok) {
        loginMessage.textContent = result;
        loginMessage.style.color = "green";
        setTimeout(() => {
        window.location.href = "dashboard.html";
    }, 1000);
    
    }  else {
            loginMessage.textContent = result;
            loginMessage.style.color = "red";
        }

}
catch (error) {

    const loginMessage = document.getElementById("loginMessage");

    loginMessage.textContent = "Unable to connect to server.";
    loginMessage.style.color = "red";

}

});