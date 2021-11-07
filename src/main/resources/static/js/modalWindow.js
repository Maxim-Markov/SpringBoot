modalWindow();
function modalWindow() {
// Get the modal
    let modals = document.getElementsByClassName("modal");
// Get the button that opens the modal
    let btns = document.getElementsByClassName("modal-btn");

    let span;
// Get the <span> element that closes the modal
    for (let i = 0; i < modals.length * 2; i++) {
        let j = Math.trunc(i / 2);
        span = document.getElementsByClassName("close")[i];
        span.onclick = function () {
            modals[j].style.display = "none";
        }
    }

    for (let i = 0; i < btns.length; i++) {
        // When the user clicks the button, open the modal
        btns[i].onclick = function () {
            let form = modals[i % 2].getElementsByTagName("form")[0];
            let row = document.getElementsByClassName("usersRow")[Math.trunc(i / 2)];
            for (let j = 0; j < 6; j++) {
                form[j].value = row.cells[j].textContent;
            }
            modals[i % 2].style.display = "inline-block";
        }

    }


// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        for (let i = 0; i < modals.length; i++) {
            if (event.target == modals[i]) {
                modals[i].style.display = "none";
            }
        }
    }
}



