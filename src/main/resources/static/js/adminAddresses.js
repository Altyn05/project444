// Countries
const countriesList = document.getElementById('countriesList');
const searchBar = document.getElementById('searchBar');
let hpCountry = [];

const editForm = document.querySelector('.editForm')
const btnSub = document.querySelector('.subBTN')

const deleteForm = document.querySelector('.deleteForm')
const btnDel = document.querySelector('.delBTN')

const btnCreate = document.querySelector('.createBTN')

const urlCountry = "http://localhost:8888/adminapi/countries";


searchBar.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filteredCountries = hpCountry.filter((country) => {
        return (
            country.name.toLowerCase().includes(searchString) ||
            JSON.stringify(country.id).toLowerCase().includes(searchString)
        );
    });
    displayCountries(filteredCountries);
    loadCountriesModals(filteredCountries);
});

const loadCountries = async () => {
    const res = await fetch(urlCountry);
    hpCountry = await res.json();
    displayCountries(hpCountry);
    loadCountriesModals(hpCountry);
    selectCountry(hpCountry)
};

const loadCountriesModals = (list) => {
    list.forEach(country => {
        const btnEdit = document.querySelector(`#dataId${country.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editForm.id.value = country.id
            editForm.name.value = country.name
        })

        const btnDelete = document.querySelector(`#dataId${country.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteForm.id.value = country.id
            deleteForm.name.value = country.name
        })
    })
};

const displayCountries = (countries) => {
    countriesList.innerHTML = countries
        .map((country) => {
            return `
            <tr id="dataId${country.id}">
                <td>${country.id}</td>
                <td>${country.name}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal">Ред</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Удл</button></td>
            </tr>
        `;
        })
        .join('');
};

btnDel.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteId').value;
    let delURL = urlCountry + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })

})

btnSub.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCountry, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editId').value,
            name: document.getElementById('editName').value,
        })
    }).then(res => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })
})

btnCreate.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCountry, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('createName').value,
        })
    }).then(res => {
        res.json();
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })
})

loadCountries().then();

//Regions
const regionsList = document.getElementById('regionsList');
const searchBarRegion = document.getElementById('searchBarRegion');
let hpRegion = [];

const editFormRegion = document.querySelector('.editFormRegion')
const btnSubRegion = document.querySelector('.subBTNRegion')

const deleteFormRegion = document.querySelector('.deleteFormRegion')
const btnDelRegion = document.querySelector('.delBTNRegion')

const btnCreateRegion = document.querySelector('.createBTNRegion')

const urlRegion = "http://localhost:8888/adminapi/regions";


searchBarRegion.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filteredRegions = hpRegion.filter((region) => {
        return (
            region.name.toLowerCase().includes(searchString) ||
            region.country.name.toLowerCase().includes(searchString) ||
            JSON.stringify(region.id).toLowerCase().includes(searchString)
        );
    });
    displayRegions(filteredRegions);
    loadRegionsModals(filteredRegions);
});

const loadRegions = async () => {
    const res = await fetch(urlRegion);
    hpRegion = await res.json();
    displayRegions(hpRegion);
    loadRegionsModals(hpRegion);
    modalCreateSelectRegions(hpRegion)
};

const loadRegionsModals = (list) => {
    list.forEach(region => {
        const btnEdit = document.querySelector(`#dataIdRegion${region.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormRegion.id.value = region.id
            editFormRegion.name.value = region.name
            editFormRegion.country.value = region.country.id
        })

        const btnDelete = document.querySelector(`#dataIdRegion${region.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormRegion.id.value = region.id
            deleteFormRegion.name.value = region.name
            deleteFormRegion.country.value = region.country.id
        })
    })
};

const displayRegions = (regions) => {
    regionsList.innerHTML = regions
        .map((region) => {
            return `
            <tr id="dataIdRegion${region.id}">
                <td>${region.id}</td>
                <td>${region.name}</td>
                <td>${region.country.name}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalRegion">Ред</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalRegion">Удл</button></td>
            </tr>
        `;
        })
        .join('');
};


btnDelRegion.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdRegion').value;
    let delURL = urlRegion + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })

})

btnSubRegion.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlRegion, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdRegion').value,
            name: document.getElementById('editNameRegion').value,
            country: document.getElementById('inputCountryEdit').value
        })
    }).then(res => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })
})

btnCreateRegion.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlRegion, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('createNameRegion').value,
            country: document.getElementById('inputCountry').value
        })
    }).then(res => {
        res.json();
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
        loadShops();
    })
})

