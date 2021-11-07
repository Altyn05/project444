
const itemPages = document.querySelector('#item-pages')
const shopPages = document.querySelector('#shop-pages')

let totalItemPages = 0
let totalShopPages = 0
let currentItemPage = 1
let currentShopPage = 1


addPageBlockListener(itemPages)
addPageBlockListener(shopPages)

function renderPages(block, current, total) {
    let str = `
            <li class="page-item">
                <button class="page-link" aria-label="Previous">&laquo;</button>
            </li>`
    for (let i = 1; i <= total; i++) {
        str += `
                <li class="page-item">
                    <button class="page-link">${i}</button>
                </li>`
    }
    str += `
            <li class="page-item">
                <button class="page-link" aria-label="Next">&raquo;</button>
            </li>
        `
    block.innerHTML = str
    setActivePage(block, current, total)
}

function addPageBlockListener(block) {
    block.addEventListener('click', e => {
        if (e.target.tagName === 'BUTTON') {
            const page = e.target.innerText
            if (block.id === 'item-pages') {
                currentItemPage = +page
                    ? +page
                    : page === '«'
                        ? currentItemPage - 1
                        : currentItemPage + 1
                setActivePage(block, currentItemPage, totalItemPages)
                getPopularItems()
            } else if (block.id === 'shop-pages') {
                currentShopPage = +page
                    ? +page
                    : page === '«'
                        ? currentShopPage - 1
                        : currentShopPage + 1
                setActivePage(block, currentShopPage, totalShopPages)
                getPopularShops()
            }
        }
    })
}

function setActivePage(block, current, total) {
    const arr = block.children
    current === 1
        ? arr[0].classList.add('disabled')
        : arr[0].classList.remove('disabled')
    current === total
        ? arr[arr.length - 1].classList.add('disabled')
        : arr[arr.length - 1].classList.remove('disabled')
    for (let i = 1; i < arr.length - 1; i++) {
        arr[i].innerText === current.toString()
            ? arr[i].classList.add('active')
            : arr[i].classList.remove('active')
    }
}
