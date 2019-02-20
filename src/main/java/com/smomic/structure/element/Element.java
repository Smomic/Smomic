package com.smomic.structure.element;

import com.smomic.structure.Executable;
import com.smomic.structure.NodeType;

public abstract class Element implements Executable {
    public abstract NodeType getType();
}
