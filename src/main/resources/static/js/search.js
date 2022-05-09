function displayCategories() {
    document.getElementById("categories").style.display = 'block';
    document.getElementById("addCategories").style.display = 'none';
    document.getElementById("hiddenCategories").style.display = 'block';
}

function hiddenCategories() {
    document.getElementById("categories").style.display = 'none';
    document.getElementById("addCategories").style.display = 'block';
    document.getElementById("hiddenCategories").style.display = 'none';
}

function displayThuongHieu() {
    document.getElementById("brands").style.display = 'block';
    document.getElementById("addBrands").style.display = 'none';
    document.getElementById("hiddenBrands").style.display = 'block';
}

function hiddenThuongHieu() {
    document.getElementById("brands").style.display = 'none';
    document.getElementById("addBrands").style.display = 'block';
    document.getElementById("hiddenBrands").style.display = 'none';
}

function validate(evt) {
    if (evt.keyCode != 8) {
        var theEvent = evt || window.event;
        var key = theEvent.keyCode || theEvent.which;
        key = String.fromCharCode(key);
        var regex = /[0-9]|\./;
        if (!regex.test(key)) {
            theEvent.returnValue = false;
            if (theEvent.preventDefault)
                theEvent.preventDefault();
        }
    }
}