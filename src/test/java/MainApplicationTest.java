import com.smomic.SemChecker;
import com.smomic.analyzer.BuildingParser;
import com.smomic.structure.Program;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainApplicationTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private BuildingParser parser;
    private String importSource;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        parser = new BuildingParser();
        importSource =
                "import [length : area | m : 1 | km : 1000 | mile : 1609.344 | cm : 0.01 | mm : 0.001];" +
                "import [area | m2:1 | a:100 | ha:10000 | km2:1000000];";
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void print_shouldShowCorrectValueByReturnStatement() {
        String source = importSource +
                "function foo(a) {" +
                    "return ((a*a / 100cm ) * 10).km + 1km;" +
                "}" +
                "function main() {" +
                "    print(foo(foo(1m)));" +
                "}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.check();
        program.execute(null, null, null);
        assertEquals("10202km\r\n", outContent.toString());
    }

    @Test
    public void print_shouldShowCorrectValueByLoopStatement() {
        String source = importSource +
                "function foo(a) {" +
                    "i = 0;" +
                    "loop(i < 10) {" +
                        "a = a + c;" +
                        "i = i + 1;" +
                    "}" +
                    "return ((a / 10).cm).mm;" +
                "}" +
                "function main() {" +
                    "    print(foo(1mm));" +
                "}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.check();
        program.execute(null, null, null);
        assertEquals("10,1mm\r\n", outContent.toString());
    }

    @Test
    public void print_shouldShowCorrectValueByElseStatement() {
        String source = importSource +
                "function foo(a) {" +
                "i = 0;" +
                "if(!a) {" +
                "   return 1mile;" +
                "}" +
                "else {" +
                    "b = 2 * a;" +
                    "return (a * b).ha;" +
                "}}" +
                "function main() {" +
                "    print(foo(10km));" +
                "}";
        Program program = parser.parse(source);
        SemChecker s = new SemChecker(program);
        s.check();
        program.execute(null, null, null);
        assertEquals("20000ha\r\n", outContent.toString());
    }

}
