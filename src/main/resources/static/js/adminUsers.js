const usersList = document.getElementById('usersList');
const searchBarUser = document.getElementById('searchBarUsers');
let hpUser = [];
let hpRoles = [];

const editFormUser = document.querySelector('.editFormUser')
const btnSubUser = document.querySelector('.subBTNUser')

const deleteFormUser = document.querySelector('.deleteFormUser')
const btnDelUser = document.querySelector('.delBTNUser')

const btnCreateUser = document.querySelector('.createBTNUser')

const urlUser = "http://localhost:8888/adminapi/users";
const urlRole = "http://localhost:8888/adminapi/roles";


searchBarUser.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();
    const filtered = hpUser.filter((entity) => {
        return (
            entity.firstName.toLowerCase().includes(searchString) ||
            entity.lastName.toLowerCase().includes(searchString) ||
            entity.username.toLowerCase().includes(searchString) ||
            entity.email.toLowerCase().includes(searchString) ||
            JSON.stringify(entity.shops, ['name']).replace(/[^a-zа-яё ]/iug, "")
                .replaceAll("name", "").toLowerCase().includes(searchString) ||
            JSON.stringify(entity.roles, ['name']).replace(/[^a-zа-яё ]/iug, "")
                .replaceAll("name", "").replaceAll("ROLE_", "")
                .toLowerCase().includes(searchString) ||
            JSON.stringify(entity.id).toLowerCase().includes(searchString)
        );
    });
    displayUsers(filtered);
    loadUsersModals(filtered);
});

const loadUsers = async () => {
    const res = await fetch(urlUser);
    hpUser = await res.json();
    displayUsers(hpUser);
    loadUsersModals(hpUser)
    selectUsers(hpUser)
};

const loadRoles = async () => {
    const res = await fetch(urlRole);
    hpRoles = await res.json();
    displayRoles(hpRoles)
};
loadRoles().then();

const displayRoles = (list) => {
    document.getElementById('inputRoleCreateUser').innerHTML = list
        .map((role) => {
            return `
            <option value="${role.name}">${role.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRoleDeleteUser').innerHTML = list
        .map((role) => {
            return `
            <option value="${role.name}">${role.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRoleEditUser').innerHTML = list
        .map((role) => {
            return `
            <option value="${role.name}">${role.name}</option>
        `;
        })
        .join('');

};


const loadUsersModals = (list) => {
    list.forEach(entity => {
        let mass = [];
        for (let i = 0; i < entity.roles.length; i++) {
            mass.push(entity.roles[i].name)
        }
        const btnEdit = document.querySelector(`#dataIdUser${entity.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormUser.id.value = entity.id
            editFormUser.name.value = entity.username
            editFormUser.email.value = entity.email
            editFormUser.password.value = entity.password
            editFormUser.phone.value = entity.phone
            editFormUser.firstname.value = entity.firstName
            editFormUser.lastname.value = entity.lastName
            editFormUser.roles.value = mass[0]
        })

        const btnDelete = document.querySelector(`#dataIdUser${entity.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormUser.id.value = entity.id
            deleteFormUser.name.value = entity.firstName
            deleteFormUser.email.value = entity.email
            deleteFormUser.password.value = entity.password
            deleteFormUser.phone.value = entity.phone
            deleteFormUser.firstname.value = entity.firstName
            deleteFormUser.lastname.value = entity.lastName
            deleteFormUser.roles.value = mass[0]
            // deleteFormUser.activate.value = entity.activate
        })
    })
};

const displayUsers = (list) => {
    usersList.innerHTML = list
        .map((user) => {
            const regex = /[^a-zа-яё ]/iug;
            let uShops = JSON.stringify(user.shops, ['name']).replace(regex, "")
                .replace("name", "").replaceAll("name", "\n");
            let uRoles = JSON.stringify(user.roles, ['name']).replace(regex, "")
                .replace("name", "").replaceAll("name", "\n")
                .replaceAll("ROLE_", "");
            return `
            <tr id="dataIdUser${user.id}">
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${uShops}</td>
                <td>${uRoles}</td>
                <td>${user.activate}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalUser">Редактировать</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalUser">Удалить</button></td>
            </tr>
        `;
        })
        .join('');
};

const selectUsers = (users) => {
    document.getElementById('inputUserCreateShop').innerHTML = users
        .map((c) => {
            return `
            <option value="${c.id}">${c.username}</option>
        `;
        })
        .join('');
    document.getElementById('inputUserEditShop').innerHTML = users
        .map((c) => {
            return `
            <option value="${c.id}">${c.username}</option>
        `;
        })
        .join('');
    document.getElementById('inputUserDeleteShop').innerHTML = users
        .map((c) => {
            return `
            <option value="${c.id}">${c.username}</option>
        `;
        })
        .join('');
}

btnDelUser.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdUser').value;
    let delURL = urlUser + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })

})

btnSubUser.addEventListener('click', async (e) => {
    e.preventDefault();
    let selected = Array.from(document.querySelector(".editFormUser").roles.options)
        .filter(option => option.selected)
        .map(option => option.value);
    await fetch(urlUser, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdUser').value,
            username: document.getElementById('editNameUser').value,
            email: document.getElementById('editEmailUser').value,
            password: document.getElementById('editPasswordUser').value,
            phone: document.getElementById('editPhoneUser').value,
            firstName: document.getElementById('editFirstNameUser').value,
            lastName: document.getElementById('editLastNameUser').value,
            activate: document.getElementById('editActivateUser').checked,
            roles: selected
        })
    }).then(res => {
        res.json()
        loadUsers();
        loadShops();
        loadItems();
    })
})

btnCreateUser.addEventListener('click', async (e) => {
    e.preventDefault();
    let selected = Array.from(document.querySelector(".createFormUser").roles.options)
        .filter(option => option.selected)
        .map(option => option.value);
    await fetch(urlUser, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById('createNameUser').value,
            email: document.getElementById('createEmailUser').value,
            password: document.getElementById('createPasswordUser').value,
            phone: document.getElementById('createPhoneUser').value,
            firstName: document.getElementById('createFirstNameUser').value,
            lastName: document.getElementById('createLastNameUser').value,
            activate: document.getElementById('createActivateUser').checked,
            roles: selected
        })
    }).then(res => {
        res.json();
        loadUsers();
        loadShops();
        loadItems();
    })
})


loadUsers().then();