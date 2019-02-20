package com.smomic.analyzer;

import com.smomic.structure.*;
import com.smomic.structure.condition.*;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.FunCall;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.element.Variable;
import com.smomic.structure.expression.*;
import com.smomic.structure.importSt.ImportContent;
import com.smomic.structure.importSt.ImportDefinition;
import com.smomic.structure.importSt.ImportDirective;
import com.smomic.structure.statement.AssignStatement;
import com.smomic.structure.statement.IfStatement;
import com.smomic.structure.statement.LoopStatement;
import com.smomic.structure.statement.ReturnStatement;
import javafx.util.Pair;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static java.util.stream.Collectors.toList;


public class BuildingParser {
    public Program parse(String sourceCode) {
        CharStream charStream = new ANTLRInputStream(sourceCode);
        UnitLanguageLexer lexer = new UnitLanguageLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        UnitLanguageParser parser = new UnitLanguageParser(tokens);

        ProgramVisitor visitor = new ProgramVisitor();
        return visitor.visit(parser.program());
    }

    private static class ProgramVisitor extends UnitLanguageBaseVisitor<Program> {
        @Override
        public Program visitProgram(UnitLanguageParser.ProgramContext ctx) {
            ImportDirectiveVisitor directiveVisitor = new ImportDirectiveVisitor();
            List<ImportDirective> importDirectiveList = null;
            List<String> importNames = null;
            if (ctx.importDirective() != null) {
                importNames = visitImportNames(ctx);
                importDirectiveList = ctx.importDirective()
                        .stream()
                        .map(directive -> directive.accept(directiveVisitor))
                        .collect(toList());
                importDirectiveList.removeAll(Collections.singleton(null));
            }
            FunctionDefinitionVisitor functionDefinitionVisitor = new FunctionDefinitionVisitor();
            List<FunDefinition> functionDefinitionList = null;
            if (ctx.funDefinition() != null) {
                functionDefinitionList = ctx.funDefinition()
                        .stream()
                        .map(directive -> directive.accept(functionDefinitionVisitor))
                        .collect(toList());
            }
            return new Program(importDirectiveList, importNames, functionDefinitionList);
        }

        private List<String> visitImportNames(UnitLanguageParser.ProgramContext ctx) {
            List<String> importNames = new ArrayList<>();
            for (UnitLanguageParser.ImportDirectiveContext iDirective : ctx.importDirective()) {
                if (iDirective.Id() != null)
                    importNames.add(iDirective.Id().getText());
            }
            return importNames;
        }
    }

    private static class ImportDirectiveVisitor extends UnitLanguageBaseVisitor<ImportDirective> {
        @Override
        public ImportDirective visitImportDirective(UnitLanguageParser.ImportDirectiveContext ctx) {
            ImportDefinitionVisitor importDefinitionVisitor = new ImportDefinitionVisitor();
            return (ctx.importDefinition() == null) ? null
                    : new ImportDirective(importDefinitionVisitor.visitImportDefinition(ctx.importDefinition()));
        }
    }

    private static class ImportDefinitionVisitor extends UnitLanguageBaseVisitor<ImportDefinition> {
        @Override
        public ImportDefinition visitImportDefinition(UnitLanguageParser.ImportDefinitionContext ctx) {
            ImportContentVisitor importContentVisitor = new ImportContentVisitor();
            List<String> multiUnitList = null;
            List<Pair<Integer, Integer>> positionList = null;
            if (ctx.Id() != null) {
                multiUnitList = new ArrayList<>();
                positionList = new ArrayList<>();
                for (TerminalNode id : ctx.Id()) {
                    multiUnitList.add(id.getText());
                    positionList.add(new Pair<>(id.getSymbol().getLine(), id.getSymbol().getCharPositionInLine()));
                }
            }
            List<ImportContent> importContentList = ctx.importContent()
                    .stream()
                    .map(content -> content.accept(importContentVisitor))
                    .collect(toList());

            return new ImportDefinition(multiUnitList, positionList, importContentList);
        }
    }

    private static class ImportContentVisitor extends UnitLanguageBaseVisitor<ImportContent> {
        @Override
        public ImportContent visitImportContent(UnitLanguageParser.ImportContentContext ctx) {
            return new ImportContent(ctx.Id().getText(), parseNumber(ctx.Number().getText(),
                    new Pair<>(ctx.Number().getSymbol().getLine(), ctx.Number().getSymbol().getCharPositionInLine())));
        }
    }

