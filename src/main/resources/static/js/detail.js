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
    $('.collapsible').click(function (){
        if($('#long-desc').hasClass('shadow-inset')){
            $('.long-desc').css('height','auto');
            $('.long-desc').removeClass('shadow-inset');
            $(this).addClass('mt-3')
            $(this).html('Ẩn bớt <i class="fas fa-angle-up"></i>');
        }else {
            $('.long-desc').css('height','200px');
            $('.long-desc').addClass('shadow-inset');
            $(this).html('Xem thêm <i class="fas fa-angle-down"></i>');
            $(this).removeClass('mt-3')

        }
    });
});

