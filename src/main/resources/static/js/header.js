$(document).ready(function () {
    setTimeout(function () {
        $('.loader-wrapper').css({"height": "0"}).delay(350).queue(function () {
            $(this).css({"display": "none"})
        })
    }, 1500)

    $(window).scroll(function () {
        const header = document.getElementById("mt-header-mobile")
        const sticky = header.offsetTop;

        if (window.pageYOffset > sticky) {
            header.classList.remove("full")
        } else {
            header.classList.add("full")
        }
    });

    $('.mt-header-mobile .mt-navbar #form-box-search-mobile .box-search .mt-input').focus(function () {
        $('.mt-header-mobile').addClass("active")
        $(".over-suggest").css({"display": "block"})
    });

    $(".over-suggest").click(function () {
        $(this).css({"display": "none"})
        $(".mt-header-mobile").removeClass("active")
    });

    $('.mt-header-mobile .mt-navbar .btn-side-nav').click(function () {
        $('.mt-sidebar').css({"transform": "translateX(0)"})
        $(".over").css({"display": "block"})
    })

    $(".over").click(function () {
        $(this).css({"display": "none"})
        $(".mt-sidebar").css({"transform": "translateX(-345px)"})
        // $(".sidebar-dropdown").removeClass("active")
    });

    $(".close-sidebar").click(function () {
        $(".over").css({"display": "none"})
        $(".mt-sidebar").css({"transform": "translateX(-345px)"})
        // $(".sidebar-dropdown").removeClass("active")
    });

    // $(".sidebar-dropdown > a").click(function () {
    //     if ($(this).parent().hasClass("active")) {
    //         $(".sidebar-dropdown").removeClass("active");
    //         $(this)
    //             .parent()
    //             .removeClass("active");
    //     } else {
    //         $(".sidebar-dropdown").removeClass("active");
    //         $(this)
    //             .parent()
    //             .addClass("active");
    //     }
    // })
});