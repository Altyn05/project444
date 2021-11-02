function showUserShops() {
    const rowsToDelete = document.querySelectorAll('.DELSHOP')
    rowsToDelete.forEach(row => row.remove())

    console.log(user.shops)
    console.log(user)
    for(let shop of user.shops) {

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


