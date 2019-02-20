package com.smomic.analyzer;// Generated from UnitLanguage.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link UnitLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface UnitLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(UnitLanguageParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#importDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDirective(UnitLanguageParser.ImportDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#importDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDefinition(UnitLanguageParser.ImportDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#importContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportContent(UnitLanguageParser.ImportContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#funDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDefinition(UnitLanguageParser.FunDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(UnitLanguageParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(UnitLanguageParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(UnitLanguageParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(UnitLanguageParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(UnitLanguageParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(UnitLanguageParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#assignStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStatement(UnitLanguageParser.AssignStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(UnitLanguageParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#multiExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiExp(UnitLanguageParser.MultiExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#simpleExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExp(UnitLanguageParser.SimpleExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp(UnitLanguageParser.PrimaryExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#funCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunCall(UnitLanguageParser.FunCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(UnitLanguageParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(UnitLanguageParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#andCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCond(UnitLanguageParser.AndCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#comparisonCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonCond(UnitLanguageParser.ComparisonCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#primaryCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryCond(UnitLanguageParser.PrimaryCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#parentCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentCond(UnitLanguageParser.ParentCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#addOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddOp(UnitLanguageParser.AddOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOp(UnitLanguageParser.UnaryOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#multiOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiOp(UnitLanguageParser.MultiOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#assignmentOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOp(UnitLanguageParser.AssignmentOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#notOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotOp(UnitLanguageParser.NotOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#orOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrOp(UnitLanguageParser.OrOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#andOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOp(UnitLanguageParser.AndOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#comparisonOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOp(UnitLanguageParser.ComparisonOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#convertOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConvertOp(UnitLanguageParser.ConvertOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnitLanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(UnitLanguageParser.LiteralContext ctx);
}