window.onload = async function () {
    let shopData = await loadMarketInfo();
    showMarketInfo(shopData);
    $('#market-list-popular').html(function() {return getProductsTop(shopData, 4) + $(this).html()});
    $('#market-list-all').html(function() {return getProductsTop(shopData) + $(this).html()});
    showInfoPage();
}

function showInfoPage() {
    $('.market-info-page').show();
    $('.market-items-page').hide();
}

function showItemsPage() {
    $('.market-info-page').hide();
    $('.market-items-page').show();
}

async function loadMarketInfo() {
    let pathArr = window.location.pathname.split("/");
    let id = pathArr[pathArr.length - 1];
    return await fetch("/market/api/info/" + id)
        .then(response => {
            return response.json()
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function showMarketInfo(data) {
    $('#market-logo').attr("src", "data:image/jpg;base64," + data["logo"]["picture"]);
    $('#market-title').html(data["name"]);
    $('#market-info-data').html(
        "<tr><td>Страна:</td><td>" + data["location"]["name"] + "</td></tr>" +
        "<tr><td>Email:</td><td>" + data["email"] + "</td></tr>" +
        "<tr><td>Телефон:</td><td>" + data["phone"] + "</td></tr>"
    );
}

function getProductsTop(data, amount = -1) {
    let itemList = data["items"].sort((a, b) => parseFloat(b["rating"]) - parseFloat(a["rating"]));
    if (amount === -1) {
        amount = itemList.length;
    }
    amount = Math.min(itemList.length, amount);
    let htmlData = "";
    for (let i = 0; i < amount; ++i) {
        try {
            htmlData += '<div class="card border-light m-2" style="width: 15rem; border: 0;">' +
                '<img class="card-img-top" alt="Item Image" src="data:image/jpg;base64,' + itemList[i]["images"][0]["picture"] + '">' +
                '<div class="card-body">' +
                '<h5 class="card-title text-dark">' + itemList[i]["name"] + '</h5>' +
                '<p class="card-text text-secondary">' + itemList[i]["description"] + '</p>' +
                '<p class="card-text">Цена: <span class="text-danger">' + itemList[i]["price"] + '</span></p>' +
                '<a href="' + window.location.origin + '/item/' + itemList[i]["id"] + '" class="btn btn-primary">Страница товара</a>' +
                '</div>' +
                '</div>'
        } catch (e) {
            console.error("Cant show an item card");
        }
    }
    return htmlData;
}