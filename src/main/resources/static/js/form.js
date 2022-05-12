$(document).ready(function() {
    $(".show-pass").on('click', function(event) {
        event.preventDefault();

        if($('.form-pass input').attr("type") == "text"){
            $('.form-pass input').attr('type', 'password');
            $('.form-pass i').addClass( "fa-eye-slash" );
            $('.form-pass i').removeClass( "fa-eye" );
        }else if($('.form-pass input').attr("type") == "password"){
            $('.form-pass input').attr('type', 'text');
            $('.form-pass i').removeClass( "fa-eye-slash" );
            $('.form-pass i').addClass( "fa-eye" );
        }
    });
});