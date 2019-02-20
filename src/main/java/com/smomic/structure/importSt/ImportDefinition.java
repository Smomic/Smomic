package com.smomic.structure.importSt;

import javafx.util.Pair;

import java.util.List;

public class ImportDefinition {
    private List<String> multiUnitList;
    private List<Pair<Integer, Integer>> multiPositionList;
    private List<ImportContent> importContentList;

    public ImportDefinition(List<String> multiUnitList, List<Pair<Integer, Integer>> multiPositionList,
                            List<ImportContent> importContentList) {
        this.multiUnitList = multiUnitList;
        this.multiPositionList = multiPositionList;
        this.importContentList = importContentList;
    }

    public List<String> getMultiUnitList() {
        return multiUnitList;
    }

    public Pair<Integer, Integer> getPosition(String unit) {
        if (multiUnitList.contains(unit))
            return multiPositionList.get(multiUnitList.indexOf(unit));
        return null;
    }

    public List<ImportContent> execute() {
        return importContentList;
    }

    public List<String> executeMulti() {
        return multiUnitList;
    }
}
