function showUserShops() {
    const rowsToDelete = document.querySelectorAll('.DELSHOP')
    rowsToDelete.forEach(row => row.remove())

    for (let shop of user.shops) {

        let tr = document.createElement('tr')
        tr.className = "DELSHOP"

        let varHTML =
            "<td>\n" +
            "<img src = 'data:image/png;base64," + shop.logo.picture + "'/>\n" +
            "</td>\n" +
            "<td>\n" +
            "<a href='/market/" + shop.id + "'>" + shop.name + "</a>\n" +
            "</td>\n" +
            "<td>\n" +
            "<span>Рейтинг: " + shop.rating + "</span>\n" +
            "</td>\n" +
            "<td>\n" +
            "<span>" + shop.email + "</span>\n" +
            "</td>\n"


        document.querySelector('.usershops').appendChild(tr).insertAdjacentHTML('beforeend', varHTML);
    }
}

let shopLogo

function loadShopLogo() {
    let fileInput = document.querySelector(".newLogo")
    if (fileInput.files[0] == undefined) return
    return new Promise((resolve, reject) => {
        let reader = new FileReader()
        reader.onload = () => {
            document.shoplogotype.src = reader.result
            let res = reader.result.replace(/data:image.*,/, "")
            console.log(res)
            shopLogo = {id: null, url: "file", picture: res, isMain: true}
            resolve()
        }
        reader.onerror = reject
        reader.readAsDataURL(fileInput.files[0]);
    })
}

const newShopCreate = document.getElementById('submitNewShop')
let shopProfile = document.querySelectorAll('.form-control-shop')
const URLAddShop = 'http://localhost:8888/user/newShop/'
newShopCreate.addEventListener('submit', (ev) => {
    ev.preventDefault()
    console.log(shopProfile[0])
    let a = new Promise(function (resolve) {
        resolve(
            fetch(URLAddShop, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: shopProfile[0].value,
                    location: {
                        name: shopProfile[1].value
                    },
                    cityDto: shopProfile[2].value,
                    email: shopProfile[3].value,
                    phone: shopProfile[4].value,
                    description: shopProfile[5].value,
                    logo: shopLogo
                })
            })
        )
    })
    a.then(function () {
        initUserPage().then()
        $('#newShopModal .close').click()
    })
})

