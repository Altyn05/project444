
const dropdownCategoryBtn = document.querySelector('#dropdownCategoryButton')
const categoryLinkArr = document.querySelectorAll('.category-link')
const categoryReset = document.querySelector('#category-reset')
const searchInput = document.querySelector('#search-input')
const searchBtn = document.querySelector('#search-button')
const itemSizeBlock = document.querySelector('#item-size')
const shopSizeBlock = document.querySelector('#shop-size')
const itemHeader = document.querySelector('#item-header')
const shopHeader = document.querySelector('#shop-header')
const itemBlock = document.querySelector('#item-block')
const shopBlock = document.querySelector('#shop-block')
const itemPages = document.querySelector('#item-pages')
const shopPages = document.querySelector('#shop-pages')

const ITEM_SIZE = [4, 12, 24, 48, 2_147_483_647]
const SHOP_SIZE = [6, 12, 24, 48, 2_147_483_647]

const ITEM_TEXT = [
    'Подборка популярных товаров',
    'Подборка товаров из категории ',
    'Поиск товаров',
    'Поиск товаров в категории '
]
const SHOP_TEXT = [
    'Подборка популярных магазинов',
    'Подборка магазинов из категории ',
    'Поиск магазинов',
    'Поиск магазинов в категории '
]

let currentItemSize = ITEM_SIZE[0]
let currentShopSize = SHOP_SIZE[0]
let currentItemPage = 1
let currentShopPage = 1
let totalItemPages = 0
let totalShopPages = 0
let categoryName = ''
let categoryId = 0
let searchText = ''
let itemArr = []
let shopArr = []

renderSizeBlock('items')
renderSizeBlock('shops')
addSizeBlockListener('items')
addSizeBlockListener('shops')
addPageBlockListener('items')
addPageBlockListener('shops')
getData('items')
getData('shops')


categoryReset.addEventListener('click', e => {
    e.preventDefault()
    categoryName = ''
    categoryId = 0
    searchText = ''
    searchInput.value = ''
    dropdownCategoryBtn.textContent = 'Каталог'
    getData('items')
    getData('shops')
})

categoryLinkArr.forEach(link => link.addEventListener('click', e => {
    e.preventDefault()
    categoryName = e.target.text
    categoryId = e.target.dataset.catId
    dropdownCategoryBtn.textContent = categoryName
    getData('items')
    getData('shops')
}))

searchBtn.addEventListener('click', e => {
    e.preventDefault()
    searchText = searchInput.value
    getData('items')
    getData('shops')
})
