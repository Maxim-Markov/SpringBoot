const table_element = document.getElementById('table');
const pagination_element = document.getElementById('pagination');
const keyOfCurrentPage = "?currPage="
const keyCountOfItems = "&items=";

let url;
let totalCountOfItems;
let valueOfCurrentPage = 1;
let valueOfCountItems;

async function sendUrlAndNumberOfRows(sentUrl, rowsCount) {
    url = sentUrl
    valueOfCountItems = rowsCount
    totalCountOfItems = await getTotalCountOfItems()
    await displayList(table_element, valueOfCurrentPage)
    await setupPagination(pagination_element)
}

async function getTotalCountOfItems() {
    let getTotalItems = await fetch(url + keyOfCurrentPage + valueOfCurrentPage, {
        method: "GET",
        credentials: 'include',
        contentType: "application/json",
    })
    let getTotalItemsObject = await getTotalItems.json()
    return getTotalItemsObject['totalResultCount']
}

async function getItemsForPage() {
    let itemsForPage = await fetch (url + keyOfCurrentPage + valueOfCurrentPage + keyCountOfItems + valueOfCountItems, {
        method: "GET",
        credentials: 'include',
        contentType: "application/json",
    })
    let itemsOnPage = await itemsForPage.json()
    return itemsOnPage["items"]
}

async function displayList(wrapper, page) {
    const items = await getItemsForPage()
    wrapper.innerHTML = "";
    page--;

    for (let note in items) {
        let tr = document.createElement('tr');

        for (let item in items[note]) {
        let td = document.createElement('td');
        td.classList.add('item');
        td.innerText = items[note][item];

        wrapper.appendChild(td);
        }
        wrapper.appendChild(tr)
    }
}

async function setupPagination(wrapper) {
    wrapper.innerHTML = "";

    let page_count = Math.ceil(totalCountOfItems / valueOfCountItems);
    for (let i = 1; i < page_count + 1; i++) {
        let btn = await paginationButton(i);
        wrapper.appendChild(btn);
    }
}

async function paginationButton(page) {
    let button = document.createElement('button');
    button.innerText = page;

    if (valueOfCurrentPage === page) button.classList.add('active');

    button.addEventListener('click', async function () {
        valueOfCurrentPage = page;
        await displayList(table_element, valueOfCurrentPage);

        let current_btn = document.querySelector('.pagenumbers button.active');
        current_btn.classList.remove('active');

        button.classList.add('active');
    });

    return button;
}