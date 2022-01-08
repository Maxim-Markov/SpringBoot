let formDelete = document.getElementsByName("DeleteForm")[0];//form in modal
const token = $('#csrfDelete').attr('value');

formDelete.onsubmit = async (e) => {
    e.preventDefault();
    let id = formDelete[0].value;
    let response = await fetch('/admin/' + id, {
            headers: {
                'X-CSRF-TOKEN': token
            },
        credentials: 'include',
        method: 'DELETE',
    });
        if (!response.ok) {
            alert("You cannot delete yourself");
            formDelete.querySelector('.close').click()
            return;
        }
        await response.text();
        let rows = document.getElementsByClassName("usersRow");
        for (let i = 0; i < rows.length; i++) {
            if (rows[i].cells[0].textContent === id) {
                let rowToDelete = rows[i];
                rowToDelete.parentNode.removeChild(rowToDelete);
                modalWindow();
            }
        }
        formDelete.querySelector('.close').click();
}
