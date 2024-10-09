function sortTable(columnIndex, isNum, lowerToMax) {
    let table, rows, switching, i, x, y, shouldSwitch, z1, z2;
    table = document.getElementById("data-table");
    switching = true;

    while (switching) {
        switching = false;
        rows = table.rows;

        for (i = 1; i < rows.length - 1; i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[columnIndex];
            y = rows[i + 1].getElementsByTagName("td")[columnIndex];

            if (!isNum) {
                z1 = x.innerHTML
                z2 = y.innerHTML
            } else {
                z1 = parseFloat(x.innerHTML)
                z2 = parseFloat(y.innerHTML)
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
