package com.smomic.structure.element;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.List;

public class Number extends Element {
    private Double value;
    private Pair<Integer, Integer> position;

    public Number(Double value, Pair<Integer, Integer> position) {
        this.position = position;
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void changeSign() {
        this.value *= -1;
    }

    @Override
    public NodeType getType() {
        return NodeType.Number;
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
        return df.format(value);
    }
}
