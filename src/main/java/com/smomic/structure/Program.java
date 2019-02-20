package com.smomic.structure;

import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

import static com.smomic.SemChecker.MAIN;

public class Program extends Element {
    private List<ImportDirective> imports;
    private List<FunDefinition> functions;
    private List<String> importNames;

    public Program(List<ImportDirective> imports, List<String> importNames, List<FunDefinition> functions) {
        this.imports = imports;
        this.importNames = importNames;
        this.functions = functions;
    }

    public List<FunDefinition> getFunctions() {
        return functions;
    }

    public List<ImportDirective> getImports() {
        return imports;
    }

    public List<String> getImportNames() {
        return importNames;
    }

    public void clearImportNames() {
        importNames.clear();
    }

    @Override
    public NodeType getType() {
        return NodeType.Program;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> f, List<ImportDirective> i) {
        FunDefinition main = null;
        for(FunDefinition fun : functions) {
            if (fun.getName().equals(MAIN)) {
                main = fun;
                break;
            }
        }
        List<FunDefinition> funDefinitionList = functions;
        return (main != null) ? main.execute(null, funDefinitionList, imports)
                : null;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
