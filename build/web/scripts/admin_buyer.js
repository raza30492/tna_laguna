function selectBuyer(){
    var buyer = document.form.buyerId.value;
    
    if(buyer === ""){
        document.form.buyerId.focus();
        document.getElementById("errorBox").innerHTML = "Select Buyer first";
        return false;
    }
    
}
function selectBuyer2(){
    var buyer = document.buyerform.buyerId.value;
    
    if(buyer === ""){
        document.buyerform.buyerId.focus();
        document.getElementById("errorBox").innerHTML = "Select Buyer first";
        return false;
    }
}

function removeBuyer(){
    var buyer = document.form.buyerName.value;
    
    if(buyer === ""){
        //document.form.buyerName.focus();
        //document.getElementById("errorBox").innerHTML = "Select Buyer first";
        alert('Select Buyer first');
        return false;
    }else{
        return confirm('Are you sure, you want to remove buyer ' + buyer);
    }
}

function updateBuyer(){
    return addBuyer();
}
function addBuyer(){
    var regex = /[0-9]/;
    var str_regex = /[&]/;
    
    var buyer = document.form.buyer.value;
    
    var activities = [];
    
    for(i = 0; i < 12; i++){
        activities[i] = document.forms["form"]["activity" + (i+1)].value;
    }
    /*
    var activity1 = document.form.activity1.value;
    var activity2 = document.form.activity2.value;
    var activity3 = document.form.activity3.value;
    var activity4 = document.form.activity4.value;
    var activity5 = document.form.activity5.value;
    var activity6 = document.form.activity6.value;
    var activity7 = document.form.activity7.value;
    var activity8 = document.form.activity8.value;
    var activity9 = document.form.activity9.value;
    var activity10 = document.form.activity10.value;
    var activity11 = document.form.activity11.value;
    var activity12 = document.form.activity12.value;
    */
    if(buyer === ""){
        document.form.buyer.focus();
        document.getElementById("errorBox").innerHTML = "Buyer name cannot be blank";
        return false;
    }else if(str_regex.test(buyer)){
        alert("'&' character not allowed!!!");
        return false;
    }
    
    for( i = 0; i < 12; i++){
        if(activities[i] === ""){
            document.forms["form"]["activity" + (i+1)].focus();
            document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
            return false;
        }else if(!regex.test(activities[i])){
            document.forms["form"]["activity" + (i+1)].focus();
            document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
            return false;
        }
    }
    /*
    if(activity1 === ""){
        document.form.activity1.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity1)){
        document.form.activity1.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity2 === ""){
        document.form.activity2.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity2)){
        document.form.activity2.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity3 === ""){
        document.form.activity3.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity3)){
        document.form.activity3.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity4 === ""){
        document.form.activity4.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity4)){
        document.form.activity4.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity5 === ""){
        document.form.activity5.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity5)){
        document.form.activity5.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity6 === ""){
        document.form.activity6.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity6)){
        document.form.activity6.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity7 === ""){
        document.form.activity7.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity7)){
        document.form.activity7.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity8 === ""){
        document.form.activity8.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity8)){
        document.form.activity8.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity9 === ""){
        document.form.activity9.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity9)){
        document.form.activity9.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity10 === ""){
        document.form.activity10.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity10)){
        document.form.activity10.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    
    if(activity11 === ""){
        document.form.activity11.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity11)){
        document.form.activity11.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    if(activity12 === ""){
        document.form.activity12.focus();
        document.getElementById("errorBox").innerHTML = "timeline field cannot be blank";
        return false;
    }else if(!regex.test(activity12)){
        document.form.activity12.focus();
        document.getElementById("errorBox").innerHTML = "timeline can only be numeric";
        return false;
    }
    */
    
}