    private static Double parseNumber(String number, Pair<Integer, Integer> position) {
        Double value = null;
        if (number.length() > 1
                && number.charAt(0) == '0'
                && number.charAt(1) != '.') {
            System.err.printf("[%d:%d] Incorrect number format\n", position.getKey(), position.getValue());
            return null;
        }
        try {
            value = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            System.err.printf("[%d:%d] Incorrect number format", position.getKey(), position.getValue());
        }
        return value;
    }

    private static class FunctionDefinitionVisitor extends UnitLanguageBaseVisitor<FunDefinition> {
        @Override
        public FunDefinition visitFunDefinition(UnitLanguageParser.FunDefinitionContext ctx) {
            BodyVisitor bodyVisitor = new BodyVisitor();
            List<String> parameterList = null;
            if (ctx.parameters() != null) {
                parameterList = new ArrayList<>();
                for (TerminalNode parameter : ctx.parameters().Id())
                    parameterList.add(parameter.getText());
            }
            Pair<Integer, Integer> pos = new Pair<>(ctx.Id().getSymbol().getLine(),
                    ctx.Id().getSymbol().getCharPositionInLine());
            return new FunDefinition(parameterList, bodyVisitor.visitBody(ctx.body()), ctx.Id().getText(), pos);
        }
    }

    private static class BodyVisitor extends UnitLanguageBaseVisitor<Body> {
        @Override
        public Body visitBody(UnitLanguageParser.BodyContext ctx) {
            List<Element> statementList = null;
            if (ctx.statement() != null) {
                StatementVisitor statementVisitor = new StatementVisitor();
                statementList = ctx.statement()
                        .stream()
                        .map(statementContext -> statementContext.accept(statementVisitor))
                        .collect(toList());
            }
            return new Body(statementList);

        }
    }

    private static class StatementVisitor extends UnitLanguageBaseVisitor<Element> {
        @Override
        public Element visitStatement(UnitLanguageParser.StatementContext ctx) {

            if (ctx.assignStatement() != null) {
                MainExpressionVisitor exceptionVisitor = new MainExpressionVisitor();
                Element element = exceptionVisitor.visitExpression(ctx.assignStatement().expression());
                return new AssignStatement(new Variable(ctx.assignStatement().Id().getText(),
                        new Pair<>(ctx.assignStatement().Id().getSymbol().getLine(),
                                ctx.assignStatement().Id().getSymbol().getCharPositionInLine())), element);

            } else if (ctx.ifStatement() != null) {
                BodyVisitor bodyVisitor = new BodyVisitor();
                MainConditionVisitor mainConditionVisitor = new MainConditionVisitor();
                Body elseBody = null;
                if (ctx.ifStatement().Else() != null)
                    elseBody = bodyVisitor.visitBody(ctx.ifStatement().body(1));

                return new IfStatement(bodyVisitor.visitBody(ctx.ifStatement().body(0)), elseBody,
                        mainConditionVisitor.visitCondition(ctx.ifStatement().condition()));
            } else if (ctx.funCall() != null)
                return new FunCallVisitor().visitFunCall(ctx.funCall());

            else if (ctx.loopStatement() != null) {
                BodyVisitor bodyVisitor = new BodyVisitor();
                MainConditionVisitor mainConditionVisitor = new MainConditionVisitor();
                return new LoopStatement(mainConditionVisitor.visitCondition(ctx.loopStatement().condition()),
                        bodyVisitor.visitBody(ctx.loopStatement().body()));
            } else {
                MainExpressionVisitor mainExpressionVisitor = new MainExpressionVisitor();
                return new ReturnStatement(mainExpressionVisitor.visitExpression(ctx.returnStatement().expression()));
            }
        }
    }

