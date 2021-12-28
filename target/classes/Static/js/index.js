///////////////////////
var itemList = [];
var cartCount = 0;
///////////////////////

$(document).ready(function () {

    $('#cart-btn').prop("disable", true);

    console.log($('#cart-btn'));
    console.log($('#cart-btn').prop("disable") == false)
    //=======
    //Slider-
    //=======
    slider();

    //==========
    //Carousels-
    //==========
    publicityCarousel();
    carousel1();
    carousel2();
    carousel3();
    carousel4();

    //======================================
    //Ajax de carga de sección de productos-
    //======================================

    $.get("/carousel?petType=Dog", function (data) {
        $('#dog-section').find('.carousel__list').html(data);
        loadModalProduct();
    });

    $.get("/carousel?petType=Cat", function (data) {
        $('#cat-section').find('.carousel__list').html(data);
        loadModalProduct();
    });

    $.get("/carousel?petType=Rodent", function (data) {
        $('#rodent-section').find('.carousel__list').html(data);
        loadModalProduct();
    });

    $.get("/carousel?petType=Fish", function (data) {
        $('#fish-section').find('.carousel__list').html(data);
        loadModalProduct();
    });

    $('.shopping-cart_button').click(function () {
        if ($('#cart-btn').prop("disable") == false) {
            $.get("/shoppingCart?products=" + itemList + "&selector=1", function (data) {
                $('#shoppingCart').find('.modal-body').html(data);
                deleteCartButton();
            });
        }

    });

    //===============================
    //Ajax Post del modal de registro
    //===============================
    $("#user-sing-up").submit(function (e) {
        e.preventDefault()
        singUp();
    })

    function singUp() {
        var formData = {
            name: $('#name').val(),
            dni: $('#dni').val(),
            mail: $('#mail').val(),
            phone: $('#phone').val(),
            address: $('#address').val(),
            password: $('#password').val(),
            role: $('#role').val(),
            active: $('#active').val()
        }

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            contentType: "aplication/json",
            url: "/saveClient",
            data: JSON.stringify(formData),
            dataType: "JSON",
            success: function (result) {
                console.log(result);
                if (result !== null) {
                    alert("Succesfully registered");
                } else {
                    alert("Error in register");
                }
            },
            error: function (e) {
                $('#error1').html(e.responseJSON.message);
                console.log(e);
            }
        })
    }

    $("#admin-sign-up").submit(function (e) {
        e.preventDefault()
        adminSignUp();
    })

    function adminSignUp() {
        var formData = {
            name: $('#name').val(),
            dni: $('#dni').val(),
            mail: $('#mail').val(),
            phone: $('#phone').val(),
            password: $('#password').val(),
            role: $('#role').val(),
            active: $('#active').val()
        }

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            contentType: "aplication/json",
            url: "/saveAdmin",
            data: JSON.stringify(formData),
            dataType: "JSON",
            success: function (result) {
                console.log(result);
                if (result !== null) {

                } else {
                    alert("Error in register");
                }
            },
            error: function (e) {
                $('#error2').html(e.responseJSON.message);
                console.log(e);
            }
        })
    }


    //===============================
    //Ajax Post del modal de registro
    //===============================
    $("#user-login").submit(function (e) {
        e.preventDefault()
        login();
    })

    function login() {
        var formData = {
            username: $('#username').val(),
            password: $('#password1').val()
        }

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            contentType: "aplication/json",
            url: "/login",
            data: JSON.stringify(formData),
            dataType: "JSON",
            success: function (result) {
                if (result.status == true) {
                    location.reload()
                } else {
                    console.log(result);
                    $('#error1').html(result.error);
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

    $('.filter_img').click(function () {
        var value = $(this).attr('value')

        if (!petTypes.includes(value)) {
            $(this).addClass("filter_img_highlighted");
        } else {
            $(this).removeClass("filter_img_highlighted");
        }
    })

});

$(document).on("click", ".add-to-cart_button", function () {
    

    var itemId = $(this).attr('productId');

    itemList.push(itemId);

    cartCount = cartCount + 1;

    if (cartCount !== 0) {
        $('#cart-btn').prop("disable", false);
    }
    
    console.log($('#cart-btn').prop("disable") == false)

    $('#cart-btn').val(cartCount);
    document.getElementById('cart-count').innerHTML = cartCount;
});

function loadModalProduct() {
    $('.show-product-button').click(function () {
        var productId = $(this).attr('productId');

        $.get("/modalProduct?productId=" + productId, function (data) {
            $('#modalProduct').find('.modal-body').html(data);
        });
    });
};


function deleteCartButton() {
    $('.delete-item_button').click(function () {

        removeItemFromArr(itemList, $(this).attr('productId'));

        if (cartCount > 0) {
            cartCount = cartCount - 1;
        }

        $('#cart-btn').val(cartCount);
        document.getElementById('cart-count').innerHTML = cartCount;

    });
};

function removeItemFromArr(arr, item) {
    var i = arr.indexOf(item);

    if (i !== -1) {
        arr.splice(i, 1);
    }
};

function slider() {
    var slider = document.querySelector("#slider");
    let sliderSection = document.querySelectorAll(".slider__section");
    let sliderSectionFirst = document.querySelectorAll(".slider__section")[0];
    let sliderSectionLast = sliderSection[sliderSection.length - 1];

    var btnLeft = document.querySelector("#btn-left");
    var btnRight = document.querySelector("#btn-right");

    slider.insertAdjacentElement('afterbegin', sliderSectionLast);
    slider.insertAdjacentElement('beforeend', sliderSectionFirst);

    btnRight.addEventListener('click', function () {
        Next();
    });

    function Next() {
        let sliderSectionFirst = document.querySelectorAll(".slider__section")[0];

        slider.style.marginLeft = "-200%";
        slider.style.transition = "all 0.5s";

        setTimeout(function () {
            slider.style.transition = "none";
            slider.insertAdjacentElement('beforeend', sliderSectionFirst);
            slider.style.marginLeft = "-100%";

        }, 500);
    }

    btnLeft.addEventListener('click', function () {
        Back();
    });

    function Back() {
        let sliderSection = document.querySelectorAll(".slider__section");
        let sliderSectionLast = sliderSection[sliderSection.length - 1];
        console.log(sliderSectionLast);

        slider.style.marginLeft = "0%";
        slider.style.transition = "all 0.5s";
        setTimeout(function () {
            slider.style.transition = "none";
            slider.insertAdjacentElement('afterbegin', sliderSectionLast);
            slider.style.marginLeft = "-100%";

        }, 500);
    }




    // setInterval(function () {
    //     Next();
    // }, 5000);

};


function publicityCarousel() {
    var slider = document.querySelector("#publicity-section");
    let sliderSection = document.querySelectorAll(".publicity-sector");
    let sliderSectionFirst = document.querySelectorAll(".publicity-sector")[0];
    let sliderSectionLast = sliderSection[sliderSection.length - 1];

    var btnLeft = document.querySelector("#left_pub_btn");
    var btnRight = document.querySelector("#right_pub_btn");

    slider.insertAdjacentElement('afterbegin', sliderSectionLast);
    slider.insertAdjacentElement('beforeend', sliderSectionFirst);

    btnRight.addEventListener('click', function () {
        Next();
    });

    function Next() {
        let sliderSectionFirst = document.querySelectorAll(".publicity-sector")[0];

        slider.style.marginLeft = "-200%";
        slider.style.transition = "all 0.5s";

        setTimeout(function () {
            slider.style.transition = "none";
            slider.insertAdjacentElement('beforeend', sliderSectionFirst);
            slider.style.marginLeft = "-100%";

        }, 500);
    }

    btnLeft.addEventListener('click', function () {
        Back();
    });

    function Back() {
        let sliderSection = document.querySelectorAll(".publicity-sector");
        let sliderSectionLast = sliderSection[sliderSection.length - 1];
        console.log(sliderSectionLast);

        slider.style.marginLeft = "0%";
        slider.style.transition = "all 0.5s";
        setTimeout(function () {
            slider.style.transition = "none";
            slider.insertAdjacentElement('afterbegin', sliderSectionLast);
            slider.style.marginLeft = "-100%";

        }, 500);
    }

    // setInterval(function () {
    //     Next();
    // }, 5000);
};

function carousel1() {
    var row = document.getElementById("carousel__list-1");
    // var car_product = document.querySelectorAll('.carousel__element');

    var arrow_last = document.getElementById("last__carousel-1");
    var arrow_next = document.getElementById("next__carousel-1");

    arrow_next.addEventListener('click', () => {
        row.scrollLeft += row.offsetWidth;
    })

    arrow_last.addEventListener('click', () => {
        row.scrollLeft -= row.offsetWidth;
    })

    // var car_quantity = car_product.length / 4;
}

function carousel2() {
    var row = document.getElementById("carousel__list-2");
    // var car_product = document.querySelector('.carousel__element');

    var arrow_last = document.getElementById("last__carousel-2");
    var arrow_next = document.getElementById("next__carousel-2");

    arrow_next.addEventListener('click', () => {
        row.scrollLeft += row.offsetWidth;
    })

    arrow_last.addEventListener('click', () => {
        row.scrollLeft -= row.offsetWidth;
    })
}

function carousel3() {
    var row = document.getElementById("carousel__list-3");
    // var car_product = document.querySelector('.carousel__element');

    var arrow_last = document.getElementById("last__carousel-3");
    var arrow_next = document.getElementById("next__carousel-3");

    arrow_next.addEventListener('click', () => {
        row.scrollLeft += row.offsetWidth;
    })

    arrow_last.addEventListener('click', () => {
        row.scrollLeft -= row.offsetWidth;
    })
}

function carousel4() {
    var row = document.getElementById("carousel__list-4");
    // var car_product = document.querySelector('.carousel__element');

    var arrow_last = document.getElementById("last__carousel-4");
    var arrow_next = document.getElementById("next__carousel-4");

    arrow_next.addEventListener('click', () => {
        row.scrollLeft += row.offsetWidth;
    })

    arrow_last.addEventListener('click', () => {
        row.scrollLeft -= row.offsetWidth;
    })
}

