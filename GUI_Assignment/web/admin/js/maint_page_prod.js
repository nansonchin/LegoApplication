function validateForm() {
    const id = document.getElementById("id");
    const name = document.getElementById("name");
    const description = document.getElementById("description");
    const price = document.getElementById("price");

    var isValid = true;
    if (isInputFieldEmpty(name)) {
        isValid = false;
    }
    if (isInputFieldEmpty(price)) {
        isValid = false;
    }
    if (!validateImageInput()) {
        isValid = false;
    }

    if (isValid) {
        if (!isValidPrice)
            isValid = false;
    }

    return isValid;
}
function isValidPrice() {
    return validateInput('price', /^\d+(\.\d{1,2})?$/, 'Please enter a valid price (e.g. 10.99)');
}

function isPriceKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode !== 46 && charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
function formatPrice(input) {
    let price = parseFloat(input.value);

    if (isNaN(price)) {
        price = 0;
    }

    const formattedPrice = price.toFixed(2);

    input.value = formattedPrice;
}
function cancel() {
    window.location.href = "prod_list.jsp";
}

const image_input = document.querySelector("#image_input");
var uploaded_image = "";

image_input.addEventListener("change", function () {
    validateImageInput();
    const file = this.files[0];
    const fileSizeInMB = file.size / (1024 * 1024);
    if (fileSizeInMB > 10) {
        alert("The selected image is too large (maximum size is 10MB).");
        this.value = null;
    } else {
        const img = document.querySelector("#img");
        img.style.display = "none";
        const reader = new FileReader();
        reader.addEventListener("load", () => {
            uploaded_image = reader.result;
            document.querySelector("#display_image").style.backgroundImage = `url(${uploaded_image})`;
        });
        reader.readAsDataURL(this.files[0]);
        
        const edited = document.querySelector("#imgEdited");
        edited.value = "true";
    }
});

function validateImageInput() {
    const input = document.querySelector("#image_input");
    const errorDiv = document.querySelector("#image_error");
    const errorDiv2 = document.querySelector("#display_image");
    const imgID = parseInt(document.getElementById("imgID").value);

    if (imgID === 0) {
        if (!input.value) {
            input.style.borderColor = "red";
            errorDiv2.style.borderColor = "red";
            errorDiv.innerHTML = "Upload an Image";
            return false;
        }
    }

    input.style.borderColor = "";
    errorDiv.innerHTML = "";
    errorDiv2.style.borderColor = "";
    return true;
}