    private static class MainExpressionVisitor extends UnitLanguageBaseVisitor<MainExpression> {
        @Override
        public MainExpression visitExpression(UnitLanguageParser.ExpressionContext ctx) {
            MultiExpressionVisitor muliExpressionVisitor = new MultiExpressionVisitor();
            List<MultiExpression> multiExpressionList = ctx.multiExp()
                    .stream()
                    .map(multiExpContext -> multiExpContext.accept(muliExpressionVisitor))
                    .collect(toList());

            List<OperatorType> operatorTypeList = null;
            if (ctx.addOp() != null) {
                operatorTypeList = new ArrayList<>();
                for (UnitLanguageParser.AddOpContext node : ctx.addOp())
                    operatorTypeList.add(getOperatorType(node.getText()));
            }

            return new MainExpression(multiExpressionList, operatorTypeList);
        }
    }

    private static class MultiExpressionVisitor extends UnitLanguageBaseVisitor<MultiExpression> {
        @Override
        public MultiExpression visitMultiExp(UnitLanguageParser.MultiExpContext ctx) {
            SimpleExpressionVisitor simpleExpressionVisitor = new SimpleExpressionVisitor();
            List<SimpleExpression> simpleExpressionList = ctx.simpleExp()
                    .stream()
                    .map(multiExpContext -> multiExpContext.accept(simpleExpressionVisitor))
                    .collect(toList());

            List<OperatorType> operatorTypeList = null;
            if (ctx.multiOp() != null) {
                operatorTypeList = new ArrayList<>();
                for (UnitLanguageParser.MultiOpContext node : ctx.multiOp())
                    operatorTypeList.add(getOperatorType(node.getText()));
            }
            return new MultiExpression(simpleExpressionList, operatorTypeList, ctx.unaryOp() != null);
        }
    }

    private static class SimpleExpressionVisitor extends UnitLanguageBaseVisitor<SimpleExpression> {
        @Override
        public SimpleExpression visitSimpleExp(UnitLanguageParser.SimpleExpContext ctx) {
            PrimaryExpressionVisitor expressionVisitor = new PrimaryExpressionVisitor();
            String convertOp = null;
            if (ctx.convertOp() != null) {
                String convert = ctx.convertOp().getText();
                convertOp = convert.substring(1, convert.length());
            }

            if (convertOp != null) {
                return new SimpleExpression(expressionVisitor.visitPrimaryExp(ctx.primaryExp()), convertOp,
                        new Pair<>(ctx.convertOp().Id().getSymbol().getLine(),
                                ctx.convertOp().Id().getSymbol().getCharPositionInLine()));
            }
            else
                return new SimpleExpression(expressionVisitor.visitPrimaryExp(ctx.primaryExp()), null, null);
        }
    }

    private static class PrimaryExpressionVisitor extends UnitLanguageBaseVisitor<PrimaryExpression> {
        @Override
        public PrimaryExpression visitPrimaryExp(UnitLanguageParser.PrimaryExpContext ctx) {
            if (ctx.expression() != null) {
                MainExpressionVisitor expressionVisitor = new MainExpressionVisitor();
                return new PrimaryExpression(expressionVisitor.visitExpression(ctx.expression()));
            } else if (ctx.Id() != null)
                return new PrimaryExpression(new Variable(ctx.Id().getText(),
                        new Pair<>(ctx.Id().getSymbol().getLine(), ctx.Id().getSymbol().getCharPositionInLine())));

            else if (ctx.funCall() != null) {
                FunCallVisitor funCallVisitor = new FunCallVisitor();
                return new PrimaryExpression(funCallVisitor.visitFunCall(ctx.funCall()));
            } else {
                LiteralVisitor visitor = new LiteralVisitor();
                return new PrimaryExpression(visitor.visitLiteral(ctx.literal()));
            }
        }
    }

    private static OperatorType getOperatorType(String operator) {
        return OperatorType.getOperatorType(operator);
    }


    private static class MainConditionVisitor extends UnitLanguageBaseVisitor<MainCondition> {
        @Override
        public MainCondition visitCondition(UnitLanguageParser.ConditionContext ctx) {
            AndConditionVisitor andConditionVisitor = new AndConditionVisitor();
            List<AndCondition> conditions = ctx.andCond()
                    .stream()
                    .map(andCondContext -> andCondContext.accept(andConditionVisitor))
                    .collect(toList());


            return new MainCondition(conditions);
        }
    }

