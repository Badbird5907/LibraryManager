package dev.badbird.library.csv;

import java.util.Map;

public class Row {
    private Map<String, String> values;

    public Row(Map<String, String> values) {
        this.values = values;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
