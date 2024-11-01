document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login');

    // Function to check for cookies and auto-login if userId cookie exists
    function checkCookieAndAutoLogin() {
        const userIdCookie = getCookie("userId");
        console.log('User ID Cookie:', userIdCookie); // Log cookie value
        if (userIdCookie) {
            // Automatically log in if userId cookie is present
            fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userId: userIdCookie }),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Auto-login failed.');
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                // Redirect after a successful auto-login
                window.location.href = "/login/test.html";
            })
            .catch(error => {
                console.error('There was a problem with the auto-login operation:', error);
            });
        } else {
            console.log('No user ID cookie found.');
        }
    }

    // Function to get a specific cookie by name
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // Check cookie on page load
    checkCookieAndAutoLogin();

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Create FormData object from the form
        const formData = new FormData(loginForm);

        fetch('/login', {
            method: 'POST',
            body: formData, // Send form data directly
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json(); // Parse JSON if response is ok
        })
        .then((data) => {
            console.log(data);
            // Redirect after a successful login
            window.location.href = "/login/test.html";
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });
});
