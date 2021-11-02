const shopsList = document.getElementById('shopsList');
const searchBarShop = document.getElementById('searchBarShops');
let hpShop = [];

const editFormShop = document.querySelector('.editFormShop')
const btnSubShop = document.querySelector('.subBTNShop')

const deleteFormShop = document.querySelector('.deleteFormShop')
const btnDelShop = document.querySelector('.delBTNShop')


const btnCreateShop = document.querySelector('.createBTNShop')

const urlShop = "http://localhost:8888/adminapi/shops";


searchBarShop.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filtered = hpShop.filter((entity) => {
        return (
            entity.name.toLowerCase().includes(searchString) ||
            entity.email.toLowerCase().includes(searchString) ||
            entity.description.toLowerCase().includes(searchString) ||
            entity.location.name.toLowerCase().includes(searchString) ||
            entity.user.username.toLowerCase().includes(searchString) ||
            JSON.stringify(entity.rating).toLowerCase().includes(searchString) ||
            JSON.stringify(entity.id).toLowerCase().includes(searchString)
        );
    });
    displayShops(filtered);
    loadShopsModals(filtered)
});

const loadShops = async () => {
    const res = await fetch(urlShop);
    hpShop = await res.json();
    displayShops(hpShop)
    loadShopsModals(hpShop)
    selectShops(hpShop)
};

const selectShops = (shops) => {
    document.getElementById('inputShopCreateItem').innerHTML = shops
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputShopDeleteItem').innerHTML = shops
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputShopEditItem').innerHTML = shops
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
}

const loadShopsModals = (list) => {
    list.forEach(entity => {
        const btnEdit = document.querySelector(`#dataIdShop${entity.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormShop.id.value = entity.id
            editFormShop.name.value = entity.name
            editFormShop.email.value = entity.email
            editFormShop.phone.value = entity.phone
            editFormShop.description.value = entity.description
            editFormShop.country.value = entity.location.id
            editFormShop.user.value = entity.user.id
        })

        const btnDelete = document.querySelector(`#dataIdShop${entity.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormShop.id.value = entity.id
            deleteFormShop.name.value = entity.name
            deleteFormShop.email.value = entity.email
            deleteFormShop.phone.value = entity.phone
            deleteFormShop.description.value = entity.description
            deleteFormShop.country.value = entity.location.id
            deleteFormShop.user.value = entity.user.id
        })
        const btnItems = document.querySelector(`#dataIdShop${entity.id} .btn-warning`);
        btnItems.addEventListener('click', () => {
            displayItemsInShop(entity.items)
        })
    })
};

const displayShops = (list) => {
    shopsList.innerHTML = list
        .map((shop) => {
            return `
            <tr id="dataIdShop${shop.id}">
                <td>${shop.id}</td>
                <td>${shop.name}</td>
                <td>${shop.email}</td>
                <td>${shop.phone}</td>
                <td>${shop.description}</td>
                <td>${shop.location.name}</td>
                <td>${JSON.stringify(shop.rating).substring(0, 5)}</td>
                <td>${shop.user.username}</td>
                <td><button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#itemsModalShop">Товары</button></td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalShop">Редактировать</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalShop">Удалить</button></td>
            </tr>
        `;
        })
        .join('');
};


const displayItemsInShop = (list) => {
    document.getElementById('itemsListShop').innerHTML = list
        .map((item) => {
            let regexp = /[^a-zа-яё ]/iug;
            let sCategories = JSON.stringify(item.categories, ['name']).replace(regexp, "")
                .replace("name", "").replaceAll("name", "\n");
            return `
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${sCategories}</td>
                <td>${JSON.stringify(item.rating).substring(0, 5)}</td>
           
            </tr>
        `;
        })
        .join('');
};

btnDelShop.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdShop').value;
    let delURL = urlShop + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })

})

btnSubShop.addEventListener('click', async (e) => {
    e.preventDefault();
    let shop = {
        id: document.getElementById('editIdShop').value,
        name: document.getElementById('editNameShop').value,
        email: document.getElementById('editEmailShop').value,
        phone: document.getElementById('editPhoneShop').value,
        description: document.getElementById('editDescriptionShop').value,
        location: document.getElementById('inputCountryEditShop').value,
        user: {
            email: document.getElementById('inputUserEditShop').value
        }
    }
    await fetch(urlShop, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(shop)
    }).then(res => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })
})

btnCreateShop.addEventListener('click', async (e) => {
    e.preventDefault();
    let shop = {
        name: document.getElementById('createNameShop').value,
        email: document.getElementById('createEmailShop').value,
        phone: document.getElementById('createPhoneShop').value,
        description: document.getElementById('createDescriptionShop').value,
        userId: document.getElementById('inputUserCreateShop').value,
        location: document.getElementById('inputCountryCreateShop').value,
        user: {
            email: document.getElementById('inputUserCreateShop').value
        }
    }
    await fetch(urlShop, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(shop)
    }).then(res => {
        res.json();
        loadUsers();
        loadShops();
        loadItems();
    })
})

loadShops().then();

