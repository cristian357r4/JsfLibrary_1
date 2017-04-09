/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkValue(form, message) {
    
    var userInput = form[form.id + ":userName"];
    
    if (userInput.value ==''){
        alert(message)
        userInput.focus();
        return false;
    } 
    
    return true;
}

function showProgress(data) {
    if (data.status == "begin") {
        document.getElementById('loading_wrapper').style.display = "block";
//        document.getElementById('booksCount').style.display = "none";
//        document.getElementById('pages_top').style.display = "none";
//        document.getElementById('booksList').style.display = "none";
//        document.getElementById('pages_bottom').style.display = "none";

        document.getElementById('books_panel').style.display = "none";

        document.getElementById('loading_image').style.display = "block";
    } else if (data.status == "success") {
        document.getElementById('loading_wrapper').style.display = 'none';
        document.getElementById('loading_image').style.display = 'none';
//        var timer2 = setTimeout("document.getElementById('booksCount').style.display = 'block'", 500);
//        var timer2 = setTimeout("document.getElementById('pages_top').style.display = 'block'", 500);
////        var timer2 = setTimeout("document.getElementById('booksList').style.display = 'block'", 500);
//        var timer2 = setTimeout("document.getElementById('pages_bottom').style.display = 'block'", 500);

        document.getElementById('books_panel').style.display = "block";
        
//        document.getElementById('loading_wrapper').style.display = 'none';
    }
}

function showLoadingImage() {
    document.getElementById("login_form:loading_image").style.display = "block";
    var timer = setTimeout("document.getElementById('login_form:loading_image').style.display = 'block'", 500);
}

function test(message) {
    return prompt(message, 0);
}