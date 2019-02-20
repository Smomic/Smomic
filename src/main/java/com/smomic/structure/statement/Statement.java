package com.smomic.structure.statement;

import com.smomic.structure.NodeType;
import com.smomic.structure.element.Element;

public abstract class Statement extends Element {

    @Override
    public NodeType getType() {
        return NodeType.Statement;
    }
}
