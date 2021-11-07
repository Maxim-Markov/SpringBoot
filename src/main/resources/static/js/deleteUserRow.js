let formDelete = document.getElementsByName("DeleteForm")[0];//form in modal

formDelete.onsubmit = async (e) => {
    e.preventDefault();
    let id = formDelete[0].value;
    let response = await fetch('/admin/' + id, {
        credentials: 'include',
        method: 'DELETE',
    });
    await response.text();
    let rows = document.getElementsByClassName("usersRow");
    for (let i = 0; i < rows.length; i++) {
        if (rows[i].cells[0].textContent === id) {
            let rowToDelete = rows[i];
            rowToDelete.parentNode.removeChild(rowToDelete);
        }
    }
    formDelete.querySelector('.close').click()
}
