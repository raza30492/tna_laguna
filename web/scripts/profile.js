function checkFirstLogin(){
    var passRegex = /[A-Za-z0-9]{5,}/;
    var pass1 = document.form.pass1.value;
    var pass2 = document.form.pass2.value;

    if(pass1 === ""){
        document.form.pass1.focus();
        document.getElementById("errorBox").innerHTML = "Enter new Password";
        return false;
    }else if(!passRegex.test(pass1)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    if(pass2 === ""){
        document.form.pass2.focus();
        document.getElementById("errorBox").innerHTML = "Confirm new Password";
        //alert('Enter new Password');
        return false;
    }else if(!passRegex.test(pass2)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    
    if(!(pass1 === pass2)){       
        document.getElementById("errorBox").innerHTML = "Passwords do not match";
        return false;
    }
    
}

function checkEmail(){
    var email = document.emailForm.email.value;
    
    if(email === ""){
        
        document.emailForm.email.focus();
        document.getElementById("errorBox").innerHTML = "email field cannot be blank";
        return false;
    }
}
function checkDob(){
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    
    var dob = document.dobForm.dob.value;
    
    if(dob === ""){
        document.dobForm.dob.focus();
        document.getElementById("errorBox").innerHTML = "Date of Birth cannot be blank";
        return false;
    }else if(!dateRegex.test(dob)){
        document.dobForm.dob.focus();
        document.getElementById("errorBox").innerHTML = "Invalid date or date format";
        return false;
        return false;
    } 
}

function checkPassword(){
    var passRegex = /[A-Za-z0-9]{5,}/;
    var currentPass = document.passForm.currentPass.value;
    var pass1 = document.passForm.pass1.value;
    var pass2 = document.passForm.pass2.value;

    if(currentPass === ""){
        document.passForm.currentPass.focus();
        document.getElementById("errorBox").innerHTML = "Enter current Password";
        return false;
    }
    if(pass1 === ""){
        document.passForm.pass1.focus();
        document.getElementById("errorBox").innerHTML = "Enter new Password";
        return false;
    }else if(!passRegex.test(pass1)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    if(pass2 === ""){
        document.passForm.pass2.focus();
        document.getElementById("errorBox").innerHTML = "Confirm new Password";
        //alert('Enter new Password');
        return false;
    }else if(!passRegex.test(pass2)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    
    if(!(pass1 === pass2)){       
        document.getElementById("errorBox").innerHTML = "Passwords do not match";
        return false;
    }
}



