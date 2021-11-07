
const itemBlock = document.querySelector('#item-block')
const shopBlock = document.querySelector('#shop-block')

let itemArr = []
let shopArr = []


getPopularItems()
getPopularShops()

function getPopularItems() {
    fetch(`/api/items?page=${currentItemPage-1}&size=${currentItemSize}`)
        .then(resp => resp.json())
        .then(data => {
            itemArr = data.content
            renderItems()
            currentItemPage = data.number + 1
            totalItemPages = data.totalPages
            renderPages(itemPages, currentItemPage, totalItemPages)
        })
}

function getPopularShops() {
    fetch(`/api/shops?page=${currentShopPage-1}&size=${currentShopSize}`)
        .then(resp => resp.json())
        .then(data => {
            shopArr = data.content
            renderShops()
            currentShopPage = data.number + 1
            totalShopPages = data.totalPages
            renderPages(shopPages, currentShopPage, totalShopPages)
        })
}

function renderItems() {
    let str = ''
    itemArr.forEach(item => {
        const url = item.images[0].url
        str += `
            <div class="d-flex">
                <div class="gcard d-flex flex-column flex-grow-1">
                    <div>
                        <a href="item/${item.id}">
                            <img src="${url.substring(url.indexOf('/img'))}"
                                 class="card-img-top item-img"
                                 alt="фото товара">
                        </a>
                    </div>
                    <div class="card-body d-flex flex-column flex-grow-1">
                        <a class="text-reset d-flex flex-column flex-grow-1"
                           href="item/${item.id}">
                            <h5 class="card-title item-title">${item.name}</h5>
                            <p class="font-weight-bold">
                                Цена: <span class="text-danger">${item.price}</span>
                            </p>
                            <p class="flex-grow-1">
                                ${item.description.substring(0, 50) + '...'}
                            </p>
                            <a href="#" data-item-id="${item.id}" class="btn btn-primary btn-sm w-100" data-id="to-cart">
                                В корзину
                            </a>
                        </a>
                    </div>
                </div>
            </div>
        `
    })
    itemBlock.innerHTML = str
}

function renderShops() {
    let str = ''
    shopArr.forEach(shop => {
        const url = shop.logo.url
        str += `
            <div class="shop-card d-flex">
                <div class="gcard d-flex flex-column flex-grow-1">
                    <div class="d-flex justify-content-center">
                        <img src="${url.substring(url.indexOf('/img'))}"
                             class="card-img-top shop-img" alt="лого магазина">
                    </div>
                    <div class="card-body d-flex flex-column flex-grow-1">
                        <h5 class="card-title">${shop.name}</h5>
                        <p class="card-text flex-grow-1">
                            ${shop.description}
                        </p>
                        <a href="/market/${shop.id}" class="btn btn-primary btn-sm">
                            Перейти
                        </a>
                    </div>
                </div>
            </div>
        `
    })
    shopBlock.innerHTML = str
}
