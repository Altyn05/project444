const categoriesList = document.getElementById('categoriesList');
const searchBarCategory = document.getElementById('searchBarCategories');
let hpCategory = [];

const editFormCategory = document.querySelector('.editFormCategory')
const btnSubCategory = document.querySelector('.subBTNCategory')

const deleteFormCategory = document.querySelector('.deleteFormCategory')
const btnDelCategory = document.querySelector('.delBTNCategory')

const btnCreateCategory = document.querySelector('.createBTNCategory')

const urlCategory = "http://localhost:8888/adminapi/categories";



searchBarCategory.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filtered = hpCategory.filter((entity) => {
        return (
            entity.name.toLowerCase().includes(searchString) ||
            JSON.stringify(entity.id).toLowerCase().includes(searchString)
        );
    });
    displayCategories(filtered);
    loadCategoriesModals(filtered)
});

const loadCategories = async () => {
    const res = await fetch(urlCategory);
    hpCategory = await res.json();
    displayCategories(hpCategory);
    loadCategoriesModals(hpCategory)
    selectCategories(hpCategory)
};

const  selectCategories = (categories) => {
    document.getElementById('inputCategoriesCreateItem').innerHTML = categories
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCategoriesDeleteItem').innerHTML = categories
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCategoriesEditItem').innerHTML = categories
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');
}

const loadCategoriesModals = (list) => {
    list.forEach(entity => {
        const btnEdit = document.querySelector(`#dataIdCategory${entity.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormCategory.id.value = entity.id
            editFormCategory.name.value = entity.name
        })

        const btnDelete = document.querySelector(`#dataIdCategory${entity.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormCategory.id.value = entity.id
            deleteFormCategory.name.value = entity.name
        })
    })
};

const displayCategories = (list) => {
    categoriesList.innerHTML = list
        .map((category) => {
            return `
            <tr id="dataIdCategory${category.id}">
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalCategory">Редактировать</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalCategory">Удалить</button></td>
            </tr>
        `;
        })
        .join('');
};

btnDelCategory.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdCategory').value;
    let delURL = urlCategory + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadCategories();
        loadItems();
        loadUsers();
    })

})

btnSubCategory.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCategory, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdCategory').value,
            name: document.getElementById('editNameCategory').value,
        })
    }).then(res => {
        res.json()
        loadCategories();
        loadItems();
        loadUsers();
    })
})

btnCreateCategory.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCategory, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('createNameCategory').value,
        })
    }).then(res => {
        res.json();
        loadCategories();
        loadItems();
        loadUsers();
    })
})

loadCategories().then();