$(document).ready(function () {
    $(".delete").on("click", function (e) {
        e.preventDefault();
        result = confirm("Bạn có muốn hủy đơn hàng này ??")
        result ? deleteProduct($(this)) : null;
    })

    $(".complete").on("click", function (e) {
        e.preventDefault();
        result = confirm("Bạn bạn đã nhận đơn hàng này ??")
        result ? completeProduct($(this)) : null;
    })
})

function deleteProduct(link) {
    var orderId = link.attr("deleteId");
    var url = origin +"/api/cart/delete/order/" + orderId;
    console.log(orderId);
    $.ajax({
        type: "POST",
        url: url
    }).done(function (orderId){
        $("#rowOf" + orderId).remove();
        window.location.reload();
    });
}


function completeProduct(link) {
    var orderId = link.attr("completeId");
    var url = origin +"/api/cart/complete/order/" + orderId;
    console.log(orderId);
    $.ajax({
        type: "POST",
        url: url
    }).done(function (orderId){
        $("#rowOf" + orderId).remove();
        window.location.reload();
    });
}