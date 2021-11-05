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
    console.log(cart)

    let price = 0;
    cartEmpty.hidden = cart.length > 0
    cartPriceLabel.hidden = cart.length === 0;
    cartPurchase.disabled = cart.length === 0;
    cartItems.innerHTML = '';
    for (let i = 0; i < cart.length; ++i) {
        price += cart[i]['itemDto']['price'] * cart[i]['quantity'];
        cartItems.innerHTML +=
            '<div class="d-flex justify-content-between mb-3">' +
                '<div class="col-6">' + cart[i]['itemDto']['name'] + '</div>' +
                '<div class="col-3 p-0 row justify-content-center">' +
                    '<div class="p-0">' + '<button onclick="" class="btn btn-outline-warning btn-sm cart-amount-control">&#8722;</button>' + '</div>' +
                    '<div class="cart-amount" id="cart-id-' + cart[i]['id'] + '" type="number" min="0" max="100" data-initial-value="1">' + cart[i]['quantity'] + '</div>' +
                    '<div class="p-0">' + '<button onclick="" class="btn btn-outline-warning btn-sm cart-amount-control">&#43;</button>' + '</div>' +
                '</div>' +
                '<div class="col-2 text-right">' + cart[i]['itemDto']['price'] + '</div>' +
                '<div class="p-0 col-1">' + '<button onclick="delItem(' + cart[i]['id'] + ')" class="btn btn-outline-danger btn-sm cart-amount-control">&times;</button>' + '</div>' +
            '</div>'
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