package com.smomic.structure.element;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.List;

public class Literal extends Element {
    private String unit;
    private Number number;
    private Pair<Integer, Integer> position;

    public Literal(Double value, String unit, Pair<Integer, Integer> position) {
        this.number = new Number(value, position);
        this.unit = unit;
        this.position = position;
    }

    public String getUnit() {
        return unit;
    }

    public Number getNumber() {
        return number;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void changeSign() {
        number.changeSign();
    }

    @Override
    public NodeType getType() {
        return NodeType.Literal;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return this;
    }

    @Override
    public boolean canReturn() {
        return false;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#####");
        return new StringBuilder().append(df.format(number.getValue()))
                .append(unit)
                .toString();
    }
}
