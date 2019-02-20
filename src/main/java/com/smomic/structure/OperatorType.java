package com.smomic.structure;

import com.smomic.analyzer.UnitLanguageParser;

import static com.smomic.Helper.makeOperator;

public enum OperatorType {
    Plus(makeOperator(UnitLanguageParser.Plus)),
    Minus(makeOperator(UnitLanguageParser.Minus)),
    Multiply(makeOperator(UnitLanguageParser.Multiply)),
    Divide(makeOperator(UnitLanguageParser.Divide)),
    Modulo(makeOperator(UnitLanguageParser.Modulo)),
    Or(makeOperator(UnitLanguageParser.Or)),
    And(makeOperator(UnitLanguageParser.And)),
    Equality(makeOperator(UnitLanguageParser.Equality)),
    Inequality(makeOperator(UnitLanguageParser.Inequality)),
    Greater(makeOperator(UnitLanguageParser.Greater)),
    GreaterEqual(makeOperator(UnitLanguageParser.GreaterEqual)),
    Less(makeOperator(UnitLanguageParser.Less)),
    LessEqual(makeOperator(UnitLanguageParser.LessEqual));

    private final String description;

    OperatorType(String description) {
        this.description = description;
    }

    public static OperatorType getOperatorType(String operator) {
        for (OperatorType op : OperatorType.values()) {
            if (op.description.equals(operator))
                return op;
        }
        return null;
    }

}
