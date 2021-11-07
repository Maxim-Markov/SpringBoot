let formsDelete = document.getElementsByName("DeleteForm");

for (let i = 0; i < formsDelete.length; i++) {
    formsDelete[i].onsubmit = async (e) => {
        e.preventDefault();
        let id = formsDelete[i][0].value;
        let response = await fetch('/admin/' + id, {
            credentials: 'include',
            method: 'DELETE',
        });
        await response.text();
        let rows = document.getElementsByClassName("usersRow");
        for (let i = 0; i < rows.length; i++) {

            if (rows[i].cells[0].textContent === id) {
                let rowId = rows[i];
                rowId.parentNode.removeChild(rowId);
            }
        }
    };
}
