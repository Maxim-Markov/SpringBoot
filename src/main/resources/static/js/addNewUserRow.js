let formsAdd = document.getElementsByName("addForm");//form in modal
let rows = document.getElementsByClassName("usersRow");//table with users

formsAdd[0].onsubmit = async (e) => {
    e.preventDefault();
    fetch('/admin', {
        credentials: 'include',
        method: 'POST',
        body: new FormData(formsAdd[0]),
    })
        .then(response => {
            if(response.status === 400){
                throw new Error("user with such name already exists")
            }
        response.json()
            .then(user => {
            let html = `<tr class="usersRow">
                    <td >${user.id}</td>
                    <td >${user.name}</td>
                    <td >${user.lastName}</td>
                    <td >${user.age}</td>
                    <td >${user.email}</td>
                    <td >${user.password}</td>
                    <td >${user.roles[0].role.slice(5)}</td>
                    <td>
                        <button class="btn btn-info modal-btn">Edit</button>                                
                    </td>
                    <td>
                    <button class="btn btn-danger modal-btn">Delete</button>
                    </td>
                 </tr>`
            rows[rows.length - 1].insertAdjacentHTML("afterEnd", html);
            modalWindow();
            })
        })
        .catch(error => alert(`Ошибка: ${error.message}`));
}






