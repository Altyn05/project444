function showUserOrders() {
    const rowsToDelete = document.querySelectorAll('.DELORDER')
    rowsToDelete.forEach(row => row.remove())

    for(let order of user.orders) {

        let tr = document.createElement('tr')
        tr.className = "DELORDER"

        let varHTML =
            "<td>\n" +
                "<span>ID заказа: " + order.id + "</span>\n" +
            "</td>\n" +
            "<td>\n" +
                "<span>Статус заказа: " + order.status + "</span>\n" +
            "</td>\n"
        if(order.status === "COMPLETE") document.querySelector('.userOrdersClose').appendChild(tr).insertAdjacentHTML('beforeend', varHTML);
            else document.querySelector('.userOrdersOpen').appendChild(tr).insertAdjacentHTML('beforeend', varHTML);

        for(let item of order.items) {
            varHTML =
                "<td class='DELORDER'>\n" +
                "<img style='border-radius: 50%; max-height: 30px' src = 'data:image/png;base64," + item.images[0].picture + "'/>\n" +
                "</td>\n" +
                "<td class='DELORDER'>\n" +
                "<a href='/item/" + item.id + "'>" + item.name + "</a>\n" +
                "</td>\n" +
                "<td class='DELORDER'>\n" +
                "<span>Цена: " + item.price + "</span>\n" +
                "</td>\n"
            if (order.status === "COMPLETE") document.querySelector('.userOrdersClose').insertAdjacentHTML('beforeend', varHTML);
            else document.querySelector('.userOrdersOpen').insertAdjacentHTML('beforeend', varHTML);
        }
    }
}


