$('.filtering').slick({
    slidesToShow: 4,
    slidesToScroll: 4,
    arrows: true,
    prevArrow: "<span role='none' aria-label='Previous' class='slick-prev'></span>",
    nextArrow: "<span role='none' aria-label='Next' class='slick-next'></span>",
    speed: 300,
    responsive: [
        {
            breakpoint: 991,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 3,
            },
        },
        {
            breakpoint: 767,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
            },
        }
    ],
});