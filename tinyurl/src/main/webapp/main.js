window.onload = (eventObj) => {
    let button = document.getElementById("button");
    button.addEventListener("click", fetchTinyURL);
}

function fetchTinyURL() {
        "use strict";
        const longURLValue = document.getElementById("longURLArea").value;
        if(!longURLValue) {
            return ;
        }
        const http = new XMLHttpRequest();
        const url = "http://localhost:7070/tinyurl/";
        http.open("POST", url);

        http.onreadystatechange = (e) => {
          if( (http.readyState == XMLHttpRequest.DONE) && (http.status == 200)  )  {
              let txtarea = document.getElementById("shortURLArea");
              txtarea.value = `http://localhost:7070/tinyurl/${http.responseText}`;
          }
        };   

        http.send(longURLValue);
}