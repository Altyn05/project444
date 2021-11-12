const itemsList = document.getElementById('itemsList');
const searchBarItem = document.getElementById('searchBarItems');
let hpItem = [];

const editFormItem = document.querySelector('.editFormItem')
const btnSubItem = document.querySelector('.subBTNItem')

const deleteFormItem = document.querySelector('.deleteFormItem')
const btnDelItem = document.querySelector('.delBTNItem')

const btnCreateItem = document.querySelector('.createBTNItem')

const urlItem = "http://localhost:8888/adminapi/items";


searchBarItem.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filtered = hpItem.filter((entity) => {
        return (
            entity.name.toLowerCase().includes(searchString) ||
            entity.description.toLowerCase().includes(searchString) ||
            entity.shop.name.toLowerCase().includes(searchString) ||
            JSON.stringify(entity.categories, ['name']).replace(/[^a-zа-яё ]/iug, "")
                .replaceAll("name", "").toLowerCase().includes(searchString) ||
            JSON.stringify(entity.id).toLowerCase().includes(searchString)
        )
    });
    displayItems(filtered);
    loadItemsModals(filtered)
});

const loadItems = async () => {
    const res = await fetch(urlItem);
    hpItem = await res.json();
    displayItems(hpItem);
    loadItemsModals(hpItem)
};

const loadItemsModals = (list) => {
    list.forEach(entity => {
        let mass = [];
        for (let i = 0; i < entity.categories.length; i++) {
            mass.push(entity.categories[i].name)
        }
        // console.log(mass)
        const btnEdit = document.querySelector(`#dataIdItem${entity.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormItem.id.value = entity.id
            editFormItem.name.value = entity.name
            editFormItem.price.value = entity.price
            editFormItem.description.value = entity.description
            editFormItem.shop.value = entity.shop.id
            editFormItem.categories.value = mass
            // editFormItem.categories[1].value = mass[1]
        })

        const btnDelete = document.querySelector(`#dataIdItem${entity.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormItem.id.value = entity.id
            deleteFormItem.name.value = entity.name
            deleteFormItem.price.value = entity.price
            deleteFormItem.description.value = entity.description
            deleteFormItem.shop.value = entity.shop.id
            deleteFormItem.categories.value = mass[0]
        })
    })
};


const displayItems = (list) => {
    itemsList.innerHTML = list
        .map((item) => {
            let regexp = /[^a-zа-яё ]/iug;
            let sCategories = JSON.stringify(item.categories, ['name']).replace(regexp, "")
                .replace("name", "").replaceAll("name", "\n");
            return `
            <tr id="dataIdItem${item.id}">
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${sCategories}</td>
                <td>${JSON.stringify(item.rating).substring(0, 5)}</td>
                <td>Des</td>
                <td>${item.shop.name}</td>
                <td id="editItemTD"><button id="editItemBTN" type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalItem">Редактировать</button></td>
                <td id="deleteItemTD"><button id="deleteItemBTN" type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalItem">Удалить</button></td>
            </tr>
        `;
        })
        .join('');
};

btnDelItem.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdItem').value;
    let delURL = urlItem + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })

})

btnSubItem.addEventListener('click', async (e) => {
    e.preventDefault();
    let selected = Array.from(document.querySelector(".editFormItem").categories.options)
        .filter(option => option.selected)
        .map(option => option.value);
    await fetch(urlItem, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdItem').value,
            name: document.getElementById('editNameItem').value,
            price: document.getElementById('editPriceItem').value,
            description: document.getElementById('editDescriptionItem').value,
            categories: selected,
            rating: 0,
            shop: {
                name: document.getElementById('inputShopEditItem').value
            }
        })
    }).then(res => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })
})

btnCreateItem.addEventListener('click', async (e) => {
    e.preventDefault();
    let selected = Array.from(document.querySelector(".createFormItem").categories.options)
        .filter(option => option.selected)
        .map(option => option.value);
    await fetch(urlItem, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('createNameItem').value,
            price: document.getElementById('createPriceItem').value,
            description: document.getElementById('createDescriptionItem').value,
            rating: 0,
            categories: selected,
            shop: {
                name: document.getElementById('inputShopCreateItem').value
            }
        })
    }).then(res => {
        res.json();
        loadUsers();
        loadShops();
        loadItems();
    })
})

loadItems().then();