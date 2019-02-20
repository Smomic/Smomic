package com.smomic.structure.importSt;

public class ImportContent {
    private String unit;
    private Double value;

    public ImportContent(String unit, Double value) {
        this.unit = unit;
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public Double getValue() {
        return value;
    }
}
