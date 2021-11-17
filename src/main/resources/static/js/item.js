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
    $('#cart-modal').modal('show');
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