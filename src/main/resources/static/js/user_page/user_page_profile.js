function userProfile() {
    tempUser = JSON.parse(JSON.stringify(user));

    $('.newfoto').val(null)
    document.loadfoto.src = 'https://www.avito.st/stub_avatars/А/10_256x256.png'

    let userProfile = document.querySelectorAll('.profile')
    userProfile[0].value = user.firstName;
    userProfile[1].value = user.lastName;
    userProfile[2].value = user.email;
    userProfile[3].value = user.phone;
    userProfile[4].value = user.age;
    userProfile[5].value = user.birthday
    if(user.gender === "MALE") userProfile[6].setAttribute("selected", "selected")
        else userProfile[7].setAttribute("selected", "selected")
    userProfile[8].value = user.password;
    editAddresses()
}

async function handleClickSubmitProfile() {
    extractUser(document.querySelectorAll('.profile'))
    //    await loadFoto()
    updateUser()
    openShops()
}

//////////////// Extract user from the form
function extractUser(form) {
    tempUser.gender = "FEMALE"
    if (form[6].selected) tempUser.gender = "MALE"
    tempUser.firstName = form[0].value
    tempUser.lastName =  form[1].value
    tempUser.email =  form[2].value
    tempUser.phone =  form[3].value
    tempUser.age =  form[4].value
    tempUser.birthday = form[5].value +"T21:00:00.001+00:00"
    tempUser.password = form[8].value
}

/////////// Load new Foto
function loadFoto() {
    let fileInput = document.querySelector(".newfoto")
    if (fileInput.files[0] == undefined) return
    return new Promise((resolve, reject) => {
        let reader = new FileReader();
        reader.onload = () => {
            document.loadfoto.src = reader.result
            let res = reader.result.replace(/data:image.*,/, "")
            tempUser.images.push({ id: null, url: "https://www.avito.st/stub_avatars/А/10_256x256.png", picture: res, isMain: false })
            resolve()
        }
        reader.onerror = reject
        reader.readAsDataURL(fileInput.files[0]);
    })
}

function fotoEdit() {
    const rowsToDelete = document.querySelectorAll('.DELFOTO')
    rowsToDelete.forEach(row => row.remove())

    let editFoto = document.querySelector('.editFotoModal')
    let i = 0;
    let isMain = ""
    for (let image of user.images) {
        src = "data:image/png;base64," + image.picture
        if (image.isMain === true) isMain = "checked"
        else isMain = ""

        let varHTML =
            " <tr class = \"DELFOTO\">\n" +
            "                                            <td>\n" +
            "                                                <img style=\"border-radius: 50%; max-width: 40px\" src=\"" + src + "\" class=\"img mx-2\" alt=\"\">\n" +
            "                                            </td>\n" +
            "                                            <td>\n" +
            "                                                <div class=\"form-check\" style=\"padding-left: 100px\">\n" +
            "                                                    <input class=\"isMain form-check-input\" name = \"radio\" type=\"radio\" id =\"radio[" + i + "]\" " + isMain + ">\n" +
            "                                                    <label class=\"form-check-label\" for=\"radio[" + i + "]\">\n" +
            "                                                        Сделать главной\n" +
            "                                                    </label>\n" +
            "                                                </div>\n" +
            "                                            </td>\n" +
            "                                            <td>\n" +
            "                                                <div class=\"form-check\" style=\"padding-left: 40px\">\n" +
            "                                                    <input class=\"toDelete form-check-input\" type=\"checkbox\" id =\"check[" + i + "]\" " + false + ">\n" +
            "                                                    <label class=\"form-check-label\" for=\"check[" + i + "]\">\n" +
            "                                                        Удалить\n" +
            "                                                    </label>\n" +
            "                                                </div>\n" +
            "                                            </td>\n" +
            "                                        </tr>"


        editFoto.insertAdjacentHTML('beforeend', varHTML);
        i++
    }
}

