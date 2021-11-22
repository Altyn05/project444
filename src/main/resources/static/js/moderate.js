let hpShop = [];
let hpItem = [];
let hpReview = [];

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
        case "2" : urlRequest = urlRequest + "moderatorRejectReview";
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
        case "2" :  await  loadReviews();
            break;

    }


}

const loadShops = async () => {
    const res = await fetch(urlApi+"notModeratedShops");
    hpShop = await res.json();
    displayShops(hpShop)
    let x = document.getElementById("badge-shop");
    x.innerText = "" + hpShop.length;

};



const displayShops = (list) => {
    moderateShopsList.innerHTML = list
        .map((shop) => {
            let row =
            '<tr id="dataIdShop' + shop.id + '">' +
//            '    <td>' + shop.id + '</td>' +
            ' <td> <span class = "border-0"><div class = "card border-0"> <img class="card-img-top" alt="Item Image" src="data:image/jpg;base64,' + shop.logo.picture + '"> ' +
            '    <div class = "card-body"><div class="card-text text-center"> ' + shop.name + '</div></div>' +

            '  </div> </span> <td>'
            if (shop.moderatedRejectReason !=null ) {
               row =  row +  shop.moderatedRejectReason;
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
///////////////////////////////////   items  ////////////////////////////////////////

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
    let x = document.getElementById("badge-items");
    x.innerText = "" + hpItem.length;


};

const displayItems = (list) => {
    itemsList.innerHTML = list
        .map((item) => {
            let reason="";
            if (item.moderatedRejectReason !=null ) {
                reason =  item.moderatedRejectReason;
            }

            let carouselItems = "";

            item.images.forEach(function (img, i, arr) {
                if (i == 0 ) {carouselItems = carouselItems + `<div class="carousel-item active">`;}
                 else {carouselItems = carouselItems + `<div class="carousel-item">`}
                carouselItems = carouselItems + `<img className="card-img-top" alt="Item Image" width = "300" src="data:image/jpg;charset=utf-8;base64,${img.picture}">`
                + `</div>`
            })


//            <td>${item.id}</td>


            return `
            <tr id="dataIdItem${item.id}">
            <td>
                <span class = "border-0">
                <div class = "card border-0" style ="width: 300px">
                    <div  id = "carousel${item.id}"  class = "carousel  slide " data-bs-ride="carousel">
                        <div class = "carousel-inner">
                            ${carouselItems}
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#carousel${item.id}" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#carousel${item.id}" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>               
                    </div>
                    <div class = "card-body"><div class="card-text text-center"> ${item.name}</div></div>
                </div>
                </span>
                </td>
                <td>${item.description}</td>
                <td>${reason}</td>
                <td><a href = "#" class="btn btn-warning" onclick="buttonApproveItemEvent(${item.id})" >Одобрить</a></td>
                <td><a href = "#" class="btn btn-info" onclick = "buttonRejectPrepareEvent(${item.id}, 1)">Отклонить</a></td>
            </tr>
        `;
        })
        .join('');
};
///////////////////////////////////////////////////// reviews  ////////////////////////////////////////////
async function  buttonApproveReviewEvent(id) {
    const response = await fetch( urlApi +"moderatorApproveReview/"+id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: ''
    });
    await loadReviews();
}

const loadReviews = async () => {
    const res = await fetch(urlApi+"notModeratedReviews");
    hpReview = await res.json();
    displayReviews(hpReview);
    let x = document.getElementById("badge-reviews");
    x.innerText = "" + hpReview.length;

};

const displayReviews = (list) => {
    reviewsList.innerHTML = list
        .map((item) => {
            let reason="";
            if (item.moderatedRejectReason !=null ) {
                reason =  item.moderatedRejectReason;
            }
   //        <td>${item.id}</td>
            return `
            <tr id="dataIdReview${item.id}">
   
                <td>${item.date}</td>
                <td>${item.dignity}</td>
                <td>${item.flaw}</td>
                <td>${reason}</td>
                
                
                <td><a href = "#" class="btn btn-warning" onclick="buttonApproveReviewEvent(${item.id})" >Одобрить</a></td>
                <td><a href = "#" class="btn btn-info" onclick = "buttonRejectPrepareEvent(${item.id}, 2)">Отклонить</a></td>
            </tr>
        `;
        })
        .join('');
};

///////////////////////////////////////////////////  main  /////////////////////////////////////////////////////////////


loadShops().then();
loadItems().then();
loadReviews().then();

