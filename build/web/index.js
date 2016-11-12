function Login(){
    //var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
    var eidRegex = /[0-9]/;
    var eid = document.form.eid.value;
    var password = document.form.pass.value;
    var role = document.form.role.value;
    
    if( eid==="" ){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Enter Employee ID";
        return false;
    }else if(!eidRegex.test(eid)){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Employee ID. It can only be Numeric value of 5 digits";
        return false;
    }else if(eid.length != 5){
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Employee ID. It can only be Numeric value of 5 digits";
        return false;
    }
 
    if(password==="" ){
        document.form.pass.focus();
        document.getElementById("errorBox").innerHTML = "Enter Password";
        return false;
    }else if(password.length < 5){
        document.form.pass.focus();
        document.getElementById("errorBox").innerHTML = "Password should be at least 5 character long";
        return false;
    }
    
    if(role===""){
        document.form.role.focus();
        document.getElementById("errorBox").innerHTML = "Select Role";
        return false;
    }
    
}

function check(){
    var role = document.form.role.value;
    var eid = document.form.eid.value;
    if(role === ""){
        //alert('Select role first');
        document.form.role.focus();
        document.getElementById("errorBox").innerHTML = "Select role first";
        return false;
    }
    if(eid === ""){
        //alert('Enter Employee Id');
        document.form.eid.focus();
        document.getElementById("errorBox").innerHTML = "Enter Employee Id";
        return false;
    }
}
function resetPassword(){
    var passRegex = /[A-Za-z0-9]{5,}/;
    var newPassword = document.resetForm.newPassword.value;
    var newPassword2 = document.resetForm.newPassword2.value;
    
    if(newPassword === ""){
        document.resetForm.newPassword.focus();
        document.getElementById("errorBox").innerHTML = "Enter new password";
        //alert('Enter new Password');
        return false;
    }else if(!passRegex.test(newPassword)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    
    if(newPassword2 === ""){
        document.resetForm.newPassword2.focus();
        document.getElementById("errorBox").innerHTML = "Confirm new password";
        //alert('Confirm new Password');
        return false;
    }else if(!passRegex.test(newPassword2)){
        alert('Password can only contain alphabets and numbers of length greater or equal to 5 ');
        return false;
    }
    
    if(!(newPassword === newPassword2)){
        document.getElementById("errorBox").innerHTML = "Passwords do not match";
        //alert('Passwords do not match');
        return false;
    }
}
function forgotPassword(){
    var role = document.form.role.value;
    var eid = document.form.eid.value;
    
    if(role === ""){
        //document.form.role.focus();
        //document.getElementById("errorBox").innerHTML = "Select role first";
        alert('Select role first');
        return false;
    }
    
    if(eid === ""){
        //document.form.eid.focus();
        //document.getElementById("errorBox").innerHTML = "Enter Employee Id";
        alert('Enter Employee ID');
        return false;
    }
}
