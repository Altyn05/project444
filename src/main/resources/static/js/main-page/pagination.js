
function renderSizeBlock(target) {
    const block = target === 'items' ? itemSizeBlock : shopSizeBlock
    const sizeArr = target === 'items' ? ITEM_SIZE : SHOP_SIZE
    
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
    setActiveSize(target)
}

function addSizeBlockListener(target) {
    const block = target === 'items' ? itemSizeBlock : shopSizeBlock
    const sizeArr = target === 'items' ? ITEM_SIZE : SHOP_SIZE
    
    block.addEventListener('click', e => {
        if (e.target.tagName === 'BUTTON') {
            const text = e.target.innerText
            const size = isNaN(+text) ? sizeArr[sizeArr.length - 1] : +text
            if (target === 'items') {
                currentItemSize = size
                getData('items')
            } else {
                currentShopSize = size
                getData('shops')
            }
            setActiveSize(target)
        }
    })
}

function setActiveSize(target) {
    const arr = target === 'items' ? itemSizeBlock.children : shopSizeBlock.children
    const current = target === 'items' ? currentItemSize : currentShopSize
    
    for (let i = 1; i < arr.length; i++) {
        arr[i].value === current
            ? arr[i].classList.add('active')
            : arr[i].classList.remove('active')
    }
}

function renderPageBlock(target) {
    const block = target === 'items' ? itemPages : shopPages
    const total = target === 'items' ? totalItemPages : totalShopPages

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
    setActivePage(target)
}

function addPageBlockListener(target) {
    const block = target === 'items' ? itemPages : shopPages

    block.addEventListener('click', e => {
        if (e.target.tagName === 'BUTTON') {
            const page = e.target.innerText
            if (target === 'items') {
                currentItemPage = +page
                    ? +page
                    : page === '«'
                        ? currentItemPage - 1
                        : currentItemPage + 1
            } else {
                currentShopPage = +page
                    ? +page
                    : page === '«'
                        ? currentShopPage - 1
                        : currentShopPage + 1
            }
            setActivePage(target)
            getData(target)
        }
    })
}

function setActivePage(target) {
    const arr = target === 'items' ? itemPages.children : shopPages.children
    const current = target === 'items' ? currentItemPage : currentShopPage
    const total = target === 'items' ? totalItemPages : totalShopPages

    current === 1
        ? arr[0].classList.add('disabled')
        : arr[0].classList.remove('disabled')
    current === total || total === 0
        ? arr[arr.length - 1].classList.add('disabled')
        : arr[arr.length - 1].classList.remove('disabled')
    for (let i = 1; i < arr.length - 1; i++) {
        arr[i].innerText === current.toString()
            ? arr[i].classList.add('active')
            : arr[i].classList.remove('active')
    }
}
