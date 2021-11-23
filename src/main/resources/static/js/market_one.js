$(document).ready(function() {

    $('#editButton').on('click', function (event) {

        event.preventDefault();

        var href = $(this).attr('href');
        console.log($.get(href))

        $.get(href, function (shop) {
            shop = JSON.parse(shop);
            $(' #inputId').val(shop.id);
            $('#inputName1').val(shop.name);
            $('#inputCountry1').val(shop.location);
            $('#inputCity1').val(shop.city);
            $('#inputEmail1').val(shop.email);
            $('#inputPhone1').val(shop.phone);
            $(' #inputDescription1').val(shop.description);
        });
        $('#updateShopModal').modal();
    });
});