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

        fetch('/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
        .then(response => {
            console.log(response)
            if (response.ok) {
                // Redirect after a successful account creation
                window.location.href = "/Animated-Login-and-Signup-Form-Leaf/index.html"; // Redirect to login page
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });
});
