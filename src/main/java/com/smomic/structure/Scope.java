package com.smomic.structure;

import com.smomic.exception.IncorrectVarNameException;
import com.smomic.structure.element.Element;
import javafx.util.Pair;

import java.util.LinkedHashMap;

public class Scope {
    private LinkedHashMap<String, Element> scopeMap;
    private Scope upperScope;

    public Scope(Scope upperScope) {
        this.scopeMap = new LinkedHashMap<>();
        this.upperScope = upperScope;
    }

    public void setUpperScope(Scope upperScope) {
        this.upperScope = upperScope;
    }

    public void setScopeMap(LinkedHashMap<String, Element> scopeMap) {
        this.scopeMap = scopeMap;
    }

    public void addVariable(String name, Element value) {
        Scope scope = getScope(name);
        if (scope != null)
            scope.setVariable(name, value);
        else
            setVariable(name, value);
    }

    public Element getVariableValue(String name, Pair<Integer, Integer> position) throws IncorrectVarNameException {
        Scope scope = getScope(name);

        if (scope == null)
            throw new IncorrectVarNameException(name, position);


        return scope.getValue(name);
    }

    private Scope getScope(String name) {
        boolean result = scopeMap.containsKey(name);
        if (!result && upperScope != null)
            return upperScope.getScope(name);

        return (result) ? this : null;
    }

    private void setVariable(String name, Element value) {
        scopeMap.put(name, value);
    }

    private Element getValue(String name) {
        return scopeMap.get(name);
    }
}
