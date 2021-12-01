

async function openSettings() {
    userProfile()
    let settings = document.querySelector('#pills-settings-tab')
    let tab_settings = new bootstrap.Tab(settings)
    tab_settings.show()
}

function openShops() {
    let settings = document.querySelector('#pills-shops-tab')
    let tab_settings = new bootstrap.Tab(settings)
    tab_settings.show()
}

function openOrders() {
    showUserOrders()
    let settings = document.querySelector('#pills-orders-tab')
    let tab_settings = new bootstrap.Tab(settings)
    tab_settings.show()
}

