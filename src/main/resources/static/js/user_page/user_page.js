var user
var tempUser

async function initUserPage() {
    user = await getUserPrincipal()
    console.log(user)
    writeUserData()
    showUserShops()
}

function writeUserData() {
    const rowsToDelete = document.querySelectorAll('.DEL')
    rowsToDelete.forEach(row => row.remove())

    userFoto(user.images)
    document.querySelector('.avatar_name').innerHTML = user.firstName + " " + user.lastName;
//    document.user_foto.src = user.images.url   // картинка по URL
//    document.user_foto.src = "data:image/png;base64,"+user.images.picture  // картинка из файла
    let userData = document.querySelectorAll('.user_data')
    userData[0].innerHTML = user.firstName + " " + user.lastName;
    userData[1].innerHTML = "Возраст: " + user.age;
    userData[2].innerHTML = "Пол: " + user.gender;

    writeAddresses(user.address, "addr11", "addr12")

    // userData[3].innerHTML = "Страна: " + user.address.country;
    // userData[4].innerHTML = "Город: " + user.address.city;
    // userData[5].innerHTML = "Улица: " + user.address.street + " дом: "+ user.address.house;

    userData[6].innerHTML = "Почта: " + user.email;
    userData[7].innerHTML = "Телефон: " + user.phone;
}

function userFoto(images) {
    let i = 0
    for (let image of images) {

        if (image.isMain === true) document.avatar_foto.src = "data:image/png;base64," + image.picture  // картинка из файла

        let newLi = document.createElement('li')
        newLi.className = "DEL"
        newLi.setAttribute('data-target', "#carouselIndicators")
        newLi.setAttribute('data-slide-to', i.toString())
        document.querySelector(".carousel-indicators").appendChild(newLi)

        let img = document.createElement("img");
        img.className = "DEL d-block w-100"
        img.style = "border-radius: 50%; border: solid black; max-height:255px; max-width: 255px"
        img.alt = i + "slide"
        img.src = "data:image/png;base64," + image.picture

        let inner = document.createElement("div")
        inner.className = "DEL carousel-item";
        if (i === 0) inner.className = "DEL carousel-item active";
        inner.appendChild(img)

        document.querySelector('.carousel-inner').appendChild(inner)

        i++

    }
}

function writeAddresses(addresses, addr1, addr2) {
    let i = 0
    for (let address of addresses) {
        let newLi = document.createElement('li')
        newLi.className = "DEL nav-item " + addr1 + addr2
        let newA = document.createElement('a')
        newA.className = "DEL nav-link " + addr1 + addr2
        if (i === 0) newA.className = "DEL nav-link active " + addr1 + addr2
        newA.setAttribute('data-toggle', "tab")
        newA.href = "#" + addr1 + i
        newA.text = i.toString()
        newLi.appendChild(newA)
        document.querySelector('.' + addr1).appendChild(newLi)
        let newDiv = document.createElement('div')
        newDiv.className = "DEL tab-pane " + addr1 + addr2
        if (i === 0) newDiv.className = "DEL tab-pane active " + addr1 + addr2
        newDiv.id = addr1 + i.toString()
        let li1 = document.createElement('li')
        li1.className = "country" + addr1 + addr2
        li1.innerHTML = "Страна: " + address.country;
        newDiv.appendChild(li1)
        let li2 = document.createElement('li')
        li2.className = "city" + addr1 + addr2
        li2.innerHTML = "Город: " + address.city;
        newDiv.appendChild(li2)
        let li3 = document.createElement('li')
        li3.className = "street" + addr1 + addr2
        li3.innerHTML = "Улица: " + address.street + " дом: " + address.house;
        newDiv.appendChild(li3)
        document.querySelector('.' + addr2).appendChild(newDiv)

        i++
    }
}

initUserPage()


const urlCats = "http://localhost:8888/api/categories";
let itemPhoto = []
let hpCats = [];
const loadCats = async () => {
    const res = await fetch(urlCats);
    hpCats = await res.json();

};
let phCount = 0;

function loadItemPhoto() {
    loadCats().then()
    console.log("список категорий: " + hpCats)
    let fileInput = document.querySelector(".newPhoto")
    if (fileInput.files[0] == undefined) return
    return new Promise((resolve, reject) => {
        let reader = new FileReader()
        reader.onload = () => {
            let res
            if (phCount < 1) {
                phCount++;
                document.getElementById('pictureItem').innerHTML +=
                    '<div class="carousel-item active"><img src="' + reader.result + '" class="d-block w-50" alt="" ></div>'
                res = reader.result.replace(/data:image.*,/, "")
                itemPhoto.push( {id: null, url: "file", picture: res, isMain: true})
            } else {
                document.getElementById('pictureItem').innerHTML +=
                    '<div class="carousel-item"><img src="' + reader.result + '" class="d-block w-50" alt="" ></div>'
                res = reader.result.replace(/data:image.*,/, "")
                itemPhoto.push({id: null, url: "file", picture: res, isMain: true})
            }
            console.log(itemPhoto)
            resolve()
        }
        reader.onerror = reject
        reader.readAsDataURL(fileInput.files[0]);
    })
}


URLCategories = 'http://localhost:8888/api/categories'

const newItemModalBTN = document.getElementById('newItemMod')

newItemModalBTN.addEventListener('click', () =>{
    loadCategoriesField().then()
})
let hpCat = []
const loadCategoriesField = async () => {
    const res = await fetch(URLCategories);
    hpCat = await res.json();
    selCats(hpCat)
};

const  selCats = (categories) => {
    document.getElementById('categoriesCreateItem').innerHTML = categories
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');

}

URLCreateItem = 'http://localhost:8888/api/items/'
let newItemCreate = document.getElementById('submitNewItem');
let itemProfile = document.querySelectorAll('.createItem');
newItemCreate.addEventListener('submit', (ev) => {
    ev.preventDefault();
    let sel = Array.from(document.querySelector(".createItemClass").categories.options)
        .filter(option => option.selected)
        .map(option => option.value);
    let a = new Promise(function (resolve) {
        resolve(
            fetch(URLCreateItem, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    categories: sel,
                    name: itemProfile[1].value,
                    description: itemProfile[2].value,
                    price: itemProfile[3].value,
                    count: itemProfile[4].value,
                    user: {username:user.username},
                    images:
                        itemPhoto
                })
            })
        )
    })
    a.then(function () {
        initUserPage().then()
        phCount = 0;
        itemPhoto = []
        document.getElementById('pictureItem').innerHTML = ''
        document.getElementById('pictureItem').innerHTML = ''
        $('#newItemModal .close').click()
    })
})