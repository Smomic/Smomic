package com.smomic.structure.importSt;

import java.util.List;

public class ImportDirective {
    private ImportDefinition importDefinition;

    public ImportDirective(ImportDefinition importDefinition) {
        this.importDefinition = importDefinition;
    }

    public ImportDefinition getImportDefinition() {
        return importDefinition;
    }

    public List<ImportContent> execute() {
        return importDefinition.execute();
    }

    public List<String> executeMulti() {
        return importDefinition.executeMulti();
    }
}