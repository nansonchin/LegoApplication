window.onload = function () {
    const inputField = document.getElementById('search');
    const submitButton = document.getElementById('submit-button');

    inputField.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            submitButton.click();
        }

        // focus on the input field

    });

    document.addEventListener("keypress", function () {
        inputField.focus();
    });

    if (inputField.value !== '') {
        const end = inputField.value.length;
        inputField.setSelectionRange(end, end);
        inputField.focus();
    }
};
