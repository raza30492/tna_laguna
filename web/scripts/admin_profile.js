function editProfile(){
    
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    var dob = document.profileform.dob.value;
    var currentPass = document.profileform.currentPass.value;
    var newPass = document.profileform.newPass.value;
    var newPass2 = document.profileform.newPass2.value;
    
    if(dob === ""){
        document.profileform.dob.focus();
        document.getElementById("errorBox").innerHTML = "Date of Birth cannot be empty";
        return false;
    }else if(!dateRegex.test(dob)){
        document.profileform.dob.focus();
        document.getElementById("errorBox").innerHTML = "Invalid date or format";
        return false;
    }
    
    if(currentPass === ""){
        document.profileform.currentPass.focus();
        document.getElementById("errorBox").innerHTML = "Current Password cannot be blank";
        return false;
    }
    
    if(newPass === ""){
        document.profileform.newPass.focus();
        document.getElementById("errorBox").innerHTML = "Enter new password";
        return false;
    }
    
    if(newPass2 === ""){
        document.profileform.newPass2.focus();
        document.getElementById("errorBox").innerHTML = "Re-Enter new password";
        return false;
    }
    if(newPass !== newPass2){
        document.getElementById("erorrBox").innerHTML = "password do not match";
        return false;
    }
    if(newPass.length < 5){
        document.getElementById("errorBox").innerHTML = "password should be at least 5 character long";
        return false;
    }
    
}


