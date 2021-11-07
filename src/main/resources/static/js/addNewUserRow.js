let formsAdd = document.getElementsByName("addForm");
let rows = document.getElementsByClassName("usersRow");//table with users

formsAdd[0].onsubmit = async (e) => {
    e.preventDefault();
    //let id = formsDelete[i][0].value;
    let response = await fetch('/admin', {
        credentials: 'include',
        method: 'POST',
        body: new FormData(formsAdd[0]),
    });
    let user = await response.json();
    let html = ` <tr class="usersRow">
                                            <td >${user.id}</td>
                                            <td >${user.name}</td>
                                            <td >${user.lastName}</td>
                                            <td >${user.age}</td>
                                            <td >${user.email}</td>
                                            <td >${user.password}</td>
                                            <td >${user.roles[0].role}</td>
                                            <td>
                                                <button class="btn btn-info modal-btn">Edit</button>                                
                                            </td>
                                            <td>
                                                <button class="btn btn-danger modal-btn">Delete</button>
                                            </td>
                                        </tr>`
    rows[rows.length - 1].insertAdjacentHTML("afterEnd", html);
    modalWindow();
}






