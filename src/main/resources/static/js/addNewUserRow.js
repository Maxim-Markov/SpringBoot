

let html = "   <tr class=\"usersRow\">\n" +
    "                                            <td th:text=\"${curUser.getId()}\">Id</td>\n" +
    "                                            <td th:text=\"${curUser.getUsername()}\">Maxim</td>\n" +
    "                                            <td th:text=\"${curUser.getLastName()}\">Markov</td>\n" +
    "                                            <td th:text=\"${curUser.getAge()}\">14</td>\n" +
    "                                            <td th:text=\"${curUser.getEmail()}\">test@gmail.com</td>\n" +
    "                                            <td th:text=\"${curUser.getPassword()}\">pass123</td>\n" +
    "                                            <td th:each=\"role,iter : ${curUser.getRoles()}\" th:text=\"${role.role.replaceFirst('ROLE_','')}+(${!iter.last} ? ', ' : '')\">USER, ADMIN</td>\n" +
    "                                            <td>\n" +
    "                                                <button class=\"btn btn-info modal-btn\">Edit</button>\n" +
    "                                                <!-- Модальном окно -->\n" +
    "                                                <div  class=\"modal\">\n" +
    "                                                    <!-- Модальное содержание -->\n" +
    "                                                    <div class=\"modal-content\">\n" +
    "                                                        <span class=\"h4\" >Edit user</span>\n" +
    "                                                        <span class=\"close rood\">&times;</span>\n" +
    "                                                        <hr/>\n" +
    "                                                        <div class=\"text-center\">\n" +
    "                                                            <form name=\"myForm\" th:method=\"PATCH\" th:action=\"@{/admin/{id}(id=${curUser.getId()})}\" th:object=\"${user}\">\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer0\" class=\"form-label fw-bold m-0\">ID</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control \" th:name=\"id\" th:value = \"${curUser.getId()}\"   id=\"validationServer0\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer06\" class=\"form-label fw-bold m-0\">First name</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control\" th:name=\"name\" th:value = \"${curUser.getName()}\"  id=\"validationServer06\"  required>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer07\" class=\"form-label fw-bold m-0\">Last name</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control\" th:name=\"lastName\" th:value = \"${curUser.getLastName()}\" id=\"validationServer07\"  required>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer08\" class=\"form-label fw-bold m-0\">Age</label>\n" +
    "                                                                    <input type=\"number\" class=\"form-control\" th:name=\"age\" th:value = \"${curUser.getAge()}\" id=\"validationServer08\" th:max=\"255\"  required>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer09\" class=\"form-label fw-bold m-0\">Email</label>\n" +
    "                                                                    <input type=\"email\" class=\"form-control\" th:name=\"email\" th:value = \"${curUser.getEmail()}\" id=\"validationServer09\"   required>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"validationServer10\" class=\"form-label fw-bold m-0\">Password</label>\n" +
    "                                                                    <input type=\"password\" class=\"form-control\" th:name=\"password\" th:value = \"${curUser.getPassword()}\" id=\"validationServer10\" required>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label for=\"editRole\" class=\"form-label fw-bold m-0\">Role</label>\n" +
    "                                                                    <select id=\"editRole\" name=\"roles\" multiple size=\"2\" required class=\"form-control form-control-sm\">\n" +
    "                                                                        <option value=\"ROLE_ADMIN\">ADMIN</option>\n" +
    "                                                                        <option value=\"ROLE_USER\">USER</option>\n" +
    "                                                                    </select>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <hr>\n" +
    "                                                                <div class=\"text-end\">\n" +
    "                                                                    <input type=\"reset\" class=\"btn btn-secondary close\" value=\"Close\">\n" +
    "                                                                    <input type=\"submit\" class=\"btn btn-primary\" value=\"Edit\">\n" +
    "                                                                </div>\n" +
    "                                                            </form>\n" +
    "                                                        </div>\n" +
    "                                                    </div>\n" +
    "                                                </div>\n" +
    "\n" +
    "                                            </td>\n" +
    "                                            <td>\n" +
    "\n" +
    "                                                <button class=\"btn btn-danger modal-btn\">Delete</button>\n" +
    "                                                <!-- Модальном окно -->\n" +
    "                                                <div class=\"modal\">\n" +
    "                                                    <!-- Модальное содержание -->\n" +
    "                                                    <div class=\"modal-content\">\n" +
    "                                                        <span class=\"h4\" >Delete user</span>\n" +
    "                                                        <span class=\"close rood\">&times;</span>\n" +
    "                                                        <hr/>\n" +
    "                                                        <div class=\"text-center\">\n" +
    "                                                            <form name=\"DeleteForm\"  th:object=\"${user}\">\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteId' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">ID</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control \" th:name=\"id\" th:value = \"${curUser.getId()}\" th:id=\"'deleteId' + ${curUserStat.index}\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteName' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">First name</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control \" th:name=\"name\" th:value = \"${curUser.getName()}\" th:id=\"'deleteName' + ${curUserStat.index}\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteLastName' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">Last name</label>\n" +
    "                                                                    <input type=\"text\" class=\"form-control\" th:name=\"lastName\" value = \"20\" th:id=\"'deleteLastName' + ${curUserStat.index}\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteAge' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">Age</label>\n" +
    "                                                                    <input type=\"number\" class=\"form-control\" th:name=\"age\" th:value = \"${curUser.getAge()}\" th:id=\"'deleteAge' + ${curUserStat.index}\" th:max=\"255\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteEmail' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">Email</label>\n" +
    "                                                                    <input type=\"email\" class=\"form-control\" th:name=\"email\" th:value = \"${curUser.getEmail()}\" th:id=\"'deleteEmail' + ${curUserStat.index}\"  disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteNPassword' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">Password</label>\n" +
    "                                                                    <input type=\"password\" class=\"form-control\" th:name=\"password\" th:value = \"${curUser.getPassword()}\" th:id=\"'deletePassword' + ${curUserStat.index}\" disabled>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <div class=\"form-row\">\n" +
    "                                                                    <label th:for=\"'deleteRole' + ${curUserStat.index}\" class=\"form-label fw-bold m-0\">Role</label>\n" +
    "                                                                    <select th:id=\"'deleteRole' + ${curUserStat.index}\" name=\"roles\" multiple size=\"2\" disabled class=\"form-control form-control-sm\">\n" +
    "                                                                        <option value=\"ROLE_ADMIN\">ADMIN</option>\n" +
    "                                                                        <option value=\"ROLE_USER\">USER</option>\n" +
    "                                                                    </select>\n" +
    "                                                                </div>\n" +
    "                                                                <br>\n" +
    "                                                                <hr>\n" +
    "                                                                <div class=\"text-end\">\n" +
    "                                                                    <input type=\"reset\" class=\"btn btn-secondary close\" value=\"Close\">\n" +
    "                                                                        <input type=\"submit\" class=\"btn btn-danger\" value=\"Delete\">\n" +
    "                                                                </div>\n" +
    "                                                            </form>\n" +
    "                                                        </div>\n" +
    "                                                    </div>\n" +
    "                                                </div>\n" +
    "                                            </td>\n" +
    "                                        </tr>"


let formsAdd = document.getElementsByName("addForm");
let rows = document.getElementsByClassName("usersRow");//table with users
let table = document.getElementById("usersTable");//table with users

formsAdd[0].onsubmit = async (e) => {
    e.preventDefault();
    //let id = formsDelete[i][0].value;
    let response = await fetch('/admin', {
        credentials: 'include',
        method: 'POST',
        body: new FormData(formsAdd[0]),
    });
    let user = await response.json();

    rows[rows.length - 1].insertAdjacentHTML("afterEnd", html);
//NOW INCREMENT THE COUNTER BASED ON YOUR LOOP
    modalWindow();
}






