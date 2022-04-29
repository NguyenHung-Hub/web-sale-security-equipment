$(document).ready(function () {
    $(window).scroll(function () {
        const header = document.getElementById("header");
        const headerMobile = document.getElementById("header-mobile");
        const sticky = header.offsetTop;
        const stickyMobile = headerMobile.offsetTop;

        if (window.pageYOffset > sticky) {
            header.classList.add("sticky");
        } else {
            header.classList.remove("sticky");
        }

        if (window.pageYOffset > stickyMobile) {
            headerMobile.classList.add("sticky");
        } else {
            headerMobile.classList.remove("sticky");
        }
    });

    $('#search-input-mobile').focus(function () {
        $(".header-mobile .nav-menu-mobile .search-form-mobile").addClass("scroll-top")
        $(".over-suggest").css({"opacity": "1", "visibility": "visible"})
    })

    $(".over-suggest").click(function () {
        $(this).css({"opacity": "0", "visibility": "hidden"})
        $(".header-mobile .nav-menu-mobile .search-form-mobile").removeClass("scroll-top")
    });
});