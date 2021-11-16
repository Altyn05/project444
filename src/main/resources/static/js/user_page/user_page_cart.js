let openCart = document.getElementById("openCart").innerHTML

if(openCart === "YES"){
    openCartModal()
}

function openCartModal() {
    let myModal = new bootstrap.Modal(document.getElementById('cart-modal'));
    myModal.show();
}
