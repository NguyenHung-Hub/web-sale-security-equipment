$(document).ready(function () {
    // $(window).scroll(function () {
    //     const header = document.getElementById("header");
    //     const sticky = header.offsetTop;
    //
    //     if (window.pageYOffset > sticky) {
    //         header.classList.add("sticky");
    //     } else {
    //         header.classList.remove("sticky");
    //     }
    // });

    $('#search-input-mobile').focus(function () {
        $(".header-mobile .nav-menu-mobile .search-form-mobile").addClass("scroll-top")
        $(".over-suggest").css({"display": "block"})
    });

    $(".over-suggest").click(function () {
        $(this).css({"display": "none"})
        $(".header-mobile .nav-menu-mobile .search-form-mobile").removeClass("scroll-top")
    });

    $('.btn-side-nav').click(function () {
        $('.sidebar-mobile').css({"transform": "translateX(0)"})
        $(".over").css({"display": "block"})
    })

    $(".over").click(function () {
        $(this).css({"display": "none"})
        $(".sidebar-mobile").css({"transform": "translateX(-345px)"})
        $(".sidebar-dropdown").removeClass("active")
    });

    $(".close-sidebar").click(function () {
        $(".over").css({"display": "none"})
        $(".sidebar-mobile").css({"transform": "translateX(-345px)"})
        $(".sidebar-dropdown").removeClass("active")
    });

    $(".sidebar-dropdown > a").click(function () {
        if ($(this).parent().hasClass("active")) {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                .parent()
                .removeClass("active");
        } else {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                .parent()
                .addClass("active");
        }
    })
});