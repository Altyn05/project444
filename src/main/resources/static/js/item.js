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
