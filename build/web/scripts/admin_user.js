function searchUser(){
    var eidRegex = /[0-9]{5}/;
    var role = document.form.role.value;
    var eid = document.form.eid.value;
    
    if(role===""){
        document.form.role.focus();
        document.getElementById("errorBox").innerHTML = "Select Role First";
        return false;
    }
     if( eid==="" ){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Enter Employee ID";
        return false;
    }else if(!eidRegex.test(eid)){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Employee ID. It can only be Numeric value of 5 digits";
        return false;
    }
}
    
function registerUser(){
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    var eidRegex = /[0-9]{5}/;
    var name = document.form.name.value;
    var eid = document.form.eid.value;    
    var email = document.form.email.value;
    var dob = document.form.dob.value;
    var role = document.form.role.value;
    
    if(name === ""){
        document.form.name.focus();
        document.getElementById("errorBox").innerHTML = "Name cannot be Empty";
        return false;  
    }
    
    if(eid === ""){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Employee ID cannot be null";
        return false;
    }else if(!eidRegex.test(eid)){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Employee ID can only be numeric value of 5 digits";
        return false;
    }
    
    if(email === ""){
        document.form.email.focus();
        document.getElementById("errorBox").innerHTML = "Email Id cannot be Empty";
        return false;  
    }
    
    if(dob === ""){
        document.form.dob.focus();
        document.getElementById("errorBox").innerHTML = "Date of Birth cannot be Empty";
        return false;  
    }else if(!dateRegex.test(dob)){
        document.form.dob.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Date or date format";
        return false; 
    }
    
    if(role === ""){
        document.form.email.focus();
        document.getElementById("errorBox").innerHTML = "Select any role";
        return false;  
    }
    
}

function selectRole(){
    var role = document.form.role.value;

    if(role===""){
        document.form.role.focus();
        document.getElementById("errorBox").innerHTML = "Select Role First";
        return false;
    }
    
}