const modalCreateSelectRegions = (regions) => {
    document.getElementById('inputRegion').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRegionEdit').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRegionDelete').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRegionAddress').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRegionAddressDelete').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputRegionAddressEdit').innerHTML = regions
        .map((region) => {
            return `
            <option value="${region.id}">${region.name}</option>
        `;
        })
        .join('');

}

loadRegions().then();






// Cities

const citiesList = document.getElementById('citiesList');
const searchBarCity = document.getElementById('searchBarCity');
let hpCity = [];

const btnCreateCity = document.querySelector('.createBTNCity')

const editFormCity = document.querySelector('.editFormCity')
const btnSubCity = document.querySelector('.subBTNCity')

const deleteFormCity = document.querySelector('.deleteFormCity')
const btnDelCity = document.querySelector('.delBTNCity')

const urlCity = "http://localhost:8888/adminapi/cities";

searchBarCity.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filteredCities = hpCity.filter((city) => {
        return (
            city.name.toLowerCase().includes(searchString) ||
            city.country.name.toLowerCase().includes(searchString) ||
            JSON.stringify(city.id).toLowerCase().includes(searchString)
        );
    });
    displayCities(filteredCities);
    loadCitiesModals(filteredCities)
});

const loadCities = async () => {
    const res = await fetch(urlCity);
    hpCity = await res.json();
    displayCities(hpCity);
    loadCitiesModals(hpCity)
    modalCreateSelectCities(hpCity)
};

const loadCitiesModals = (list) => {
    list.forEach(city => {
        const btnEdit = document.querySelector(`#dataIdCity${city.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormCity.id.value = city.id
            editFormCity.name.value = city.name
            editFormCity.region.value = city.region.id
            editFormCity.country.value = city.country.id
        })

        const btnDelete = document.querySelector(`#dataIdCity${city.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormCity.id.value = city.id
            deleteFormCity.name.value = city.name
            deleteFormCity.region.value = city.region.id
            deleteFormCity.country.value = city.country.id
        })
    })
};


const displayCities = (cities) => {
    citiesList.innerHTML = cities
        .map((city) => {
            return `
            <tr id="dataIdCity${city.id}">
                <td>${city.id}</td>
                <td>${city.name}</td>
                <td>${city.region.name}</td>
                <td>${city.country.name}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalCity">Ред</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalCity">Удл</button></td>
            </tr>
        `;
        })
        .join('');
};

