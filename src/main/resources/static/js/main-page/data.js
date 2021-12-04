
function getData(target) {
    const page = target === 'items' ? currentItemPage : currentShopPage
    const size = target === 'items' ? currentItemSize : currentShopSize
    
    fetch(`/api/${target}?search=${searchText}&categoryId=${categoryId}&page=${page-1}&size=${size}`)
        .then(resp => resp.json())
        .then(data => {
            if (target === 'items') {
                itemArr = data.content
                renderItems()
                currentItemPage = data.number + 1
                totalItemPages = data['totalPages']
                renderPageBlock('items')
            } else {
                shopArr = data.content
                renderShops()
                currentShopPage = data.number + 1
                totalShopPages = data['totalPages']
                renderPageBlock('shops')
            }
        })
}

function renderItems() {
    setHeader('items')
    
    let str = ''
    itemArr.forEach(item => {
        const url = item.images[0].url
        str += `
            <div class="d-flex">
                <div class="gcard d-flex flex-column flex-grow-1 w-100">
                    <div>
                        <a href="item/${item.id}">
                            <img src="data:image/png;base64,${item.images[0].picture}"
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
                            <a href="#" data-item-id="${item.id}"
                               class="btn btn-primary btn-sm w-100">
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
    setHeader('shops')
    let str = ''
    shopArr.forEach(shop => {
        console.log(shop)
        if (shop.description) {
        const url = shop['logo'].url
        str += `
            <div class="shop-card d-flex">
                <div class="gcard d-flex w-100">
                    <a href="/market/${shop.id}" class="text-reset">
                        <div class="d-flex justify-content-center">
                            <img src="data:image/png;base64,${shop.logo.picture}"
                                 class="card-img-top shop-img"
                                 alt="лого магазина">
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${shop.name}</h5>
                            <p class="m-0">${shop.description}</p>
                        </div>
                    </a>
                </div>
            </div>
        `
    }
    })
    shopBlock.innerHTML = str
}

function setHeader(target) {
    if (target === 'items') {
        itemHeader.textContent = searchText
            ? categoryName ? ITEM_TEXT[3] + categoryName : ITEM_TEXT[2]
            : categoryName ? ITEM_TEXT[1] + categoryName : ITEM_TEXT[0]
    } else {
        shopHeader.textContent = searchText
            ? categoryName ? SHOP_TEXT[3] + categoryName : SHOP_TEXT[2]
            : categoryName ? SHOP_TEXT[1] + categoryName : SHOP_TEXT[0]
    }
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