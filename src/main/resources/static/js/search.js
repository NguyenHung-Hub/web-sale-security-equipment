function displayDanhMuc() {
    document.getElementById("danhMuc").style.display = 'block';
    document.getElementById("themDanhMuc").style.display = 'none';
    document.getElementById("anbotDanhMuc").style.display = 'block';
}

function hiddenDanhMuc() {
    document.getElementById("danhMuc").style.display = 'none';
    document.getElementById("themDanhMuc").style.display = 'block';
    document.getElementById("anbotDanhMuc").style.display = 'none';
}

function displayThuongHieu() {
    document.getElementById("thuongHieu").style.display = 'block';
    document.getElementById("themThuongHieu").style.display = 'none';
    document.getElementById("anBotThuongHieu").style.display = 'block';
}

function hiddenThuongHieu() {
    document.getElementById("thuongHieu").style.display = 'none';
    document.getElementById("themThuongHieu").style.display = 'block';
    document.getElementById("anBotThuongHieu").style.display = 'none';
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