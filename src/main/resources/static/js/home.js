// Glider Configuration

new Glider(document.querySelector(".glider"), {
    slidesToShow: 2,
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
            breakpoint: 1200,
            settings: {
                slidesToShow: 4,
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
            breakpoint: 992,
            settings: {
                slidesToShow: 3,
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
    ],
});

var mc = new Hammer(document.getElementById("carouselId"));

var pointerXCoord = 0;
var imageLeftCord = 0;
var carouselWidth = $("#carouselId .carousel-inner").width();
var lastMove = "";

$("#carouselId").carousel(
    {
        //interval: false
    }
);

function initialize() {
    $("#carouselId").carousel("cycle");
    carouselWidth = $("#carouselId .carousel-inner").width();
    imageLeftCord = 0;
    pointerXCoord = 0;
    lastMove = "";
}

function snapLeft() {
    $("#carouselId .carousel-item").css({ transition: "", transform: "" });
    $("#carouselId .carousel-item").removeClass("carousel-item-prev");
    $("#carouselId .carousel-item").removeClass("carousel-item-next");
    $("#carouselId").carousel("prev");
    setTimeout(function() {
        initialize();
    }, 600);
}

function snapRight() {
    $("#carouselId .carousel-item").css({ transition: "", transform: "" });
    $("#carouselId .carousel-item").removeClass("carousel-item-prev");
    $("#carouselId .carousel-item").removeClass("carousel-item-next");
    $("#carouselId").carousel("next");
    setTimeout(function() {
        initialize();
    }, 600);
}

$(".carousel-control-prev").click(function() {
    snapLeft();
});

$(".carousel-control-next").click(function() {
    snapRight();
});

mc.on("panstart panend panleft panright", function(ev) {
    $("#carouselId").carousel("pause");

    var prev = $("#carouselId .carousel-item.active").prev();
    if (prev[0] === undefined) {
        prev = $("#carouselId .carousel-inner").children().last();
    }
    prev.addClass("carousel-item-prev");

    var next = $("#carouselId .carousel-item.active").next();
    if (next[0] === undefined) {
        next = $("#carouselId .carousel-inner").children().first();
    }
    next.addClass("carousel-item-next");

    if (ev.type === "panstart") {
        pointerXCoord = ev.pointers[0].pageX;
        return 0;
    }

    if (pointerXCoord !== ev.pointers[0].pageX) {
        lastMove = ev.type;

        var diff = ev.pointers[0].pageX - pointerXCoord;
        $("#carouselId .carousel-item.active").css({
            transition: "initial",
            transform: "translate3d(" + (imageLeftCord + diff) + "px, 0, 0)"
        });
        $("#carouselId .carousel-item.carousel-item-next").css({
            transition: "initial",
            transform:
                "translate3d(" + (imageLeftCord + diff + carouselWidth) + "px, 0, 0)"
        });
        $("#carouselId .carousel-item.carousel-item-prev").css({
            transition: "initial",
            transform:
                "translate3d(" + (imageLeftCord + diff - carouselWidth) + "px, 0, 0)"
        });

        imageLeftCord += diff;
        pointerXCoord = ev.pointers[0].pageX;
    }

    if (ev.type === "panend") {
        if (imageLeftCord > carouselWidth / 2) {
            if (lastMove === "panright") {
                snapLeft();
                return 0;
            }
        }

        if (imageLeftCord < -(carouselWidth / 2)) {
            if (lastMove === "panleft") {
                snapRight();
                return 0;
            }
        }

        $("#carouselId .carousel-item").css({ transition: "", transform: "" });
        initialize();
    }
});


