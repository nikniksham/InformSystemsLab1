let lastId = null;
let baza_url = "http://localhost:8080";  // For local
// let baza_url = "http://localhost:32073"; // For helios

let canvas = document.getElementById('drawLine');
let ctx = canvas.getContext('2d');
ctx.font = "16px roboto";
ctx.lineWidth = "2"

class Vehicle {
    constructor(id, name, capacity, x, y, author_id, creationDate, enginePower, numberOfWheels, distanceTravelled, fuelConsumption, commonAccess) {
        this.id = id
        this.name = name
        this.radius = Math.max(1, Math.log10(capacity)) * 5
        this.x = x
        this.y = y
        this.capacity = capacity
        this.creationDate = creationDate
        this.enginePower = enginePower
        this.numberOfWheels = numberOfWheels
        this.distanceTravelled = distanceTravelled
        this.fuelConsumption = fuelConsumption
        this.author_id = author_id
        this.selected = false
        this.commonAccess = commonAccess
    }
}


class Grid {
    constructor(width, height) {
        this.mouse_click = false
        this.last_mouse_coords = null
        this.width = width
        this.height = height
        this.elems = []
        this.x_coord = 0
        this.y_coord = 0
        this.userColors = {}
    }

    update() {
        this.clear()
        this.draw()
    }

    draw() {
        this.drawGrid()
        this.drawElems()
    }

    drawElems() {
        this.elems.forEach((elem) => {
            let color = this.userColors[elem.author_id]
            if (elem.selected) {
                color = "#FFF"
            }
            this.drawPoint(...this.CoordsToCanvas([elem.x, elem.y]), elem.radius, color)
        })
    }

    drawPoint(x_point, y_point, radius, color="#ffe300") {
        ctx.fillStyle = color
        ctx.beginPath()
        ctx.arc(x_point, y_point, radius, 0, Math.PI * 2, true)
        ctx.fill()
        ctx.stroke()
        ctx.fillStyle = "#000"
    }

    drawLine(start_coords, end_coords, color="#000") {
        ctx.fillStyle = color
        ctx.beginPath()
        ctx.moveTo(...start_coords)
        ctx.lineTo(...end_coords)
        ctx.stroke()
        ctx.fillStyle = "#000"
    }

    drawGrid() {
        let step = 100
        this.drawLine([0, this.height - 10], [this.width, this.height - 10])
        for (let x = (this.x_coord + this.width) % step + 40; x <= this.width + step; x += step) {
            let x_str = (x - this.x_coord - this.width / 2).toString()
            ctx.fillText(x_str, x - x_str.length * 4, this.height - 30);
            this.drawLine([x, this.height], [x, this.height-20])
        }
        this.drawLine([10, this.height], [10, 0])
        for (let y = (this.height - this.y_coord) % step - step - 15; y <= this.height - 50; y += step) {
            let y_str = (this.height/2 - (y + this.y_coord)).toString()
            ctx.fillText(y_str, 30, y + 4);
            this.drawLine([0, y], [20, y])
        }
    }

    CoordsToCanvas(coords) {
        return [(coords[0] + this.x_coord) + this.width/2, this.height/2 - (coords[1] + this.y_coord)]
    }

    CanvasToCoords(canvas) {
        return [canvas[0] - this.x_coord - this.width/2, this.height/2 - this.y_coord - canvas[1]]
    }

    clear() {
        ctx.clearRect(0, 0, this.width, this.height);
    }

    mouse_on() {
        this.mouse_click = true
    }

    mouse_leave() {
        this.mouse_click = false
        this.last_mouse_coords = null
    }

    move(mouse_coords) {
        if (this.mouse_click) {
            if (this.last_mouse_coords != null) {
                this.x_coord += mouse_coords[0] - this.last_mouse_coords[0]
                this.y_coord -= mouse_coords[1] - this.last_mouse_coords[1]
            }
            this.last_mouse_coords = mouse_coords
        }
    }

    set_elems(data) {
        console.log(data)
        this.elems = []
        let vehicles = data["Vehicles"]
        let coordinates = data["Coordinates"]
        let authors = data["Authors"]
        vehicles.forEach((veh) => {
            let find_coords = null, find_author = null
            for (let coor of coordinates) {
                if (coor["id"] === veh["coordinates_id"]) {
                    find_coords = coor
                    break
                }
            }
            for (let auth of authors) {
                if (auth.vehicle_id === veh.id) {
                    find_author = auth
                    break
                }
            }
            if (find_coords != null) {
                if (find_author != null) {
                    find_author = find_author.author_id
                }
                if (!(find_author in this.userColors)) {
                    let randomColor = Math.floor(Math.random() * 16777215).toString(16);
                    this.userColors[find_author] = `#${randomColor.padStart(6, '0')}`
                }
                this.elems.push(new Vehicle(veh.id, veh.name, veh.capacity, find_coords.x, find_coords.y, find_author,
                    veh.creationDate, veh.enginePower, veh.numberOfWheels, veh.distanceTravelled, veh.fuelConsumption, veh.commonAccess))
            }
        })
        this.update()
    }

