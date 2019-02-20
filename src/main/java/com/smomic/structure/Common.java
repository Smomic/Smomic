package com.smomic.structure;

import com.smomic.exception.IncorrectVarNameException;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.element.Variable;
import com.smomic.structure.importSt.ImportContent;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static List<Pair<String, Double>> getUnitsPair(String arg, List<ImportDirective> imports) {
        if (!imports.isEmpty()) {
            List<Pair<String, Double>> unitList = new ArrayList<>();
            for (ImportDirective i : imports) {
                List<ImportContent> list = i.execute();
                for (ImportContent content : list) {
                    if (content.getUnit().equals(arg)) {
                        unitList.add(new Pair<>(list.get(0).getUnit(), list.get(0).getValue()));
                        unitList.add(new Pair<>(content.getUnit(), content.getValue()));
                        return unitList;
                    }
                }
            }
        }
        return null;
    }

    public static Element getVariableValue(Scope scope, Variable result) throws IncorrectVarNameException {

        Element element = scope.getVariableValue(result.getName(), result.getPosition());
        if (element.getType().equals(NodeType.Literal)) {
            return new Literal(((Literal) element).getNumber().getValue(), ((Literal) element).getUnit(),
                    result.getPosition());
        } else if (element.getType().equals(NodeType.Number)) {
            return new Number(((Number) element).getValue(), ((Number) element).getPosition());
        }

        return null;
    }
}
