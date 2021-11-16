var tempShopData

function showItems() {
    tempShopData = JSON.parse(JSON.stringify(shopData));
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

function addItems() {
    $('.market-items-page').hide();
    $('.addItems').show();
    $('#ch1').html("Количество")
    $('#ch2').html("Добавить")
    $('#ch3').hide();
    const rowsToDelete1 = document.querySelectorAll('#editItemBTN')
    rowsToDelete1.forEach(row => row.remove())
    const rowsToDelete2 = document.querySelectorAll('#deleteItemBTN')
    rowsToDelete2.forEach(row => row.remove())
    let varHTML1 =
        "<td class='DELITEMS'>\n" +
            "<form>" +
                "<input class='addItemCount' type='number' min='0' style='width: 80px'>" +
            "</form>" +
        "</td>\n"
    let varHTML2 =
        "<td class='DELITEMS'>\n" +
            "<input type='checkbox' class='addItem'>" +
        "</td>\n"
    const rowsToAdd1 = document.querySelectorAll('#editItemTD')
    rowsToAdd1.forEach(row => row.insertAdjacentHTML('beforeend', varHTML1))
    const rowsToAdd2 = document.querySelectorAll('#deleteItemTD')
    rowsToAdd2.forEach(row => row.insertAdjacentHTML('beforeend', varHTML2))

    let varHTML3 =
        "<div className='DELITEMS'>" +
            "<a class='text-secondary btn btn-outline-warning text-primary' onclick='addNewItems()'> Добавить выбранные товары в магазин </a>" +
        "</div><br>"

    document.querySelector('#ch4').insertAdjacentHTML('afterbegin', varHTML3);

    $('#searchBarItems').hide()

    let varHTML4 =
        "<input type='text' id='myInput' onKeyUp='searchable()' placeholder='Search for names..' title='Type in a name'>"
    document.querySelector('#searchWrapperItems').insertAdjacentHTML('afterbegin', varHTML4);
}

function searchable() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.querySelector('#myInput')
    filter = input.value.toUpperCase();
    table = document.querySelector('#ch5');
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
    let itemsList = document.querySelectorAll(".itemsList tr")
    console.log(itemsList)
    let addItemCount = document.querySelectorAll(".addItemCount")
    console.log(addItemCount)
    let addItem = document.querySelectorAll(('.addItem'))
    console.log(addItem)
    let i = 0
    for(let items of itemsList) {
        let checked = null
   //     if (add.checked === true) {
  //          tempShopData.items.push({id: })




   //         splice(i, 1); i-- }
    //    tempShopData.items.push
    }


}


function saveItems() {
    saveCount()
    deleteItems()
    showItems()
}

function saveCount() {
    let newCount = document.querySelectorAll('.newCount')
    let i = 0
    for (let count of newCount) {
        tempShopData.items[i].count = count.value
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


