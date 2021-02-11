// Generated from ./src/main/java/rita/antlr/RiScriptParser.g4 by ANTLR 4.8
package rita.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RiScriptParser}.
 */
public interface RiScriptParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(RiScriptParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(RiScriptParser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(RiScriptParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(RiScriptParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RiScriptParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RiScriptParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#cexpr}.
	 * @param ctx the parse tree
	 */
	void enterCexpr(RiScriptParser.CexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#cexpr}.
	 * @param ctx the parse tree
	 */
	void exitCexpr(RiScriptParser.CexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(RiScriptParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(RiScriptParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#weight}.
	 * @param ctx the parse tree
	 */
	void enterWeight(RiScriptParser.WeightContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#weight}.
	 * @param ctx the parse tree
	 */
	void exitWeight(RiScriptParser.WeightContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#choice}.
	 * @param ctx the parse tree
	 */
	void enterChoice(RiScriptParser.ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#choice}.
	 * @param ctx the parse tree
	 */
	void exitChoice(RiScriptParser.ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(RiScriptParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(RiScriptParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#chars}.
	 * @param ctx the parse tree
	 */
	void enterChars(RiScriptParser.CharsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#chars}.
	 * @param ctx the parse tree
	 */
	void exitChars(RiScriptParser.CharsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#dynamic}.
	 * @param ctx the parse tree
	 */
	void enterDynamic(RiScriptParser.DynamicContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#dynamic}.
	 * @param ctx the parse tree
	 */
	void exitDynamic(RiScriptParser.DynamicContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#symbol}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(RiScriptParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#symbol}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(RiScriptParser.SymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#wexpr}.
	 * @param ctx the parse tree
	 */
	void enterWexpr(RiScriptParser.WexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#wexpr}.
	 * @param ctx the parse tree
	 */
	void exitWexpr(RiScriptParser.WexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#link}.
	 * @param ctx the parse tree
	 */
	void enterLink(RiScriptParser.LinkContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#link}.
	 * @param ctx the parse tree
	 */
	void exitLink(RiScriptParser.LinkContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#url}.
	 * @param ctx the parse tree
	 */
	void enterUrl(RiScriptParser.UrlContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#url}.
	 * @param ctx the parse tree
	 */
	void exitUrl(RiScriptParser.UrlContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#transform}.
	 * @param ctx the parse tree
	 */
	void enterTransform(RiScriptParser.TransformContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#transform}.
	 * @param ctx the parse tree
	 */
	void exitTransform(RiScriptParser.TransformContext ctx);
	/**
	 * Enter a parse tree produced by {@link RiScriptParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(RiScriptParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RiScriptParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(RiScriptParser.OpContext ctx);
}