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
        this.drawLine([0, this.height/2], [this.width, this.height/2])
        this.drawLine([this.width/2, 0], [this.width/2, this.height])
        this.drawGrid()
        this.drawPoint(100, 20, 5)
    }

    drawPoint(x_point, y_point, radius, color="#ffe300") {
        ctx.beginPath()
        ctx.fillStyle = color
        ctx.arc(x_point, y_point, radius, 0, Math.PI * 2, true);
        ctx.fill();
        ctx.stroke();
        ctx.fillStyle = "#000";
    }

    drawLine(start_coords, end_coords, color="#000") {
        ctx.beginPath()
        ctx.moveTo(...start_coords)
        ctx.lineTo(...end_coords)
        ctx.stroke()
    }

    drawUI() {

    }

    drawGrid() {
        this.drawLine([0, this.height - 10], [this.width, this.height - 10])
        for (let x = 80; x <= this.width; x += 110) {
            let x_str = (x - this.width/2).toString()
            ctx.fillText(x_str, x - x_str.length * 4, this.height - 30);
            this.drawLine([x, this.height], [x, this.height-20])
        }
        this.drawLine([10, this.height], [10, 0])
        for (let y = this.height - 70; y >= 0; y -= 110) {
            let y_str = (this.height-y + this.y_coord - this.height/2)
            ctx.fillText(y_str, 30, y + 4);
            this.drawLine([0, y], [20, y])
        }
    }

    CoordsToCanvas(coords) {
        return [(coords[0] + this.x_coord) + this.width/2 / this.zoom, this.height - ((coords[1] + this.y_coord) + this.height/2 / this.zoom)]
    }

    clear() {
        ctx.clearRect(0, 0, this.width, this.height);
    }
}


grid = new Grid(canvas.width, canvas.height)
grid.update()

canvas.onmousemove = function (evt) {
    grid.update();
}