$(document).ready(function () {
    $(".minus").on("click", function (e) {
        e.preventDefault();
        decreaseQuantity($(this))
    })

    $(".plus").on("click", function (e) {
        e.preventDefault();
        increaseQuantity($(this))
    })

    $(".delete").on("click", function (e) {
        e.preventDefault();
        result = confirm("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng ??")
        result ? deleteProduct($(this)) : null;
    })

    $("#saveUser").on("click", function (e) {
        e.preventDefault();
        saveUser();
    })

    initClickCheckbox()
})

var origin   = window.location.origin;

var select = $('.select:checkbox');
var selectItem = [];

function decreaseQuantity(link) {
    var productID = link.attr("productID");
    var qty = $("#quantity" + productID);

    newQty = parseInt(qty.val()) - 1;
    if (newQty > 0) {
        qty.val(newQty);
        updateQuantity(productID, newQty);
    }
}

function increaseQuantity(link) {
    var productID = link.attr("productID");
    var qty = $("#quantity" + productID);

    newQty = parseInt(qty.val()) + 1;
    if (newQty > 0) {
        qty.val(newQty);
        updateQuantity(productID, newQty);
    }
}

function updateQuantity(productID, newQty) {
    var url = origin +"/api/cart/update/" + productID + "/" + newQty;

    $.ajax({
        type: "POST",
        url: url
    }).done(function (subtotal){
        $("#subTotalPrice" + productID).html(MonneyToString(subtotal));
        var discount = StringToMonney($("#discount").html());
        console.log(discount);
        initTotal(discount);
    });

}

function deleteProduct(link) {
    var productID = link.attr("deleteId");
    var url = origin +"/api/cart/delete/" + productID;
    console.log(productID);
    $.ajax({
        type: "POST",
        url: url
    }).done(function (productID){
        $("#rowOf" + productID).remove();
        var discount = StringToMonney($("#discount").html());
        initTotal(discount);
    });
}

function saveUser(){
    firstName = $('#firstName').val();
    lastName = $('#lastName').val();
    phoneNumber = $('#phoneNumber').val();
    profile = $('#profile').val();
    url = origin +"/api/cart/updateUser/" + firstName+"/"+lastName+"/"+phoneNumber+"/"+profile;
    $.ajax({
        type: "POST",
        url: url
    }).done(function (newUser){
        if(newUser === 'ok'){
            $('#nameReturnRest').html(firstName + " " + lastName)
            $('#phomeReturnRest').html(phoneNumber)
            $('#addressReturnRest').html("Địa chỉ: " + profile)
        }
        var discount = StringToMonney($("#discount").html());
        initTotal(discount);
    });

    $('#exampleModal').modal('hide');
}

function initTotal(discount) {
    var TempPrice = 0;
    var select = $('.select:checkbox:checked');
    select.each(function () {
        var id = $(this).attr('id');
        TempPrice += StringToMonney($("#subTotalPrice" + id).html());
    })

    $("#TempPrice").html(MonneyToString(TempPrice));

    var TotalPrice = parseFloat(TempPrice) - parseFloat(discount);
    $("#TotalPrice").html(MonneyToString(TotalPrice));
}

initTotal(0);

function clickCheckbox() {
    select.each(function () {
        $(this).click(function () {
            var id = $(this).attr('id');
            if (selectItem.indexOf(id) < 0) {
                selectItem.push(id);
            } else {
                selectItem.splice(selectItem.indexOf(id), 1);
            }
            var discount = StringToMonney($("#discount").html());
            initTotal(discount)
        });
    })

}

clickCheckbox();

function initClickCheckbox(){
    var discount = $("#discount").html();
    select.each(function (){
        var id = $(this).attr('id');
        if ($(this).is(':checked')) {
            selectItem.push(id);
        }
        initTotal(discount)
    })
    console.log(selectItem)
}


function StringToMonney(string) {
    return parseFloat(string.split(" ")[0].replace(/\./g,""));
}

function MonneyToString(Monney) {
    return format2(Monney, " ₫");
}

function buy() {
    $("#buy").click(function (){
        StringSelected = ""
        selectItem.forEach(value => {
            if(StringSelected === ""){
                StringSelected +=  value
            }else{
                StringSelected +="/"+ value
            }

        })
        if(StringSelected === ""){
            alert("Chọn sản phẩm")
        }else{
            $("#sel").submit( function() {
                $("<input />").attr("type", "hidden")
                    .attr("name", "selected")
                    .attr("value",StringSelected)
                    .appendTo("#sel");
                return true;
            }).submit();
        }

    })
}

buy()

function format2(n, currency) {
    return n.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, '.') + currency ;
}