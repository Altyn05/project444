let hpShop = [];
let hpItem = [];


const urlApi = "http://localhost:8888/adminapi/";



///////////////////////////////////////////////   shops
function buttonRejectPrepareEvent(id) {

    let x = document.getElementById("moderateFormShop");
    x.elements["shopId"].value = id;
    x.elements["rejectReason"].value = "blabla";
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
    const response = await fetch( urlApi +"moderatorReject", {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: 'shopId=' + x.elements["shopId"].value+'&rejectReason=' +x.elements["rejectReason"].value
    });

    $('.windowRejectShop #modalRejectShop').modal("hide");
    await loadShops();
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
               row =  row + shop.moderatedReason
            }
              row = row +
              '  </td>' +
              '  <td><a href = "#" class="btn btn-warning" onclick="buttonApproveEvent(' + shop.id+ ')" >Одобрить</a></td>' +
              '  <td><a href = "#" class="btn btn-info" onclick = "buttonRejectPrepareEvent(' + shop.id+ ')">Отклонить</a></td>' +
            '</tr>';
            return row

        })
        .join('');
};
///////////////////////////////////   items
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
                
                <td><button type="button" class="btn btn-info" >Редактировать</button></td>
                <td><button type="button" class="btn btn-danger" >Удалить</button></td>
            </tr>
        `;
        })
        .join('');
};

loadShops().then();
loadItems().then();

