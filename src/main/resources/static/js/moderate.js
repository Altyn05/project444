let hpShop = [];
let hpItem = [];


const urlApi = "http://localhost:8888/adminapi/";



///////////////////////////////////////////////   shops

// id - shop_id, item_id, ...    who = 0 -> shop, 1 -> item
function buttonRejectPrepareEvent(id, who) {


    let x = document.getElementById("moderateFormShop");
    x.elements["shopId"].value = id;
    x.elements["who"].value = who;
    x.elements["rejectReason"].value = "";
    $('.windowRejectShop #modalRejectShop').modal("show");
}

async function  buttonApproveEvent(id) {
    const response = await fetch( urlApi +"moderatorApprove/"+id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: ''
    });
    await loadShops();
}

async function buttonConfirmRejectShop() {
    let x = document.getElementById("moderateFormShop");
    let urlRequest = urlApi;
    switch (x.elements["who"].value){
        case "0" : urlRequest = urlRequest + "moderatorReject";
                   break;
        case "1" : urlRequest = urlRequest + "moderatorRejectItem";
                   break;

    }

    const response = await fetch( urlRequest , {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: 'shopId=' + x.elements["shopId"].value+'&rejectReason=' +x.elements["rejectReason"].value
    });
    $('.windowRejectShop #modalRejectShop').modal("hide");

    switch (x.elements["who"].value){
        case "0" :  await loadShops();
            break;
        case "1" :  await  loadItems();
            break;

    }


}

const loadShops = async () => {
    const res = await fetch(urlApi+"notModeratedShops");
    hpShop = await res.json();
    displayShops(hpShop)


};



const displayShops = (list) => {
    moderateShopsList.innerHTML = list
        .map((shop) => {
            let row =
            '<tr id="dataIdShop' + shop.id + '">' +
            '    <td>' + shop.id + '</td>' +
            '    <td>' + shop.name + '</td>' +
            '    <td><img class="card-img-top" alt="Item Image" src="data:image/jpg;base64,' + shop.logo.picture + '"> </td>' +
            '    <td>'
            if (shop.moderatedReason !=null ) {
               row =  row +  shop.moderatedReason
            }
              row = row +
              '  </td>' +
              '  <td><a href = "#" class="btn btn-warning" onclick="buttonApproveEvent(' + shop.id+ ')" >Одобрить</a></td>' +
              '  <td><a href = "#" class="btn btn-info" onclick = "buttonRejectPrepareEvent(' + shop.id+ ', 0)">Отклонить</a></td>' +
            '</tr>';
            return row

        })
        .join('');
};
///////////////////////////////////   items

async function  buttonApproveItemEvent(id) {
    const response = await fetch( urlApi +"moderatorApproveItem/"+id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: ''
    });
    await loadItems();
}

const loadItems = async () => {
    const res = await fetch(urlApi+"notModeratedItems");
    hpItem = await res.json();
    displayItems(hpItem);


};



const displayItems = (list) => {
    itemsList.innerHTML = list
        .map((item) => {
            return `
            <tr id="dataIdItem${item.id}">
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td><img class="card-img-top" alt="Item Image" src="data:image/jpg;charset=utf-8;base64,${item.images[0].picture}"></td>
                <td>${item.description}</td>
                
                <td><a href = "#" class="btn btn-warning" onclick="buttonApproveEvent(${item.id})" >Одобрить</a></td>
                <td><a href = "#" class="btn btn-info" onclick = "buttonRejectPrepareEvent(${item.id}, 1)">Отклонить</a></td>
            </tr>
        `;
        })
        .join('');
};

loadShops().then();
loadItems().then();

