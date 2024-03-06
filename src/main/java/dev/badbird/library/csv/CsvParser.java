package dev.badbird.library.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class CsvParser {
    private File file;
    private Row[] rows;
    private Column[] columns;

    public CsvParser(File file) {
        this.file = file;
    }

    public void readFile() { // I am very sorry for this extremely messy code
        String data;
        try {
            data = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // TODO support for comma in values
        String[] rows = data.split("\n");
        String columnsRow = rows[0].trim();
        String[] crSplit = columnsRow.split(",");
        Column[] columns = new Column[crSplit.length];
        for (int i = 0; i < crSplit.length; i++) {
            columns[i] = new Column(crSplit[i]);
        }
        Row[] rowsArr = new Row[rows.length - 1];
        for (int i = 1; i < rows.length; i++) {
            String[] rowsSplit = rows[i].split(",");
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < columns.length; j++) {
                String str = rowsSplit[j].replace("{comma}", ",").trim();
                if (str.startsWith("\"") && str.endsWith("\"")) {
                    str = str.substring(1, str.length() - 1);
                }
                rowData.put(columns[j].getName(), str);
            }
            rowsArr[i - 1] = new Row(rowData);
        }
        this.rows = rowsArr;
        this.columns = columns;
    }

    public void debugPrint() {
        for (Column column : columns) {
            System.out.print(column.getName() + ", ");
        }
        System.out.println();
        for (Row row : rows) {
            row.getValues().forEach((k, v) -> {
                System.out.println(k + ": " + v);
            });
        }
    }

    public Column[] getColumns() {
        return columns;
    }

    public Row[] getRows() {
        return rows;
    }

    public File getFile() {
        return file;
    }
}
