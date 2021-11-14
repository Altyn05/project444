var shopData
let currentPageNumber = 1;
const pagesDisplay = 3;
let numberOfPages;
let filteredItemList
const foundHeader = 'Найденные товары'
const popHeader = 'Самые популярные товары'
window.onload = async function () {
    $('.addItems').hide();
    shopData = await loadMarketInfo();
    console.log(shopData)
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
    $('.market-info-page').show();
    $('.market-items-page').hide();
}

function showItemsPage() {
    $('.market-info-page').hide();
    $('.market-items-page').show();
    showItems()
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
const itemSizeSection = document.getElementById('item-size-view')
const ITEM_SIZE = [4, 12, 24, 48, 2_147_483_647]
let currentItemSize = ITEM_SIZE[0]


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

async function filterFunc() {
    let itemList = shopData["items"]
    filteredItemList = itemList.filter(a => {
        return a.name.toLowerCase().includes(searchItemString.value.toLowerCase())
    })
    console.log("strolka:----:" + searchItemString.value)
    let pageData = paginator(2, currentPageNumber, filteredItemList)
    let pageView = pageData.trimmedItems
    pageButtons(pageData.numberOfPages)
    getFilteredProducts(pageView)
}

searchFormSubmit.addEventListener('click', (ev) => {
    ev.preventDefault()
    filterFunc()
});

searchReset.addEventListener('click', async ev => {
    ev.preventDefault()
    searchItemString.value = ''
    pageButtons(0)
    $('#market-list-section').html(function () {
        return getProductsTop(shopData, 4)
    });
})

function paginator(numberItemsPerPage, page, itemList) {
    let trimStart = (page - 1) * numberItemsPerPage;
    let trimEnd = trimStart + numberItemsPerPage;
    let trimmedItems = itemList.slice(trimStart, trimEnd);
    numberOfPages = Math.ceil(itemList.length / numberItemsPerPage);
    return {
        trimmedItems: trimmedItems,
        numberOfPages: numberOfPages
    }
}

function pageButtons(numOfPages) {
    let wrapper = document.getElementById("items-pagination-wrapper")
    generateSizeViewBlock()
    wrapper.innerHTML = ''
    console.log("chislo stranic:_____ " + numOfPages)
    console.log("TEK STRANICA:_____ " + currentPageNumber)
    let leftPageNum = (currentPageNumber - Math.floor(pagesDisplay / 2));
    let rightPageNum = (currentPageNumber + Math.floor(pagesDisplay / 2));
    console.log("otobr chislo stranic: " + pagesDisplay)
    console.log("leftpage init: " + leftPageNum)
    console.log("rigthpage init: " + rightPageNum)
    if (leftPageNum < 1) {
        leftPageNum = 1;
        rightPageNum = pagesDisplay;
    }
    if (rightPageNum > numOfPages) {
        leftPageNum = numOfPages - (pagesDisplay - 1);
        rightPageNum = numOfPages;
        if (leftPageNum < 1) {
            leftPageNum = 1;
        }
    }
    console.log("leftpage: " + leftPageNum)
    console.log("rigthpage: " + rightPageNum)
    if (numOfPages !== 1) {
        for (let i = leftPageNum; i <= rightPageNum; i++) {
            wrapper.innerHTML += '<li class="page-item" value=' + i + '><a class="page-link" href="#">' + i + '</a></li>'
        }
    }

    if (currentPageNumber !== 1) {
        wrapper.innerHTML = '<li class="page-item" value=' + 1 + '><a class="page-link" aria-valuetext="1" href="#">&#171;</a></li>' + wrapper.innerHTML
    }
    if (currentPageNumber !== numOfPages) {
        wrapper.innerHTML += '<li class="page-item" value=' + numOfPages + '><a class="page-link" href="#">&#187;</a></li>'
    }

    let pn = document.getElementsByClassName('page-item')
    for (let i = 0; i < pn.length; i++) {
        pn[i].addEventListener('click', function () {
            currentPageNumber = Number(pn[i].value)
            console.log("chislo stranic: " + numberOfPages)
            filterFunc()
        })
    }
    const pageItemSelector = document.getElementsByClassName('page-item-selector')
    for (let i = 0; i < pageItemSelector.length; i++) {
        pageItemSelector[i].addEventListener('click', e => {
            const text = e.target.innerText
            const size = isNaN(+text) ? ITEM_SIZE[ITEM_SIZE.length - 1] : +text
            currentItemSize = size
            console.log(currentItemSize)
            setActiveSize()
        })
    }
}


function setActiveSize(target) {

    // for (let i = 1; i < arr.length; i++) {
    //     arr[i].value === current
    //         ? arr[i].classList.add('active')
    //         : arr[i].classList.remove('active')
    // }
}

function generateSizeViewBlock() {
    let htmlString = `<li class="page-item disabled"><span class="page-link">Показывать:</span></li>`

    ITEM_SIZE.forEach(n => {
        htmlString += `<li class="page-item-selector" value="${n}"><button class="page-link">${n > 64 ? 'Все' : n}</button></li>`
    })
    itemSizeSection.innerHTML = htmlString;

}







