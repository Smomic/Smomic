package com.smomic.structure.element;

import com.smomic.exception.IncorrectCountOfParamsException;
import com.smomic.exception.IncorrectFunNameException;
import com.smomic.structure.*;
import com.smomic.structure.importSt.ImportDirective;

import java.util.ArrayList;
import java.util.List;

import static com.smomic.SemChecker.MAIN;
import static com.smomic.SemChecker.PRINT;

public class FunCall extends Element {
    private String name;
    private List<Element> arguments;

    public FunCall(String name, List<Element> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.FunCall;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        List<Element> concreteArguments = new ArrayList<>();

        if (name.equals(PRINT)) {
            PrintFunction printFunction = new PrintFunction(arguments);
            printFunction.execute(scope, functions, imports);
            return null;
        }

        if (arguments != null) {
            for (Element arg : arguments)
                concreteArguments.add(arg.execute(scope, functions, imports));
        }

        for (FunDefinition definition : functions) {
            if (definition.getName().equals(name) && !definition.getName().equals(MAIN)) {

                try {
                    checkCountOfParameters(definition);
                } catch (IncorrectCountOfParamsException e) {
                    e.show();
                }

                FunDefinition fun = new FunDefinition(definition.getParameters(), definition.getFunBody(),
                        definition.getName(), definition.getPosition());
                return fun.execute(functions, imports, concreteArguments);
            } else if (!definition.getName().equals(MAIN)) {
                try {
                    throw new IncorrectFunNameException(definition.getName(), definition.getPosition());
                } catch (IncorrectFunNameException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    private void checkCountOfParameters(FunDefinition definition) throws IncorrectCountOfParamsException {
        if (definition.getParameters() == null && arguments == null)
            return;

        if ((definition.getParameters() == null && arguments != null)
                || (definition.getParameters() != null && arguments == null)
                || (definition.getParameters().size() != arguments.size()))
            throw new IncorrectCountOfParamsException(definition.getPosition());

    }

    @Override
    public boolean canReturn() {
        return true;
    }
}
