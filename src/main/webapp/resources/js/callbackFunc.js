var lastId = null;
var needUpdate = null;

let baza_url = "http://localhost:8080";  // For local
// let baza_url = "http://localhost:32073"; // For helios

function setLastId() {
    const Http = new XMLHttpRequest();
    const url= 'http://localhost:8080/demo1/getLastInformationId';
    Http.open("GET", url);
    Http.send();

    Http.onreadystatechange = (e) => {
        if (Http.responseText !== "") {
            lastId = Http.responseText;
        }
    }
}

setLastId()

var interval = setInterval(function() {
    if (lastId != null) {
        const Http = new XMLHttpRequest();
        const url = baza_url + '/demo1/checkNewInformation?last_id=' + lastId;
        Http.open("GET", url);
        Http.send();

        Http.onreadystatechange = (e) => {
            if (Http.responseText !== "") {
                if (Http.responseText.charCodeAt(0) === 116) {
                    location.reload()
                }
            }
        }
    }
}, 1000);
