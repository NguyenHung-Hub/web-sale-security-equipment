var deliver = "";
var payment = "";

var tempt = 0;
var cost = 0;
var promotion = 0;
var discount = 0;

$(document).ready(function () {
    var selected = $(".subTotal-product");
    selected.each(function (){
        tempt += StringToMonney($(this).html());
    })

    $("#tempt").html(MonneyToString(tempt));


    promotion = StringToMonney($("#promotion").html());
    discount = StringToMonney($("#discount").html());

    initTotal(tempt,cost,promotion,discount);
})

function paymentClick(){
    var paymentList = $('input[name=payment]');
    var deliverList = $('input[name=delivery-form]');

    paymentList.click(function (x){
        payment = $(this).attr('id');
        showSelected()
    })

    deliverList.click(function (x){
        deliver = $(this).attr('id');
        deliverDateFrom = $("#deliveryDateFrom");
        deliverDateTo = $("#deliveryDateto");
        getdate = new Date();
        if(deliver === "fast"){
            initTotal(tempt,100000,promotion,discount)
            getdate.setDate(getdate.getDate() + 2);
            deliverDateFrom.html(getdate.toLocaleDateString("en-US"));
            getdate.setDate(getdate.getDate() + 3);
            deliverDateTo.html(getdate.toLocaleDateString("en-US"));
        }else if (deliver === "save"){
            initTotal(tempt,40000,promotion,discount)
            getdate.setDate(getdate.getDate() + 9);
            deliverDateFrom.html(getdate.toLocaleDateString("en-US"));
            getdate.setDate(getdate.getDate() + 14);
            deliverDateTo.html(getdate.toLocaleDateString("en-US"));
        }else{
            initTotal(tempt,200000,promotion,discount)
            getdate.setDate(getdate.getDate() + 0);
            deliverDateFrom.html(getdate.toLocaleDateString("en-US"));
            getdate.setDate(getdate.getDate() + 1);
            deliverDateTo.html(getdate.toLocaleDateString("en-US"));
        }
        showSelected()
    })
}
paymentClick();

function showSelected(){
    console.log(deliver + " " + payment);
}

function buy() {
    var StringSelected = "";
    $("#btn-buy").click(function (){
        var productRow = $(".line")
        productRow.each(function (){
            if(StringSelected === ""){
                StringSelected +=  $(this).attr("id")
            }else{
                StringSelected +="/"+ $(this).attr("id")
            }
        })
        if(deliver === ""){
            alert("Chọn phương thức giao hàng")
        }else if(payment === ""){
            alert("Chọn phương thức thanh toán")
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

function initTotal(tempt,cost,promotion,discount) {
    var total = parseFloat(tempt) + parseFloat(cost) - parseFloat(promotion) -parseFloat(discount);
    $("#cost").html(MonneyToString(cost));
    console.log(total);
    $("#total").html(MonneyToString(total));
}

function StringToMonney(string) {
    return parseFloat(string.split(" ")[0]);
}

function MonneyToString(Monney) {
    return Monney + " ₫";
}
