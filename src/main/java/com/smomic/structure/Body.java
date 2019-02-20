package com.smomic.structure;

import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.LinkedHashMap;
import java.util.List;

public class Body extends Element {
    private List<Element> statementList;
    private Scope scope;

    public Body(List<Element> statementList) {
        this.statementList = statementList;
        scope = new Scope(null);
    }

    @Override
    public NodeType getType() {
        return NodeType.Body;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        this.scope.setUpperScope(scope);
        return commonExecute(functions, imports);
    }

    public Element execute(LinkedHashMap<String, Element> argScope, List<FunDefinition> functions,
                           List<ImportDirective> imports) {
        scope.setScopeMap(argScope);
        return commonExecute(functions, imports);
    }

    private Element commonExecute(List<FunDefinition> functions, List<ImportDirective> imports) {
        for(Element element : statementList) {
            Element value = element.execute(scope, functions, imports);
            if (value != null && element.canReturn())
                return value;
        }
        return null;
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}
