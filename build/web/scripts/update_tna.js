function tna3(){
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    
    var c_date,today,date;
  
    for(i = 0; i < 10; i++){
        date = document.forms["updateForm"]["date" + i].value;
        
        
        var arr = date.split("/");
        c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
        today = new Date();

        if(date === "" || date === "NULL"){
           //allow request 
        }
        else if(!dateRegex.test(date)){
            alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
            return false;
        }else if(c_date > today){
            alert('TNA cannot be updated in advance.');
            return false;
        }
    }
}

function tna2(){
    var dateRegex = /[0-3]{1}[0-9]{1}\/[0-1]{1}[0-9]{1}\/[1-9]{1}[0-9]{3}/;
    
    var c_date,today,date;
    today = new Date();
    

    var user = document.forms["updateForm"]["user"].value;
    
    if(user === "ADMIN"){
        for(i = 1; i <= 12; i++){
            if( !(document.forms["updateForm"]["date" + i] === undefined))
                date = document.forms["updateForm"]["date" + i].value;
            else continue;
            
            if(!(date === "" || date === "NULL")){
                var arr = date.split("/");
                c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
                

                if(!dateRegex.test(date)){
                    alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
                    return false;
                }else if(c_date > today){
                    alert('TNA cannot be updated in advance.');
                    return false;
                }
            }
        }
    }
    if(user === "MERCHANDISER"){
        for(i = 3; i <= 12; i++){
            
            if( !(document.forms["updateForm"]["date" + i] === undefined))
                date = document.forms["updateForm"]["date" + i].value;
            else continue;

            if(!(date === "" || date === "NULL")){
                var arr = date.split("/");
                c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
                

                if(!dateRegex.test(date)){
                    alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
                    return false;
                }else if(c_date > today){
                    alert('TNA cannot be updated in advance.');
                    return false;
                }
                 
            }
        }
    }
    if(user === "PURCHASE"){
        for(i = 1; i <= 6; i++){
            if( i == 2 || i == 4 || i == 6){
                if( !(document.forms["updateForm"]["date" + i] === undefined))
                    date = document.forms["updateForm"]["date" + i].value;
                else continue;

                if(!(date === "" || date === "NULL")){
                    var arr = date.split("/");
                    c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);


                    if(!dateRegex.test(date)){
                        alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
                        return false;
                    }else if(c_date > today){
                        alert('TNA cannot be updated in advance.');
                        return false;
                    }
                }
            }
           
        }
    }
    if(user === "FABRIC_SOURCING"){
        for(i = 1; i <= 10; i++){
            if( i == 1 || i == 7 || i == 10 ){
                if( !(document.forms["updateForm"]["date" + i] === undefined))
                    date = document.forms["updateForm"]["date" + i].value;
                else continue;

                if(!(date === "" || date === "NULL")){
                    var arr = date.split("/");
                    c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);


                    if(!dateRegex.test(date)){
                        alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
                        return false;
                    }else if(c_date > today){
                        alert('TNA cannot be updated in advance.');
                        return false;
                    }
                }
            }
            
        }
    }
    if(user === "LOGISTICS"){
        var date12 = document.forms["updateForm"]["date12"].value;
        var arr = date12.split("/");
        var c_date = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
        
        if(date12 === "" || date12 === "NULL"){
           //allow request 
        }
        else if(!dateRegex.test(date12)){
            alert('Invalid Date or Date format.Correct Date Format is [DD/MM/YYYY]');
            return false;
        }else if(c_date > today){
            alert('TNA cannot be updated in advance.');
            return false;
        }
    }
    
}