    private static class AndConditionVisitor extends UnitLanguageBaseVisitor<AndCondition> {
        @Override
        public AndCondition visitAndCond(UnitLanguageParser.AndCondContext ctx) {
            ComparisonConditionVisitor comparisonConditionVisitor = new ComparisonConditionVisitor();
            List<ComparisonCondition> conditions = ctx.comparisonCond()
                    .stream()
                    .map(andCondContext -> andCondContext.accept(comparisonConditionVisitor))
                    .collect(toList());

            return new AndCondition(conditions);

        }
    }

    private static class ComparisonConditionVisitor extends UnitLanguageBaseVisitor<ComparisonCondition> {
        @Override
        public ComparisonCondition visitComparisonCond(UnitLanguageParser.ComparisonCondContext ctx) {
            PrimaryConditionVisitor visitor = new PrimaryConditionVisitor();
            PrimaryCondition arg = null;
            if (ctx.comparisonOp() != null)
                arg = visitor.visitPrimaryCond(ctx.primaryCond(1));

            if (ctx.comparisonOp() == null) {
                return new ComparisonCondition(visitor.visitPrimaryCond(ctx.primaryCond(0)), arg, null);
            } else {
                return new ComparisonCondition(visitor.visitPrimaryCond(ctx.primaryCond(0)), arg,
                        getOperatorType(ctx.comparisonOp().getText()));
            }
        }
    }

    private static class PrimaryConditionVisitor extends UnitLanguageBaseVisitor<PrimaryCondition> {
        @Override
        public PrimaryCondition visitPrimaryCond(UnitLanguageParser.PrimaryCondContext ctx) {
            if (ctx.parentCond() != null) {
                ParentConditionVisitor parentConditionVisitor = new ParentConditionVisitor();
                return new PrimaryCondition(parentConditionVisitor.visitParentCond(ctx.parentCond()), ctx.notOp() != null);
            } else if (ctx.Id() != null) {
                return new PrimaryCondition(new Variable(ctx.Id().getText(),
                        new Pair<>(ctx.Id().getSymbol().getLine(), ctx.Id().getSymbol().getCharPositionInLine())), ctx.notOp() != null);
            } else if (ctx.funCall() != null) {
                FunCallVisitor funCallVisitor = new FunCallVisitor();
                return new PrimaryCondition(funCallVisitor.visitFunCall(ctx.funCall()), ctx.notOp() != null);
            } else {
                LiteralVisitor literalVisitor = new LiteralVisitor();
                return new PrimaryCondition(literalVisitor.visitLiteral(ctx.literal()), ctx.notOp() != null);
            }
        }
    }

    private static class ParentConditionVisitor extends UnitLanguageBaseVisitor<ParentCondition> {
        @Override
        public ParentCondition visitParentCond(UnitLanguageParser.ParentCondContext ctx) {
            MainConditionVisitor mainConditionVisitor = new MainConditionVisitor();
            return new ParentCondition(mainConditionVisitor.visitCondition(ctx.condition()));
        }
    }

    private static class FunCallVisitor extends UnitLanguageBaseVisitor<FunCall> {
        @Override
        public FunCall visitFunCall(UnitLanguageParser.FunCallContext ctx) {
            MainExpressionVisitor mainExpressionVisitor = new MainExpressionVisitor();
            List<Element> argumentList = null;
            if (ctx.arguments() != null) {
                argumentList = ctx.arguments().expression()
                        .stream()
                        .map(arg -> arg.accept(mainExpressionVisitor))
                        .collect(toList());
            }

            return new FunCall(ctx.Id().getText(), argumentList);
        }
    }

    private static class LiteralVisitor extends UnitLanguageBaseVisitor<Element> {
        @Override
        public Element visitLiteral(UnitLanguageParser.LiteralContext ctx) {
            return (ctx.Id() != null) ?
                    new Literal(parseNumber(ctx.Number().getText(), new Pair<>(ctx.Number().getSymbol().getLine(),
                                    ctx.Number().getSymbol().getCharPositionInLine())), ctx.Id().getText(),
                            new Pair<>(ctx.Id().getSymbol().getLine(), ctx.Id().getSymbol().getCharPositionInLine()))
                    : new Number(parseNumber(ctx.Number().getText(), new Pair<>(ctx.Number().getSymbol().getLine(),
                    ctx.Number().getSymbol().getCharPositionInLine())),
                            new Pair<>(ctx.Number().getSymbol().getLine(), ctx.Number().getSymbol().getCharPositionInLine()));

        }
    }
}
