$(document).ready(function() {
    $("#show-hide-password a").on('click', function(event) {
        event.preventDefault();
        if($('#show-hide-password input').attr("type") == "text"){
            $('#show-hide-password input').attr('type', 'password');
            $('#show-hide-password i').addClass( "fa-eye-slash" );
            $('#show-hide-password i').removeClass( "fa-eye" );
        }else if($('#show-hide-password input').attr("type") == "password"){
            $('#show-hide-password input').attr('type', 'text');
            $('#show-hide-password i').removeClass( "fa-eye-slash" );
            $('#show-hide-password i').addClass( "fa-eye" );
        }
    });
});