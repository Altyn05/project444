let currentPageNumber=1;
let filteredItemList
const foundHeader = 'Найденные товары'
const popHeader = 'Самые популярные товары'
window.onload = async function () {
    let shopData = await loadMarketInfo();
    showMarketInfo(shopData);
    $('#market-list-section').html(function () {
        return getProductsTop(shopData, 4) + $(this).html()
    });
    $('#market-list-all').html(function () {
        return getProductsTop(shopData, -1) + $(this).html()
    });
    showInfoPage();
}

function showInfoPage() {
    $('.market-items-page').hide();
    $('.market-info-page').show();
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
    htmlData += '<h3 className="market-list-title">' + popHeader + '</h3>'
    htmlData += '<div class="d-flex flex-row flex-wrap">'
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
            console.error("Can't show an item card");
        }
    }
    return htmlData;
}

const searchItemString = document.getElementById("search_item");
const searchFormSubmit = document.getElementById("search_item_start");
const searchReset = document.getElementById("search_reset");
const itemList = document.getElementById('market-list-section')

function getFilteredProducts(itemData) {
    console.log("enter to builder: " + currentPageNumber)
    let htmlData = "";
    htmlData += '<h3 className="market-list-title">' + foundHeader + '</h3>'
    htmlData += '<div class="d-flex flex-row flex-wrap">'
    for (let i = 0; i < itemData.length; ++i) {
        try {
            htmlData += '<div class="card border-light m-2" style="width: 15rem; border: 0;">' +
                '<img class="card-img-top" alt="Item Image" src="data:image/jpg;base64,' + itemData[i]["images"][0]["picture"] + '">' +
                '<div class="card-body">' +
                '<h5 class="card-title text-dark">' + itemData[i]["name"] + '</h5>' +
                '<p class="card-text text-secondary">' + itemData[i]["description"] + '</p>' +
                '<p class="card-text">Цена: <span class="text-danger">' + itemData[i]["price"] + '</span></p>' +
                '<a href="' + window.location.origin + '/item/' + itemData[i]["id"] + '" class="btn btn-primary">Страница товара</a>' +
                '</div>' +
                '</div>'
        } catch (e) {
            console.error("Can't show item list");
        }
    }
    itemList.innerHTML = htmlData
}

searchFormSubmit.addEventListener('click',  (ev) => {
    ev.preventDefault()
     filterFunc().then()
});

async function filterFunc(){
    let shopData = await loadMarketInfo();
    let itemList = shopData["items"]
    filteredItemList = itemList.filter(a => {
        return a.name.toLowerCase().includes(searchItemString.value.toLowerCase())
    })
    let pageData = paginator(2, currentPageNumber, filteredItemList)
    let pageView = pageData.trimmedItems
    pageButtons(pageData.numberOfPages)
    getFilteredProducts(pageView)
}

searchReset.addEventListener('click', async ev => {
    ev.preventDefault()
    let shopData = await loadMarketInfo();
    $('#market-list-section').html(function () {
        return getProductsTop(shopData, 4)
    });
})

function paginator(numberItemsPerPage, page, itemList) {
    let trimStart = (page - 1) * numberItemsPerPage;
    let trimEnd = trimStart + numberItemsPerPage;
    let trimmedItems = itemList.slice(trimStart, trimEnd);
    let numberOfPages = Math.ceil(itemList.length / numberItemsPerPage);
    return {
        trimmedItems: trimmedItems,
        numberOfPages: numberOfPages
    }
}

function pageButtons(numOfPages) {
    let wrapper = document.getElementById("items-pagination-wrapper")
    wrapper.innerHTML = '';
    for (let i = 1; i <= numOfPages; i++) {
        wrapper.innerHTML += '<li class="page-item"><a class="page-link" href="#">'+i+'</a></li>'
    }
    let pn = document.getElementsByClassName('page-item')
    for (let i = 0; i < pn.length; i++) {
        pn[i].addEventListener('click', function (){
            currentPageNumber = pn[i].textContent
            console.log(currentPageNumber)
            filterFunc()
        })
    }
}








