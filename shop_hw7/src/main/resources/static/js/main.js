var itemPrice = $('#item_price');
var productId = $('#product_id');
var quantity = $('#quantity');
var incrementButton= $('#increment');
var decrementButton = $('#decrement');

quantity.html('0');

incrementButton.on('click', function () {
    var incVal = parseInt(quantity.html()) + 1;
    quantity.html(incVal);
});

decrementButton.on('click', function () {
    var decVal = parseInt(quantity.html()) - 1;
    quantity.html(decVal);
});

var addToCartButton = $('#addToCart');

addToCartButton.on('click', function () {
    $.ajax({
        url: 'http://localhost:8080/api/addToCart',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            itemPrice: itemPrice.val(),
            quantity: parseInt(quantity.html()),
            productId: productId.val(),
        }),
        success: function (data) {
            $('#cartSize').html('Корзина ' + data);
        }
    });
});

$.ajax({
    url: 'http://localhost:8080/api/cartSize',
    contentType: 'json',
    success: function (data) {
        $('#cartSize').html('Корзина ' + data);
    }
});
