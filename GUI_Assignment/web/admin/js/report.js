function handleMultipleSelect(fromId, toId, input) {
    const selectElement = document.getElementById(fromId);
    const selectedOptions = Array.from(selectElement.selectedOptions);
    const values = selectedOptions.map(option => option.value);
    const inputElement = document.getElementById(input);

    if (selectedOptions.length > 0) {

        inputElement.value = inputElement.value + ',' + values.join(',');
        selectedOptions.forEach(option => {
            option.selected = false;
            option.style.display = 'none';
            const newOption = document.createElement('option');
            newOption.value = option.value;
            newOption.text = option.text;
            const secondSelect = document.getElementById(toId);
            secondSelect.add(newOption);
            option.remove();
        });
    }
    const box = document.getElementById(input + "_select");
    box.style.backgroundColor = "";
    box.style.borderColor = "";
}

function revertMultipleSelect(fromId, toId, input) {
    const selectElement = document.getElementById(fromId);
    const inputElement = document.getElementById(input);
    const secondSelect = document.getElementById(toId);

    const selectedOption = Array.from(secondSelect.selectedOptions);

    selectedOption.forEach(option => {
        option.selected = false;
        option.style.display = 'none';
        const newOption = document.createElement('option');
        newOption.value = option.value;
        newOption.text = option.text;
        selectElement.add(newOption);

        const currentValues = inputElement.value.split(',');

        const newValues = currentValues.filter(value => value !== newOption.value);
        inputElement.value = newValues.join(',');
        option.remove();
    });
    const box = document.getElementById(input + "_select");
    box.style.backgroundColor = "";
    box.style.borderColor = "";
}

function handleAllSelect(fromId, toId, input) {
    const selectElement = document.getElementById(fromId);
    const options = Array.from(selectElement.options);
    const values = options.map(option => option.value);
    const inputElement = document.getElementById(input);

    if (selectElement.options.length > 0) {
        const newValue = ',' + values.join(',');
        const originalValue = inputElement.value || '';
        inputElement.value = originalValue + newValue;

        options.forEach(option => {
            option.style.display = 'none';
            const newOption = document.createElement('option');
            newOption.value = option.value;
            newOption.text = option.text;
            const secondSelect = document.getElementById(toId);
            secondSelect.add(newOption);
            option.remove();
        });
    }
    const box = document.getElementById(input + "_select");
    box.style.backgroundColor = "";
    box.style.borderColor = "";
}


function revertAllSelect(fromId, toId, input) {
    const selectElement = document.getElementById(fromId);
    const inputElement = document.getElementById(input);
    const secondSelect = document.getElementById(toId);

    const options = Array.from(secondSelect.options);

    options.forEach(option => {
        option.style.display = 'none';
        const newOption = document.createElement('option');
        newOption.value = option.value;
        newOption.text = option.text;
        selectElement.add(newOption);

        const currentValues = inputElement.value.split(',');

        const newValues = currentValues.filter(value => value !== newOption.value);
        inputElement.value = newValues.join(',');
        option.remove();
    });
    const box = document.getElementById(input + "_select");
    box.style.backgroundColor = "";
    box.style.borderColor = "";
}

function validateForm() {
    const name = document.getElementById("my-input-groupby");
    const phone_num = document.getElementById("my-input");

    var isValid = true;

    if (isInputFieldEmpty(name)) {
        isValid = false;
    }
    if (isInputFieldEmpty(phone_num)) {
        isValid = false;
    }

    return isValid;
}

function isInputFieldEmpty(inputField) {
    const selectDiv = document.getElementById(inputField.id + "_select");

    if (inputField.value.trim() === "") {
        const errorMessage = "This field need value.";
        selectDiv.style.borderColor = "red";
        selectDiv.style.backgroundColor = "rgb(220,53,69)";
        return true;
    } else {
        selectDiv.style.borderColor = "";
        selectDiv.style.backgroundColor = "";
        return false;
    }
}