function saveFoto() {
    let main = document.querySelectorAll('.isMain')
    let i = 0
    for (let isMain of main) {
        if (isMain.checked === true) tempUser.images[i].isMain = true
        else tempUser.images[i].isMain = false
        i++
    }

    let toDelete = document.querySelectorAll('.toDelete')
    i = 0
    for (let isDelete of toDelete) {
        if (isDelete.checked === true) { tempUser.images.splice(i, 1); i-- }
        i++
    }
}

function editAddresses() {
    const rowsToDelete = document.querySelectorAll('.addr21addr22')
    rowsToDelete.forEach(row => row.remove())

    writeAddresses(user.address, "addr21", "addr22")
    let country = document.querySelectorAll('.countryaddr21addr22')
    for (let cntry of country) cntry.insertAdjacentHTML('afterend', "<input type=\"text\" class = \"inputCountry\" >введите страну")
    let city = document.querySelectorAll('.cityaddr21addr22')
    for (let cty of city) cty.insertAdjacentHTML('afterend', "<input type=\"text\" class = \"inputCity\" >введите город")
    let street = document.querySelectorAll('.streetaddr21addr22')
    for (let strt of street) strt.insertAdjacentHTML('afterend', "<p><input type=\"text\" class = \"inputStreet\">введите улицу" +
        "<input type=\"text\" class = \"inputBuilding\">введите дом" +
        "                                                <div class=\"form-check\" style=\"padding-left: 100px\">\n" +
        "                                                    <input class=\"delAddr form-check-input\" name = \"check\" type=\"checkbox\" id =\"" + strt + "\" >\n" +
        "                                                    <label class=\"form-check-label\" for=\"" + strt + "\" >\n" +
        "                                                        Исключить этот адрес\n" +
        "                                                    </label>\n" +
        "                                                </div>\n" +
        "</p>")
    ////////Add new address
    let newLi = document.createElement('li')
    newLi.className = "DEL nav-item addr21addr22"
    let newA = document.createElement('a')
    newA.className = "DEL nav-link addr21addr22"
    newA.setAttribute('data-toggle', "tab")
    newA.href = "#addr21new"
    newA.text = "Добавить новый адрес"
    newLi.appendChild(newA)
    document.querySelector('.addr21').appendChild(newLi)

    let newDiv = document.createElement('div')
    newDiv.className = "DEL tab-pane addr21addr22"
    newDiv.id = "addr21new"
    newDiv.insertAdjacentHTML('beforeend', "<input type=\"text\" class = \"inputCountry\" >_введите страну")
    newDiv.insertAdjacentHTML('beforeend', "<input type=\"text\" class = \"inputCity\" >_введите город")
    newDiv.insertAdjacentHTML('beforeend', "<p><input type=\"text\" class = \"inputStreet\" >_введите улицу_" +
        "<input type=\"text\" class = \"inputBuilding\">_введите дом</p>")

    document.querySelector('.addr22').appendChild(newDiv)



}

function saveAddresses() {
    let i = 0
    let j = 0
    for (addr of user.address) {
        let country = $('#addr21' + i).find('.inputCountry')[0].value
        let city = $('#addr21' + i).find('.inputCity')[0].value
        let street = $('#addr21' + i).find('.inputStreet')[0].value
        let building = $('#addr21' + i).find('.inputBuilding')[0].value
        let isDelete = $('#addr21' + i).find('.delAddr')[0].checked

        if (country !== "") tempUser.address[i].country = country
        if (city !== "") tempUser.address[i].city = city
        if (street !== "") tempUser.address[i].street = street
        if (building !== "") tempUser.address[i].house = building
        if (isDelete === true) { tempUser.address.splice(j, 1); j--}
        i++
        j++
    }

    let country = $('#addr21new').find('.inputCountry')[0].value
    let city = $('#addr21new').find('.inputCity')[0].value
    let street = $('#addr21new').find('.inputStreet')[0].value
    let building = $('#addr21new').find('.inputBuilding')[0].value

    if (country !== "" | city !== "" | street !== "" | building !== "") {
        tempUser.address.push({ id: null, cityIndex: null, street: null, house: null, city: null, country: null })
        tempUser.address[j].country = country
        tempUser.address[j].city = city
        tempUser.address[j].street = street
        tempUser.address[j].house = building
    }
}