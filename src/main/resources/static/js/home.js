$(document).ready(function () {
    const width = $(window).width();

    if (width <= 1199){
        $('.box-product').addClass('scroll');
        $('.block-list-cate').addClass('scroll')
    }
    else{
        $('.box-product').removeClass('scroll');
        $('.block-list-cate').removeClass('scroll');
    }

    $('.gallery-top').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true,
        prevArrow: "<div tabindex='0' role='button' aria-label='Previous' class='slick-prev'><i class='fas fa-chevron-left'></i></div>",
        nextArrow: "<div tabindex='0' role='button' aria-label='Next' class='slick-next'><i class='fas fa-chevron-right'></i></i></div>",
        asNavFor: '.gallery-thumbs',
        autoplay: true,
        autoplaySpeed: 2000,
        speed: 300
    })

    $('.gallery-thumbs').slick({
        slidesToShow: 5,
        slidesToScroll: 1,
        arrows: false,
        asNavFor: '.gallery-top',
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

    $('.sliding-home').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
        speed: 300,
        dots: true
    })

    $('.block-hot-sale .box-product .list-product').slick({
        infinite: false,
        slidesToShow: 5,
        slidesToScroll: 5,
        arrows: true,
        prevArrow: "<div tabindex='0' role='button' aria-label='Previous' class='slick-prev'><i class='fas fa-chevron-left'></i></div>",
        nextArrow: "<div tabindex='0' role='button' aria-label='Next' class='slick-next'><i class='fas fa-chevron-right'></i></i></div>",
        speed: 300,
        responsive: [
            {
                breakpoint: 1199,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 4
                },
            },
            {
                breakpoint: 991,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3
                },
            },
            {
                breakpoint: 767,
                settings: {
                    slidesToShow: 2.5,
                    slidesToScroll: 2.5,
                    arrows: false
                },
            },
            {
                breakpoint: 441,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2,
                    arrows: false
                },
            }
        ],
    });

    $(window).resize(function () {
        const width = $(window).width();

        if (width <= 1199){
            $('.box-product').addClass('scroll');
            $('.block-list-cate').addClass('scroll')
        }
        else{
            $('.box-product').removeClass('scroll');
            $('.block-list-cate').removeClass('scroll');
        }
    })
})



