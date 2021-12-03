let formAdd = document.getElementsByName("addForm")[0];//form in modal
let rows = document.getElementsByClassName("usersRow");//table with users

const addToken = $('#csrfAdd').attr('value');

formAdd.onsubmit = async (e) => {
    e.preventDefault();
    let user = {
        name: $("#addName").val(),
        lastName: $("#addLastName").val(),
        age: $("#addAge").val(),
        email: $("#addEmail").val(),
        pass: $("#addPassword").val(),
        roles: [
            {role:$("#addRole").val()[0],},
        ],
    }

    fetch('/admin', {
        credentials: 'include',
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': addToken,
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user),
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
                    <td ></td>
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
            formAdd.reset();
            alert("user successfully added")
            })
        })
        .catch(error => alert(`Ошибка: ${error.message}`));
}






