package com.smomic;

import com.smomic.exception.*;
import com.smomic.exception.Exception;
import com.smomic.structure.FunDefinition;
import com.smomic.structure.Program;
import com.smomic.structure.importSt.ImportContent;
import com.smomic.structure.importSt.ImportDirective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SemChecker {
    public static final String MAIN = "main";
    public static final String PRINT = "print";
    private Program program;

    public SemChecker(Program program) {
        this.program = program;
    }

    public void check() {
        try {
            checkImports();
            checkFunctions();
        } catch (Exception e) {
            e.show();
        }
    }

    public void checkImports() throws DuplicateUnitTypeException, NoNameUnitTypeException, DuplicateUnitException {
        if (program.getImports() != null) {
            List<ImportDirective> importDirectives = program.getImports();
            for (int i = 0; i < importDirectives.size() - 1; ++i) {
                if (importDirectives.size() > 1) {
                    for (int j = i + 1; j < importDirectives.size(); ++j) {
                        if (importDirectives.get(i).getImportDefinition().getMultiUnitList().get(0)
                                .equals(importDirectives.get(j).getImportDefinition().getMultiUnitList().get(0))) {
                            String name = importDirectives.get(i).executeMulti().get(0);
                            throw new DuplicateUnitTypeException(name,
                                    importDirectives.get(i).getImportDefinition().getPosition(name));
                        }
                    }
                }
            }
            checkMultiplyUnit();
            checkContent();
        }
    }

    private void checkMultiplyUnit() throws NoNameUnitTypeException {
        List<String> mainUnitList = new ArrayList<>();
        for (ImportDirective directive : program.getImports()) {
            mainUnitList.add(directive.executeMulti().get(0));
        }
        for (ImportDirective directive : program.getImports()) {
            List<String> unitList = directive.executeMulti();
            for (String s : unitList) {
                if (!mainUnitList.contains(s))
                    throw new NoNameUnitTypeException(s,
                            directive.getImportDefinition().getPosition(s));
            }
        }
    }

    private void checkContent() throws DuplicateUnitException {
        List<String> list = new ArrayList<>();
        for (ImportDirective directive : program.getImports()) {
            for (ImportContent content : directive.execute()) {
                if (list.contains(content.getUnit())) {
                    throw new DuplicateUnitException(content.getUnit(),
                            directive.getImportDefinition().getPosition(content.getUnit()));
                } else
                    list.add(content.getUnit());
            }
        }
    }

    public void checkFunctions() throws DuplicateFunctionException, NoMainFunctionException,
            MainFunctionArgumentsException, OverridePrintFunctionException {

        List<FunDefinition> funDefinitions = program.getFunctions();
        for (int i = 0; i < funDefinitions.size() - 1; ++i) {
            if (funDefinitions.get(i).getName().equals(PRINT))
                throw new OverridePrintFunctionException(funDefinitions.get(i).getPosition());

            if (funDefinitions.size() > 1) {
                for (int j = i + 1; j < funDefinitions.size(); ++j) {
                    if (funDefinitions.get(i).getName().equals(funDefinitions.get(j).getName()))
                        throw new DuplicateFunctionException(funDefinitions.get(i).getName(),
                                funDefinitions.get(i).getPosition());
                }
            }
        }
        checkMainFunction();
    }

    private void checkMainFunction() throws NoMainFunctionException, MainFunctionArgumentsException {
        for (FunDefinition f : program.getFunctions()) {
            if (f.getName().equals(MAIN)) {
                checkMainFunctionParameters(f);
                Collections.swap(program.getFunctions(), program.getFunctions().indexOf(f), 0);
                return;
            }
        }
        throw new NoMainFunctionException();
    }

    private void checkMainFunctionParameters(FunDefinition funDefinition) throws MainFunctionArgumentsException {
        if (funDefinition.getParameters() != null && funDefinition.getParameters().size() > 0)
            throw new MainFunctionArgumentsException(funDefinition.getPosition());
    }
}
