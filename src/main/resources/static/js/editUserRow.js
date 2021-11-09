let formEdit = document.getElementsByName("editForm")[0];//form in modal

formEdit.onsubmit = async (e) => {
    e.preventDefault();
    let id = formEdit[0].value;
    let response = await fetch('/admin/' + id, {
        credentials: 'include',
        method: 'PATCH',
        body: new FormData(formEdit),
    });
    if (!response.ok) {
        alert("Such name is already existing");
        return;
    }
    let user = await response.json();
    let rows = document.getElementsByClassName("usersRow");
    for (let i = 0; i < rows.length; i++) {
        if (rows[i].cells[0].textContent === id) {
            let rowToUpdate = rows[i];
            rowToUpdate.cells[0].textContent = id;
            rowToUpdate.cells[1].textContent = user.name;
            rowToUpdate.cells[2].textContent = user.lastName;
            rowToUpdate.cells[3].textContent = user.age;
            rowToUpdate.cells[4].textContent = user.email;
            rowToUpdate.cells[5].textContent = user.password;
            rowToUpdate.cells[6].textContent = user.roles[0].role.slice(5);
        }
    }
    formEdit.querySelector('.close').click();
    $(".navbar-brand").html(`<b>${user.email}</b> with roles ${user.roles[0].role.slice(5)}`);
}