function genTNA(){
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    var regex = /[0-9]/;
    var and_regex = /[&]/;
    var plus_regex = /[+]/;
    
    var poRef = document.form.poRef.value;
    var style = document.form.style.value;
    var season = document.form.season.value;
    var quantity = document.form.quantity.value;
    var orderDate = document.form.orderDate.value;
    
    var arr = orderDate.split("/");
    var o_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
    var today = new Date();

    if(poRef === ""){
        document.form.poRef.focus();
        document.getElementById("errorBox").innerHTML = "PO Reference Number cannot be empty";
        return false;
    }else if(and_regex.test(poRef)){
        alert("'&' character not allowed!!!");
        return false;
    }else if(plus_regex.test(poRef)){
        alert("'+' character not allowed!!!");
        return false;
    }
    
    if(style === ""){
        document.form.style.focus();
        document.getElementById("errorBox").innerHTML = "Style cannot be empty";
        return false;
    }
    
    if(season === ""){
        document.form.season.focus();
        document.getElementById("errorBox").innerHTML = "Season cannot be empty";
        return false;
    }
    
    if(quantity === ""){
        document.form.quantity.focus();
        document.getElementById("errorBox").innerHTML = "Quantity cannot be empty";
        return false;
    }else if(!regex.test(quantity)){
        document.form.quantity.focus();
        document.getElementById("errorBox").innerHTML = "Quantity can only be numeric value";
        return false;
    }
    
    if(orderDate === ""){
        document.form.orderDate.focus();
        document.getElementById("errorBox").innerHTML = "Order Date cannot be empty";
        return false;
    }else if(!dateRegex.test(orderDate)){
        document.form.orderDate.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]";
        return false;
    }else if(o_date > today){
        alert('TNA cannot be Generated in advance.');
        return false;
    }
    
}

function selectBuyer(){
    var buyer = document.form.buyer.value;
    
    if(buyer === ""){
        alert('Select Buyer first');
        return false;
    }
}

function confirmDelete(){
    return confirm('Are you sure, you want to delete this TNA?');
}

function searchTNA(){
    var poRef = document.searchform.poRef.value;
    
    if(poRef === ""){
        document.searchform.poRef.focus();
        document.getElementById("errorBox").innerHTML = "Enter PO Reference Number";
        return false;
    }
}

function editTNA(){
    
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    var regex = /[0-9]/;
    var str_regex = /[&]/;
    
    var poRef = document.editform.poRef2.value;
    var style = document.editform.style.value;
    var season = document.editform.season.value;
    var quantity = document.editform.quantity.value;
    var orderDate = document.editform.orderDate.value;
    
    var arr = orderDate.split("/");
    var o_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
    var today = new Date();
    
    if(poRef === ""){
        document.editform.poRef2.focus();
        document.getElementById("errorBox1").innerHTML = "PO Reference Number cannot be empty";
        return false;
    }else if(str_regex.test(poRef)){
        alert("'&' character not allowed!!!");
        return false;
    }
    
    if(style === ""){
        document.editform.style.focus();
        document.getElementById("errorBox1").innerHTML = "Style cannot be empty";
        return false;
    }else if(str_regex.test(style)){
        alert("'&' character not allowed!!!");
        return false;
    }
    
    if(season === ""){
        document.editform.season.focus();
        document.getElementById("errorBox1").innerHTML = "Season cannot be empty";
        return false;
    }else if(str_regex.test(season)){
        alert("'&' character not allowed!!!");
        return false;
    }
    
    if(quantity === ""){
        document.editform.quantity.focus();
        document.getElementById("errorBox1").innerHTML = "Quantity cannot be empty";
        return false;
    }else if(!regex.test(quantity)){
        document.editform.quantity.focus();
        document.getElementById("errorBox1").innerHTML = "Quantity can only be numeric value";
        return false;
    }
    
    
    if(orderDate === ""){
        //allow 
    }else if(!dateRegex.test(orderDate)){
        document.form.orderDate.focus();
        document.getElementById("errorBox").innerHTML = "Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]";
        return false;
    }else if(o_date > today){
        alert('TNA cannot be Generated in advance.');
        return false;
    }
    
}

function selectActivity(){
    var activity = document.activityform.activityNo.value;
    
    if( activity === ""){
        alert('Select an activity first');
        return false;
    }
}


