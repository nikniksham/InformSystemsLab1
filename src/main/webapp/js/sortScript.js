let recentColumn = null;

function sortTable(columnIndex, typeColumn) {
    let table, rows, switching, i, x, y, shouldSwitch, z1, z2, lowerToMax;
    if (recentColumn === columnIndex) {
        lowerToMax = true;
        recentColumn = null;
    } else {
        lowerToMax = false;
        recentColumn = columnIndex;
    }
    table = document.getElementById("data-table");
    switching = true;

    while (switching) {
        switching = false;
        rows = table.rows;

        for (i = 1; i < rows.length - 1; i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[columnIndex];
            y = rows[i + 1].getElementsByTagName("td")[columnIndex];

            if (typeColumn === 0) {
                z1 = x.innerHTML.toLowerCase()
                z2 = y.innerHTML.toLowerCase()
            } else if (typeColumn === 1) {
                z1 = parseFloat(x.innerHTML)
                z2 = parseFloat(y.innerHTML)
            } else if (typeColumn === 2) {
                z1 = new Date(x.innerHTML).getTime()
                z2 = new Date(y.innerHTML).getTime()
            }

            if ((lowerToMax && z1 > z2) || (!lowerToMax && z1 < z2)) {
                shouldSwitch = true;
                break;
            }
        }

        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}
