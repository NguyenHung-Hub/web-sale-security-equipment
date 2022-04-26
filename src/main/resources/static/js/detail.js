function add() {
    let i = parseInt(document.getElementById("txtQuantity").value);

    i = i + 1;
    document.getElementById("txtQuantity").value = i;
}

function sub() {
    let i = parseInt(document.getElementById("txtQuantity").value);
    if (i <= 1)
        i = 1;
    else
        i = i - 1;
    document.getElementById("txtQuantity").value = i;
}
$(document).ready(function() {
    function testQuantity() {
        let i = $("#txtQuantity").val();
        let regex = /^[1-9][0-9]*$/i;
        if (i.trim() === "" || !regex.test(i)) {
            document.getElementById("txtQuantity").value = 1;
        }
    }
    $("#txtQuantity").blur(testQuantity);
});