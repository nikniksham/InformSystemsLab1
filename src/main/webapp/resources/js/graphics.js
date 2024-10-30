let canvas = document.getElementById('drawLine');
let ctx = canvas.getContext('2d');
ctx.font = "16px roboto";
ctx.lineWidth = "2"

class Vehicle {
    constructor(id, name, capacity, x, y, author_id) {
        this.id = id
        this.name = name
        this.capacity = capacity
        this.x = x
        this.y = y
        this.author_id = author_id
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
        this.zoom = 1
    }

    update() {
        this.clear()
        this.draw()
    }

    draw() {
        this.drawGrid()
        this.drawPoint(...this.CoordsToCanvas([0, 0]), 5)
        let start = this.CoordsToCanvas([-500, 0])
        let end = this.CoordsToCanvas([500, 0])
        this.drawLine(start, end)
        start = this.CoordsToCanvas([0, -500])
        end = this.CoordsToCanvas([0, 500])
        this.drawLine(start, end)
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

    drawUI() {

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
        for (let y = (this.y_coord + this.height) % step - step; y <= this.height - 50; y += step) {
            let y_str = (this.height/2 - (y - this.y_coord)).toString()
            ctx.fillText(y_str, 30, y + 4);
            this.drawLine([0, y], [20, y])
        }
    }

    CoordsToCanvas(coords) {
        return [(coords[0] + this.x_coord) + this.width/2 / this.zoom, this.height - ((coords[1] + this.y_coord) + this.height/2 / this.zoom)]
    }

    CanvasToCoords(x_canvas, y_canvas) {
        return [(x_canvas - this.width / 2 + this.x_coord), -(y_canvas - this.height / 2) + this.y_coord]
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
}


grid = new Grid(canvas.width, canvas.height)
grid.update()

canvas.onmousemove = function (evt) {
    let rect = this.getBoundingClientRect(), x = evt.clientX - rect.left, y = evt.clientY - rect.top;
    grid.update();
    grid.move([x, y])
    console.log([grid.x_coord, grid.y_coord])
}

canvas.onmousedown = function (evt) {
    grid.mouse_on()
}

canvas.onmouseup = function (evt) {
    grid.mouse_leave()
}

canvas.onmouseleave = function (evt) {
    grid.mouse_leave()
}