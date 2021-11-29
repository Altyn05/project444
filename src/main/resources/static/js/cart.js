let cart;
let amountInputs;
let cartEmpty;
let cartPriceLabel;
let cartItems;
let cartPurchase;

window.onload = async function () {
    cart = await getCart();

    cartEmpty = document.getElementById('cart-empty');
    cartPriceLabel = document.getElementById('cart-price-label');
    cartItems = document.getElementById('cart-items');
    cartPurchase = document.getElementById('cart-purchase');
    updateCartModal();
}

function updateCartModal() {
    let price = 0;
    cartEmpty.hidden = cart.length > 0
    cartPriceLabel.hidden = cart.length === 0;
    cartPurchase.disabled = cart.length === 0;
    cartItems.innerHTML = '';
    for (let i = 0; i < cart.length; ++i) {
        price += cart[i]['itemDto']['price'] * cart[i]['quantity'];
        cartItems.innerHTML +=
            '<div class="cart-item row">' +
            '<div class="col-9">' +
            cart[i]['itemDto']['name'] +
            '</div>' +
            '<div class="col-3">' +
            '<input onchange="changeAmount(' + cart[i]['id'] + ')" class="cart-amount" id="cart-id-' + cart[i]['id'] + '" type="number" min="0" max="100" data-initial-value="1" value="' + cart[i]['quantity'] + '">' +
            '<button onclick="delItem(' + cart[i]['id'] + ')" class="btn btn-outline-warning btn-sm cart-amount-control">&times;</button>' +
            '</div>' +
            '</div>' +
            '<hr class="cart-item-row">';
    }
    $('#cart-price').html(price);
}

async function changeAmount(id) {
    let item = cart.find(item => item['id'] == id)
    if (item !== undefined) {
        let amount = $('#cart-id-' + id).val();
        if (amount <= 0) {
            await delItem(id);
        } else {
            let data = {
                id: id,
                amount: amount,
                pre_amount: item['quantity']
            }
            cart = await sendResponse('PUT', data);
            updateCartModal();
        }
    }
}

async function delItem(id) {
    let item = cart.find(item => item['id'] == id)
    if (item !== undefined) {
        let data = {
            id: id,
            amount: item['quantity'],
            pre_amount: item['quantity']
        }
        cart = await sendResponse('DELETE', data);
        updateCartModal();
    }
}

async function addToCart(id) {
    let item = cart.find(item => item['id'] == id);
    let data = {
        id: id,
        amount: 0,
        pre_amount: 0
    };
    if (item === undefined) {
        data.amount = 1;
        cart = await sendResponse('PUT', data);
    } else {
        data.amount = item['quantity'] + 1;
        data.pre_amount = item['quantity'];
        cart = await sendResponse('PUT', data);
    }
    updateCartModal();
}

async function getCart() {
    return await fetch('/cart')
        .then(response => response.json())
        .then(data => {
            return data;
        });
}

async function sendResponse(method, data) {
    return await fetch('/cart', {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    }).then(response => response.json())
        .then(data => {
            return data;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

$.getJSON("/favorites/getItemsByFavorite", function(json) {
    let result =  json.filter(e => e.length);

    let itemsName = [];
    let bytes = [];
    let i;
    for (i = 0; i < result.length; ++i) {

        itemsName.push(result[i][0]["images"][0]["picture"]);
        if(document.getElementById("item") != null) {
            document.getElementById("item").innerHTML += '<p>' + '<a href="/item/' + result[i][0]["id"] + ' ">' + '<img src="data:image/jpg;base64,'
                + result[i][0]["images"][0]["picture"] + '" width="70 height="70">' + result[i][0]["name"] + '</a>' + '</p>';
        }
    }
});

$.getJSON("/favorites/getShopByFavorite", function(json2) {
    let result =  json2.filter(e => e.length);

    if (result != null) {

        let shopsName = [];
        let i;
        for (i = 0; i < result.length; ++i) {

            shopsName.push(result[i][0]["name"]);
            if(document.getElementById("shops") != null){
            document.getElementById("shops").innerHTML += '<p>' +  '<a href="/market/' + result[i][0]["id"] + ' ">' + '<img src="data:image/jpg;base64,'
                + result[i][0]["logo"]["picture"] + '" width="70 height="70">' + result[i][0]["name"] +'</a>' + '</p>';
            }
        }
    }
});