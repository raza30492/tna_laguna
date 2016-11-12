function checkNotice(){
    var title = document.form.title.value;
    var notice = document.form.notice.value;
    
    //alert(notice);
    
    if(title === ""){
        document.form.title.focus();
        document.getElementById("errorBox").innerHTML = "Title cannot be blank";
        return false;
    }
    
    if(notice === ""){
        document.form.notice.focus();
        document.getElementById("errorBox").innerHTML = "Content of Notice cannot be blank";
        return false;
    }
}


