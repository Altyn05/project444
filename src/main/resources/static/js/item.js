setActiveMiniImg()
const linkToReviews = document.querySelector('#toReviews')
const item = document.querySelector('[data-item-id]')

$('.carousel').on('slid.bs.carousel', function () {
    document.querySelector('.mini-img-list .active').classList.remove('active')
    setActiveMiniImg()
})

function setActiveMiniImg() {
    document.querySelector(`#mini-img-${getActiveImg()}`).classList.add('active')
}

function getActiveImg() {
    return document.querySelector('.carousel-indicators .active').getAttribute('data-slide-to')
}

linkToReviews.addEventListener('click', evt => {
    evt.preventDefault()
    document.querySelector('#reviews-panel').scrollIntoView({behavior: "smooth"})
})

item.addEventListener('click', evt => {
    evt.preventDefault();
    const itemId = evt.target.getAttribute('data-item-id');
    addToCart(itemId);
})

// Получения объекта item
function getReviewForItem(id) {
    fetch("/item/findAll/" + id)
        .then(response => response.json())
        .then(item => {
            console.log(item)
            document.getElementById('id').value = item.id;
            document.getElementById('name').value = item.name;

        })
}

// Post метод создания отзыва
function addReviews() {
    // event.preventDefault();
    item2 = {
        id: $('#id').val(),
        name: $('#name').val()
    };

    let path = (window.URL || window.webkitURL).createObjectURL($('#file')[0].files[0]);
    console.log('path', path);

    let pictureInput = $('#file')[0].files[0];
    let picture
    if (pictureInput !== undefined) {
        picture = imageToBinary(pictureInput)
    }

    let review = {
        dignity: $('#dignity').val(),
        flaw: $('#flaw').val(),
        rating: $('#rating').val(),
        text: $('#text').val(),
        item: item2,
        picture: picture,
        url: path
    };
    console.log(JSON.stringify(review));
    fetch("/item/findAll", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(review),
    })
        .then((response) => console.log(response.status))
        .catch(e => console.error(e))
}

// Изображение в массив байтов
function imageToBinary(image) {
    let reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onloadend = function () {
        let data = (reader.result).split(',')[1];
        let binaryBlob = atob(data);
        localStorage.setItem("image", binaryBlob)
    }
    let imageBase64 = localStorage.getItem("image")
    return base64ToBinary(imageBase64)
}
// Base64 в массив байтов
function base64ToBinary(imageBase64) {
    let bytes = [];
    for (let i = 0; i < imageBase64.length; i++) {
        bytes.push(
            imageBase64.charCodeAt(i)
        );
    }
    return bytes
}

let my_user = null;
$.getJSON("/api/users/principal", function(json) {
    my_user = json;
});

let pathArr = window.location.pathname.split("/");
let id = pathArr[pathArr.length - 1];
let my_item = null;
$.getJSON("/item/findAll/" + id, function(json) {
    my_item = json;
});

let favorite_id = null;
let fav_id = null;
$.getJSON("/favorites/getIdItem", function(json) {
    favorite_id = json;
    let item = my_item;
});


function newInFavorite() {
    let item = my_item;
    let shops = {
        id: my_item.shopId
    }
 item.shop = shops;
    item.favorite = true;
    let user = my_user;

    let items = [];
    items.push({
        id: item.id})
    let users = {
        username: user.username,
        id: user.id
    }

    let favorite = null;
    if(user != null){
        let favorites = {
            items : items,
            user : users
        }
        favorite = favorites;
    }

    console.log(JSON.stringify(favorite));
    fetch("/favorites", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(favorite),
    })
        .then((response) => console.log(response.status))
    fetch("/item/findAll/editFavorite", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(item),
    })
        .then((response) => console.log(response.status))
        .catch(e => console.error(e))
}

function deleteFavorite() {
    let item = my_item;
    fav_id = favorite_id[my_item.id];
    let shops = {
        id: my_item.shopId
    }
    item.shop = shops;
    item.favorite = false;

    fetch("/favorites/delete/" + fav_id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => console.log(response.status))
    fetch("/item/findAll/editFavorite", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(item),
    })
        .then((response) => console.log(response.status))
        .catch(e => console.error(e))
}
