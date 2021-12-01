var tempShopData

async function loadItems() {
    let response = await fetch("http://localhost:8888/api/shopItems", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    if (response.ok) {
        return response.json()
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

function showEditItems() {
    tempShopData = JSON.parse(JSON.stringify(shopData));
    showItems()
}

function showItems() {
    const rowsToDelete = document.querySelectorAll('.DELITEMS')
    rowsToDelete.forEach(row => row.remove())

    for(let item of tempShopData.items) {

        let tr = document.createElement('tr')
        tr.className = "DELITEMS"

        let varHTML =
            "<td class='DELITEMS'>\n" +
                "<span>ID: " + item.id + "</span>\n" +
            "</td>\n" +
            "<td class='DELITEMS'>\n" +
                "<img style='border-radius: 50%; max-height: 30px' src = 'data:image/png;base64," + item.images[0].picture + "'/>\n" +
            "</td>\n" +
            "<td class='DELITEMS'>\n" +
                "<a href='/item/" + item.id + "'>" + item.name + "</a>\n" +
            "</td>\n" +
            "<td class='DELITEMS'>\n" +
                "<form>" +
                    "<p><label for='count'>Изменить количество:&nbsp; </label>" +
                        "<input class='newCount' type='number' min='0' style='width: 50px' value='" + item.count + "'></p>" +
                "</form>" +
            "</td>\n" +
            "<td class='DELITEMS'>\n" +
                "<p><input type='checkbox' class='deleteItem'><span style='margin-left: 20px'>Удалить товар из магазина</span></p>" +
            "</td>\n"

        document.querySelector('.market-items').appendChild(tr).insertAdjacentHTML('beforeend', varHTML);
    }
}

async function addItems() {
    $('.addItemsToShop').remove();

    let varHTML = '<table class="addItemsToShop"></table>'
    document.querySelector('.addItems').insertAdjacentHTML('beforeend', varHTML);

    let shopItemIds =[]
    tempShopData.items.map(item => shopItemIds.push(item.id))

    $('.market-items-page').hide();
    $('.addItems').show();

    let items = await loadItems()
    items.map(item => {
        if(!shopItemIds.includes(item.id)){
            let varHTML =
                "<td class='DELADDITEMS'>" +
                    "<span style='margin-left: 20px'>ID: " + item.id + "</span>" +
                "</td>" +
                "<td class='DELADDITEMS'>" +
                    "<a style='margin-left: 20px' href='/item/" + item.id + "'>" + item.name + "</a>" +
                "</td>" +
                "<td class='DELADDITEMS'>" +
                    "<span style='margin-left: 20px'>Цена: " + item.price + "</span>" +
                "</td>" +
                "<td class='DELADDITEMS'>" +
                    "<span style='margin-left: 20px'>Рейтинг: " + item.rating + "</span>" +
                "</td>" +
                "<td class='DELADDITEMS'>" +
                    "<input type='checkbox' class='addItem' style='margin-left: 50px'>" +
                "</td>" +
                "<td class='DELADDITEMS'>" +
                    "<p><span style='margin-left: 20px'>Добавить товар в магазин</span>" +
                "</td>"
            document.querySelector('.addItemsToShop').insertAdjacentHTML('beforeend', varHTML);
        }
    })
}

function searchable() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.querySelector('#myInput')
    filter = input.value.toUpperCase();
    table = document.querySelector('.addItemsToShop');
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function addNewItems() {
    let itemsList = document.querySelectorAll('.addItemsToShop tr')

    let i = 0
    for(let items of itemsList) {
        let id = Number(items.cells[0].textContent.replace(/^.{4}/, ''))
        let name = items.cells[1].textContent
        let checked = items.cells[4].firstElementChild.checked

        if (checked === true) tempShopData.items.push({id: id, name: name, images: [{picture: ""}]})
    }
        $('.addItems').hide();
        showItems()
        $('.market-items-page').show();
}

async function putItems() {
    let response = await fetch("http://localhost:8888/api/shopItems", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tempShopData)
    })
    if (response.ok) {
        return response.json()
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

async function saveItems() {
    saveCount()
    deleteItems()
    shopData = await putItems()
    showEditItems()
}

function saveCount() {
    let newCount = document.querySelectorAll('.newCount')
    let i = 0
    for (let count of newCount) {
        if(count.value === "") tempShopData.items[i].count = 0
            else tempShopData.items[i].count = count.value
        i++
    }
}

function deleteItems() {
    let deleteItem = document.querySelectorAll('.deleteItem')
    let i = 0
    for (let item of deleteItem) {
        if (item.checked === true) { tempShopData.items.splice(i, 1); i-- }
        i++
    }
}


