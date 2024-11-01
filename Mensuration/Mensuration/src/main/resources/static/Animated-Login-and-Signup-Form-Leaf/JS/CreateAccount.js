document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const formData = {
            username: this.username.value,
            password: this.password.value,
            height: this.height.value,
            weight: this.weight.value,
        };

        fetch('/useraccount/createAccount', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
        .then(response => {
            if (response.ok) {
                return response.text(); // Get the response text (the username)
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(username => {
            console.log('Username received from backend:', username);
            // Redirect after a successful account creation
            registerTokenWithAndroid(username);
            window.location.href = "/Animated-Login-and-Signup-Form-Leaf/index.html"; // Redirect to login page
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });

    function registerTokenWithAndroid(username) {
        // Call the Android interface method
        if (window.Android) {
            window.Android.registerToken(username);
        } else {
            console.log("Android interface not found.");
        }
    }
});
