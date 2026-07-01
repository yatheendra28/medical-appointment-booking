// Auto-hide alerts after 5 seconds
document.addEventListener('DOMContentLoaded', function() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.remove();
            }, 500);
        }, 5000);
    });
});

// Form validation for appointment booking
function validateAppointmentForm() {
    const dateTime = document.querySelector('input[name="appointmentDateTime"]');
    if (dateTime) {
        const selectedDate = new Date(dateTime.value);
        const now = new Date();
        if (selectedDate < now) {
            alert('Please select a future date and time for your appointment.');
            return false;
        }
    }
    return true;
}
