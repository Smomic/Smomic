package com.smomic.structure;

import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class PrintFunction implements Executable{
    private List<Element> arguments;

    public PrintFunction(List<Element> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean canReturn() {
        return false;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        for(Element arg : arguments) {
            Element element = execute(arg, scope, functions, imports);
            System.out.println(element);
        }
        return null;
    }

    private static Element execute(Element arg, Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return arg.execute(scope, functions, imports);
    }

}