    show_info(mouse_cords) {
        let id = null
        for (let i = this.elems.length - 1; i >= 0; --i) {
            let veh = this.elems[i]
            let coords = this.CoordsToCanvas([veh.x, veh.y])
            if (((coords[0] - mouse_cords[0])**2 + (coords[1] - mouse_cords[1])**2)**0.5 <= veh.radius) {
                id = veh.id
                break
            }
        }
        if (id != null) {
            this.elems.forEach((veh) => {
                if (veh.id === id) {
                    veh.selected = true
                    document.getElementById('id').innerText = "id: " + veh.id
                    document.getElementById('name').innerText = "name: "+ veh.name
                    document.getElementById('coords').innerText = "coords: [" + veh.x + ", " + veh.y + "]"
                    document.getElementById('capacity').innerText = "capacity: "+ veh.capacity
                    document.getElementById('creationDate').innerText = "creationDate: "+ veh.creationDate
                    document.getElementById('enginePower').innerText = "enginePower: "+ veh.enginePower
                    document.getElementById('numberOfWheels').innerText = "numberOfWheels: "+ veh.numberOfWheels
                    document.getElementById('distanceTravelled').innerText = "distanceTravelled: "+ veh.distanceTravelled
                    document.getElementById('fuelConsumption').innerText = "fuelConsumption: "+ veh.fuelConsumption
                    document.getElementById('author_id').innerText = "author_id: "+ veh.author_id
                    let editWheels_btn = document.getElementById('editWheels')
                    editWheels_btn.href = "/demo1/addWheels?vehicle_id="+veh.id
                    console.log(editWheels_btn.href)
                    editWheels_btn.className = "button-link"

                    let can = (parseInt(document.getElementById('userId').textContent) === veh.author_id ||
                        parseInt(document.getElementById("userStatus").textContent) === 2 && veh.commonAccess)

                    let editVehicle_btn = document.getElementById("editVehicle")
                    let deleteVehicle_btn = document.getElementById("deleteVehicle")

                    let btn_class = "button-link-disable"
                    if (can) {
                        editVehicle_btn.href = "/demo1/editVehicle?vehicle_id="+veh.id
                        deleteVehicle_btn.href = "/demo1/deleteVehicle?vehicle_id="+veh.id
                        btn_class = "button-link"
                    } else {
                        editVehicle_btn.href = ""
                        deleteVehicle_btn.href = ""
                    }

                    editVehicle_btn.className = btn_class
                    deleteVehicle_btn.className = btn_class


                } else {
                    veh.selected = false
                }
            })
        }
        this.update()
    }
}



grid = new Grid(canvas.width, canvas.height)
grid.update()

canvas.onmousemove = function (evt) {
    let rect = this.getBoundingClientRect(), x = evt.clientX - rect.left, y = evt.clientY - rect.top;
    grid.update();
    grid.move([x, y])
}

canvas.onmousedown = function (evt) {
    grid.mouse_on()
    let rect = this.getBoundingClientRect(), x = evt.clientX - rect.left, y = evt.clientY - rect.top;
    // console.log(grid.CanvasToCoords([x, y]))
    grid.show_info([x, y])
}

canvas.onmouseup = function () {
    grid.mouse_leave()
}

canvas.onmouseleave = function () {
    grid.mouse_leave()
}

function setLastId() {
    const Http = new XMLHttpRequest();
    const url= baza_url + '/demo1/getLastInformationId';
    Http.open("GET", url);
    Http.send();

    Http.onreadystatechange = () => {
        if (Http.responseText !== "") {
            lastId = Http.responseText;
        }
    }
}

function setNewVehicles() {
    const Http = new XMLHttpRequest();
    const url= baza_url + '/demo1/getAllVehicles'
    Http.open("GET", url);
    Http.send();

    Http.onreadystatechange = () => {
        if (Http.responseText !== "") {
            grid.set_elems(JSON.parse(Http.responseText))
        }
    }
}

setNewVehicles()
setLastId()
let interval = setInterval(function() {
    if (lastId != null) {
        const Http = new XMLHttpRequest();
        const url = baza_url + '/demo1/checkNewInformation?last_id=' + lastId
        Http.open("GET", url)
        Http.send();

        Http.onreadystatechange = () => {
            if (Http.responseText !== "") {
                if (Http.responseText.charCodeAt(0) === 116) {
                    setLastId()
                    setNewVehicles()
                }
            }
        }
    }
}, 1000)
