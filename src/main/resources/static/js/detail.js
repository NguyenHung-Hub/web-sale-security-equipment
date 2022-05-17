$('.backdrops').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    asNavFor: '.thumbnail-backdrops',
    autoplay: true,
    autoplaySpeed: 10000,
    speed: 300
})

$('.thumbnail-backdrops').slick({
    slidesToShow: 5,
    slidesToScroll: 1,
    arrows: false,
    asNavFor: '.backdrops',
    focusOnSelect: true,
    responsive: [
        {
            breakpoint: 1199,
            settings: {
                slidesToShow: 4,
                slidesToScroll: 4
            },
        },
    ],
});

let quantity= $('#spQuantity').attr('value');
function add() {
    let i = parseInt(document.getElementById("txtQuantity").value);
    i = i + 1;
    if(i>quantity)
        i=quantity
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
function validate(evt) {
    if (evt.keyCode != 8) {
        var theEvent = evt || window.event;
        var key = theEvent.keyCode || theEvent.which;
        key = String.fromCharCode(key);
        var regex = /[1-9][0-9]*/;
        if (!regex.test(key)) {
            theEvent.returnValue = false;
            if (theEvent.preventDefault)
                theEvent.preventDefault();
        }
    }
}
function testQuantity() {
    let i = $("#txtQuantity").val();
    let regex = /^[1-9][0-9]*$/i;
    if (i.trim() === "" || !regex.test(i)) {
        document.getElementById("txtQuantity").value = 1;
    }else if(i> quantity) {
        document.getElementById("txtQuantity").value = quantity;
        alert("Số lượng không phù hợp!");
    }
}

$(document).ready(function () {
    $("#txtQuantity").keypress(function(){
       validate(event);
    });

    $("#txtQuantity").blur(testQuantity);
    $('.collapsible').click(function () {
        if ($('#long-desc').hasClass('shadow-inset')) {
            $('.long-desc').css('height', 'auto');
            $('.long-desc').removeClass('shadow-inset');
            $(this).addClass('mt-3')
            $(this).html('Ẩn bớt <i class="fas fa-angle-up"></i>');
        } else {
            $('.long-desc').css('height', '200px');
            $('.long-desc').addClass('shadow-inset');
            $(this).html('Xem thêm <i class="fas fa-angle-down"></i>');
            $(this).removeClass('mt-3')

        }
    });
});

function checkDesHeight() {
    if ($('#long-desc').outerHeight() >= 300) {
        $('#btnDes').show();
    } else {
        $('#btnDes').hide();
    }
    console.log('chiều cao là: ' + $('#long-desc').height());
}

$(function () {
    $('a[data-toggle="tab"]').on('click', function (e) {
        console.log("da chay");
        switch (e.target.id) {
            case "btnTabDes": {
                $('#ThemThongTin').show();//không, nhờ cái này
                checkDesHeight();
                break;
            }
            case "danh-gia": {
                $('#ThemThongTin').hide();//không, nhờ cái này
                break;
            } case "information": {
                $('#ThemThongTin').hide();//không, nhờ cái này
                break;
            }

        }
    })
})



