package com.smomic.structure.expression;

import com.smomic.structure.NodeType;
import com.smomic.structure.element.Element;

public abstract class Expression extends Element {
    @Override
    public NodeType getType() {
        return NodeType.Expression;
    }
}
