package com.smomic.structure;

import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public interface Executable {
    Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports);
    boolean canReturn();
}
