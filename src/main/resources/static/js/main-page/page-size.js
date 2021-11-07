
const itemSizeBlock = document.querySelector('#item-size')
const shopSizeBlock = document.querySelector('#shop-size')

const ITEM_SIZE = [4, 12, 24, 48, 2_147_483_647]
const SHOP_SIZE = [6, 12, 24, 48, 2_147_483_647]
let currentItemSize = ITEM_SIZE[0]
let currentShopSize = SHOP_SIZE[0]


renderSizeBlock(itemSizeBlock, ITEM_SIZE, currentItemSize)
renderSizeBlock(shopSizeBlock, SHOP_SIZE, currentShopSize)
addSizeBlockListener(itemSizeBlock, ITEM_SIZE)
addSizeBlockListener(shopSizeBlock, SHOP_SIZE)

function renderSizeBlock(block, sizeArr, current) {
    let str = `
            <li class="page-item disabled">
                <span class="page-link">Показывать:</span>
            </li>`
    sizeArr.forEach(num => {
        str += `
                <li class="page-item" value="${num}">
                    <button class="page-link">${num > 100 ? 'Все' : num}</button>
                </li>`
    })
    block.innerHTML = str
    setActiveSize(block, current)
}

function addSizeBlockListener(block, sizeArr) {
    block.addEventListener('click', e => {
        if (e.target.tagName === 'BUTTON') {
            const text = e.target.innerText
            const size = isNaN(+text) ? sizeArr[sizeArr.length - 1] : +text
            block.id === 'item-size'
                ? currentItemSize = size
                : currentShopSize = size
            setActiveSize(block, size)
            block.id === 'item-size'
                ? getPopularItems()
                : getPopularShops()
        }
    })
}

function setActiveSize(block, current) {
    const arr = block.children
    for (let i = 1; i < arr.length; i++) {
        arr[i].value === current
            ? arr[i].classList.add('active')
            : arr[i].classList.remove('active')
    }
}

