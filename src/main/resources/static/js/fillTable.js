let table = $('#usersTable tbody');//our main table

table.empty();
fetch('/admin/users', {
    credentials: 'include',
}).then(response => response.json())
    .then(users => {
        users.forEach(user => {
            let tableFilling = `$(
                        <tr class="usersRow">
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.password}</td>
                            <td>${user.roles.map(curRole => curRole.role.slice(5)).join(",")}</td>        
                            <td>
                                <button class="btn btn-info modal-btn">Edit</button>
                            </td>
                            <td>
                                <button class="btn btn-danger modal-btn">Delete</button>
                            </td>         
                        </tr>
                )`;
            table.append(tableFilling);
        })
        modalWindow();
    });


