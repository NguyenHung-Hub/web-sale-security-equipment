// Glider Configuration
window.addEventListener('load', function () {
    new Glider(document.querySelector(".glider"), {
        slidesToShow: 2,
        slidesToScroll: 2,
        duration: 1,
        draggable: true,
        scrollLock: true,
        dragVelocity: 1,
        rewind: true,
        scrollLockDelay: 100,
        arrows: {
            prev: '.glider-prev',
            next: '.glider-next'
        },
        responsive: [
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 1,
                    duration: 1,
                    draggable: true,
                    scrollLock: true,
                    dragVelocity: 1,
                    rewind: true,
                    scrollLockDelay: 100,
                    arrows: {
                        prev: '.glider-prev',
                        next: '.glider-next'
                    },
                },
            },
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 1,
                    duration: 1,
                    draggable: true,
                    scrollLock: true,
                    rewind: true,
                    dragVelocity: 1,
                    scrollLockDelay: 100,
                    arrows: {
                        prev: '.glider-prev',
                        next: '.glider-next'
                    },
                },
            },
        ],
    });
});
