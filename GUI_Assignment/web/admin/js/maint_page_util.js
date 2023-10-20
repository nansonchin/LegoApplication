

function resets() {
    const errorMessages = document.querySelectorAll('.error-message');
    const inputs = document.querySelectorAll('.error-border');
    errorMessages.forEach(errorMessage => {
        errorMessage.innerHTML = '';
    });
    inputs.forEach(input => {
        input.style.borderColor = "";
    });
}
function removeError() {
    const error = document.getElementById('error-message');
    if (error) {
        error.innerHTML = "";
    }
}

function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}

function validateInput(id, regexPattern, errorMessage) {
    const input = document.getElementById(id);
    const value = input.value.trim();
    const errorDiv = document.getElementById(`${id}_error`);

    if (!regexPattern.test(value)) {
        input.style.borderColor = "red";
        errorDiv.innerHTML = errorMessage;
        return false;
    }

    input.style.borderColor = "";
    errorDiv.innerHTML = "";
    return true;
}


function isInputFieldEmpty(inputField) {
    const errorDiv = document.getElementById(inputField.id + "_error");

    if (inputField.value.trim() === "") {
        const errorMessage = "fill in the blank";
        inputField.style.borderColor = "red";
        errorDiv.innerHTML = errorMessage;
        return true;
    } else {
        inputField.style.borderColor = "";
        errorDiv.innerHTML = "";
        return false;
    }
}
function deleteMember(id) {
    if (confirm('Are you sure?'))
        window.location.href = "member_list.jsp?id=" + id + "&delete=1";
}
function formatDate(dateString) {
    const year = parseInt(dateString.substring(0, 2)) + 2000;
    const month = dateString.substring(2, 4);
    const day = dateString.substring(4, 6);
    return `${year}-${month}-${day}`;
}
