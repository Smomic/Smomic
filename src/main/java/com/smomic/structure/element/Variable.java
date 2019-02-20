package com.smomic.structure.element;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.List;

public class Variable extends Element {
    private String name;
    private Pair<Integer, Integer> position;

    public Variable(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getType() {
        return NodeType.Var;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return this;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
