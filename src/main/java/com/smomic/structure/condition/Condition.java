package com.smomic.structure.condition;

import com.smomic.structure.NodeType;
import com.smomic.structure.element.Element;

public abstract class Condition extends Element {
    @Override
    public NodeType getType() {
        return NodeType.Condition;
    }

}
