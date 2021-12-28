//=============================================
//Arrays contenedores de los valores de filtros
//=============================================
var brands = [];
var petTypes = [];
var petSizes = [];
var petAges = [];
var productType = "";
var price = 0;
var all = "";


//=======================================
//Array contenedor de los tipos de filtro
//=======================================
var filters = [];
var filterList = [brands, petTypes, petSizes, petAges];


$(document).ready(function () {

    
    $.get("/products", function (data) {
        $('#products').find('.product-content').html(data);
        loadModalProduct();
    });
    
    //================================
    //Función "onclick" de los filtros
    //================================
    $('.category_item').click(function () {
        var filter = $(this).attr('category');
        var value = $(this).attr('value')
        const equals = (el) => el == value;

        if (filter == 'price') {
            price = $('#price').val();
        }

        if (filter == 'productType') {
            if (productType !== value) {
                productType = value;
                $(".category-button").removeClass("highlight");
                $(this).addClass("highlight");
            } else {
                productType = "";
                $(this).removeClass("highlight");
            }
        }
        //===========================
        //Agregado del tipo de filtro
        //===========================
        if (filter !== 'all') {
            if (!filters.includes(filter)) {
                filters.push(filter);
            }
            if (all.length > 0) {
                all = "";
                $("#all-btn").removeClass("highlight");
            }
        } else {
            if (all !== 'all') {
                all = filter;
                $(".category-button").removeClass("highlight");
                $(this).addClass("highlight");
            } else {
                all = "";
                $(this).addClass("highlight");
            }
            productType = "";
            price = 0;
        }



        $('#price').change(function () {
            document.getElementById('show-price').innerHTML = $(this).val();
        });

        //arreglar esta garcha*comentario motivacional*

        //===============================================================
        //Agregado y eliminación de "tipo de filtro" y "valor del filtro"
        //===============================================================


        switch (filter) {
            case 'brand':
                if (brands.some(equals)) {
                    removeItemFromArr(brands, value);

                    if (brands.length === 0) {
                        removeItemFromArr(filters, filter);
                    }
                } else {
                    brands.push(value)
                }
                break;
            case 'petType':
                if (petTypes.some(equals)) {
                    removeItemFromArr(petTypes, value);

                    if (petTypes.length === 0) {
                        removeItemFromArr(filters, filter);
                    }

                } else {
                    petTypes.push(value);
                }
                break;
            case 'petSize':
                if (petSizes.some(equals)) {
                    removeItemFromArr(petSizes, value);

                    if (petSizes.length === 0) {
                        removeItemFromArr(filters, filter);
                    }

                } else {
                    petSizes.push(value);
                }
                break;
            case 'petAge':
                if (petAges.some(equals)) {
                    removeItemFromArr(petAges, value);

                    if (petAges.length === 0) {
                        removeItemFromArr(filters, filter);
                    }

                } else {
                    petAges.push(value);
                }
                break;
        }

        //======================================================================
        //Chequeo por consola del contenedor de filtros y de la lista de filtros
        //======================================================================
        console.log(filter);
        console.log(value);
        console.log(filters);
        console.log(filterList);
        console.log(brands);
        console.log(petTypes);
        console.log(petSizes);
        console.log(petAges);

        //====================
        // Ocultando productos
        //====================
        $('.product-item').css('transform', 'scale(0)');
        function hideProduct() {
            $('.product-item').hide();
        } setTimeout(hideProduct, 175);


        //==================================================
        // Filtrando y mostrando la nueva lista de productos
        //==================================================
        function showProduct() {
            $.get("/listFilter?brands=" + brands + "&petTypes=" + petTypes + "&petSizes=" + petSizes + "&petAges=" + petAges + "&all=" + all + "&price=" + price + "&productType=" + productType, function (data) {
                $('#products').find('.product-content').html(data);
                loadModalProduct()
                // addCartButton()
                $('.product-item').show();
                $('.product-item').css('transform', 'scale(1)');
            });
        } setTimeout(showProduct, 300);

    });

});


$(document).on("click", ".add-to-cart_button",  function () {
    var itemId = $(this).attr('productId');

        itemList.push(itemId);

        console.log(itemId);

        cartCount = cartCount + 1;

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

// function addCartButton() {
//     $('.add-to-cart_button').click(function () {

//         var itemId = $(this).attr('productId');

//         itemList.push(itemId);

//         console.log(itemList);

//         cartCount = cartCount + 1;

//         $('#cart-btn').val(cartCount);
//         document.getElementById('cart-count').innerHTML = cartCount;

//         // $.get("/shoppingCart?products=" + itemList + "&selector=0", function (data) {
//         //     $('#shoppingCart').find('.modal-body').html(data);
//         //     deleteCartButton();
//         // });

//     });
// }

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