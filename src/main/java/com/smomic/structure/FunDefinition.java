package com.smomic.structure;

import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.*;

public class FunDefinition extends Element implements Executable {
    private String name;
    private Pair<Integer, Integer> position;
    private List<String> parameters;
    private Body funBody;

    public FunDefinition(List<String> parameters, Body funBody, String name, Pair<Integer, Integer> position) {
        this.parameters = parameters;
        this.funBody = funBody;
        this.name = name;
        this.position = position;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getType() {
        return NodeType.FunDefinition;
    }

    public Body getFunBody() {
        return funBody;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions,
                           List<ImportDirective> imports) {
        return execute(functions, imports, null);
    }

    public Element execute(List<FunDefinition> functions, List<ImportDirective> imports,
                           List<Element> arguments) {

        LinkedHashMap<String, Element> argScope = new LinkedHashMap<>();
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++)
                argScope.put(parameters.get(i), arguments.get(i));
        }

        return (funBody != null) ? funBody.execute(argScope, functions, imports)
                : null;
    }


    @Override
    public boolean canReturn() {
        return true;
    }
}