const selectCountry = (countries) => {
    document.getElementById('inputCountry').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCountryDelete').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCountryEdit').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputAddress').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputAddressDelete').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputAddressEdit').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.name}">${c.name}</option>
        `;
        })
        .join('');

    document.getElementById('inputCityCountry').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');

    document.getElementById('inputCityCountryDelete').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');

    document.getElementById('inputCityCountryEdit').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');

    // shops

    document.getElementById('inputCountryCreateShop').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCountryEditShop').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCountryDeleteShop').innerHTML = countries
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
};


btnCreateCity.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCity, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('createNameCity').value,
            region: document.getElementById('inputRegion').value,
            country: document.getElementById('inputCityCountry').value
        })
    }).then(res => {
        res.json();
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
    })
})

btnDelCity.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdCity').value;
    let delURL = urlCity + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
    })

})

btnSubCity.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlCity, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdCity').value,
            name: document.getElementById('editNameCity').value,
            region: document.getElementById('inputRegionEdit').value,
            country: document.getElementById('inputCityCountryEdit').value
        })
    }).then(res => {
        res.json()
        loadCities();
        loadRegions();
        loadAddresses();
        loadCountries();
    })
})


loadCities().then();


//Addresses

const addressesList = document.getElementById('addressesList');
const searchBarAddress = document.getElementById('searchBarAddress');
let hpAddress = [];

const btnCreateAddress = document.querySelector('.createBTNAddress')

const editFormAddress = document.querySelector('.editFormAddress')
const btnSubAddress = document.querySelector('.subBTNAddress')

const deleteFormAddress = document.querySelector('.deleteFormAddress')
const btnDelAddress = document.querySelector('.delBTNAddress')

const urlAddress = "http://localhost:8888/adminapi/addresses";


const displayAddresses = (addresses) => {
    addressesList.innerHTML = addresses
        .map((address) => {
            return `
            <tr id="dataIdAddress${address.id}">
                <td>${address.id}</td>
                <td>${address.cityIndex}</td>
                <td>${address.country.name}</td>
                <td>${address.region.name}</td>
                <td>${address.city.name}</td>
                <td>${address.street}</td>
                <td>${address.house}</td>
                <td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModalAddress">Ред</button></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModalAddress">Удл</button></td>
            </tr>
        `;
        })
        .join('');
};

searchBarAddress.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    const filteredAddresses = hpAddress.filter((address) => {
        return (
            address.street.toLowerCase().includes(searchString) ||
            address.country.name.toLowerCase().includes(searchString) ||
            address.region.name.toLowerCase().includes(searchString) ||
            address.city.name.toLowerCase().includes(searchString) ||
            address.house.toLowerCase().includes(searchString) ||
            address.cityIndex.toLowerCase().includes(searchString) ||
            JSON.stringify(address.id).toLowerCase().includes(searchString)

        );
    });
    displayAddresses(filteredAddresses)
    loadAddressesModals(filteredAddresses)
});

const loadAddresses = async () => {
    const res = await fetch(urlAddress);
    hpAddress = await res.json();
    displayAddresses(hpAddress)
    loadAddressesModals(hpAddress)
};

const loadAddressesModals = (list) => {
    list.forEach(address => {
        const btnEdit = document.querySelector(`#dataIdAddress${address.id} .btn-info`);
        btnEdit.addEventListener('click', () => {
            editFormAddress.id.value = address.id
            editFormAddress.index.value = address.cityIndex
            editFormAddress.country.value = address.country.name
            editFormAddress.region.value = address.region.id
            editFormAddress.city.value = address.city.id
            editFormAddress.street.value = address.street
            editFormAddress.house.value = address.house
        })

        const btnDelete = document.querySelector(`#dataIdAddress${address.id} .btn-danger`);
        btnDelete.addEventListener('click', () => {
            deleteFormAddress.id.value = address.id
            deleteFormAddress.index.value = address.cityIndex
            deleteFormAddress.country.value = address.country.name
            deleteFormAddress.region.value = address.region.id
            deleteFormAddress.city.value = address.city.id
            deleteFormAddress.street.value = address.street
            deleteFormAddress.house.value = address.house
        })
    })
};

btnCreateAddress.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlAddress, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            cityIndex: document.getElementById('createIndexAddress').value,
            city: document.getElementById('inputCity').value,
            street: document.getElementById('createStreetAddress').value,
            house: document.getElementById('createHouseAddress').value
        })
    }).then(res => {
        res.json();
        loadAddresses()
    })
})

btnDelAddress.addEventListener('click', async (e) => {
    e.preventDefault();
    let id = document.getElementById('deleteIdAddress').value;
    let delURL = urlAddress + '/' + id;
    await fetch(delURL, {
        method: 'DELETE'
    }).then((res) => {
        res.json()
        loadAddresses()
    })

})

btnSubAddress.addEventListener('click', async (e) => {
    e.preventDefault();
    await fetch(urlAddress, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editIdAddress').value,
            cityIndex: document.getElementById('editIndexAddress').value,
            city: document.getElementById('inputCityEdit').value,
            street: document.getElementById('editStreetAddress').value,
            house: document.getElementById('editHouseAddress').value
        })
    }).then(res => {
        res.json()
        loadAddresses()
    })
})

loadAddresses().then();

const modalCreateSelectCities = (cities) => {
    document.getElementById('inputCity').innerHTML = cities
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCityEdit').innerHTML = cities
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
    document.getElementById('inputCityDelete').innerHTML = cities
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');

}

loadAddresses().then();

document.getElementById('inputAddress').addEventListener('change', function () {
    const value = this.value;
    console.log(value)
    let filter = hpCity.filter((cities) => {
        return (
            cities.country.name.includes(value)
        );
    });

    document.getElementById('inputCity').innerHTML = filter
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
})
document.getElementById('inputAddressDelete').addEventListener('change', function () {
    const value = this.value;
    console.log(value)
    let filter = hpCity.filter((cities) => {
        return (
            cities.country.name.includes(value)
        );
    });
    console.log(filter)
    document.getElementById('inputCityDelete').innerHTML = filter
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
})
document.getElementById('inputAddressEdit').addEventListener('change', function () {
    const value = this.value;
    console.log(value)
    let filter = hpCity.filter((city) => {
        return (
            city.country.name.includes(value)
        );
    });
    console.log(filter)
    document.getElementById('inputCityEdit').innerHTML = filter
        .map((c) => {
            return `
            <option value="${c.id}">${c.name}</option>
        `;
        })
        .join('');
})

