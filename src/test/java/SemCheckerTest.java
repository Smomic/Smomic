import com.smomic.SemChecker;
import com.smomic.analyzer.BuildingParser;
import com.smomic.exception.*;
import com.smomic.structure.Program;
import org.junit.Before;
import org.junit.Test;

public class SemCheckerTest {
    private BuildingParser parser;

    @Before
    public void setUp() {
        parser = new BuildingParser();
    }

    @Test(expected = NoMainFunctionException.class)
    public void checkMain_shouldThrowExceptionIfNoMain()
            throws MainFunctionArgumentsException, NoMainFunctionException,
            DuplicateFunctionException, OverridePrintFunctionException {
        String source = "function foo() {}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkFunctions();
    }

    @Test(expected = MainFunctionArgumentsException.class)
    public void checkMain_shouldThrowExceptionIfMainContainsArguments()
            throws MainFunctionArgumentsException, NoMainFunctionException,
            DuplicateFunctionException, OverridePrintFunctionException {
        String source = "function main(a) {}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkFunctions();
    }

    @Test(expected = DuplicateFunctionException.class)
    public void checkMain_shouldThrowExceptionIfDuplicateFunction()
            throws MainFunctionArgumentsException, NoMainFunctionException,
            DuplicateFunctionException, OverridePrintFunctionException {
        String source = "function foo() {}" +
                "function foo() {}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkFunctions();
    }

    @Test(expected = OverridePrintFunctionException.class)
    public void checkMain_shouldThrowExceptionIfOverridePrint()
            throws MainFunctionArgumentsException, NoMainFunctionException,
            DuplicateFunctionException, OverridePrintFunctionException {
        String source = "function print() {}" +
                        "function main() {}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkFunctions();
    }

    @Test(expected = DuplicateUnitTypeException.class)
    public void checkMain_shouldThrowExceptionIfDuplicateImport()
            throws DuplicateUnitException, DuplicateUnitTypeException, NoNameUnitTypeException {
        String source = "import [a | a:1];" +
                        "import [a | a:1];";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkImports();
    }

    @Test(expected = NoNameUnitTypeException.class)
    public void checkMain_shouldThrowExceptionIfIncorrectMultiplyUnit()
            throws DuplicateUnitException, DuplicateUnitTypeException, NoNameUnitTypeException {
        String source = "import [a : b | a:1];" +
                        "import [b : c | a:1];";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkImports();
    }

    @Test(expected = DuplicateUnitException.class)
    public void checkMain_shouldThrowExceptionIfIncorrectUnit()
            throws DuplicateUnitException, NoNameUnitTypeException, DuplicateUnitTypeException {
        String source = "import [a : b | a:1];" +
                        "import [b | a:1];";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.checkImports();
    }

}
