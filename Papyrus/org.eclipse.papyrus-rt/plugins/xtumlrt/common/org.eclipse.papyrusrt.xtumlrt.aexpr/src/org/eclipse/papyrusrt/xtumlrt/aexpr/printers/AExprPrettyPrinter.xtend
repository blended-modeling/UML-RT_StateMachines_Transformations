package org.eclipse.papyrusrt.xtumlrt.aexpr.printers

import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.AExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Num
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Op
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.OpPrecedence
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprPrinter

class AExprPrettyPrinter implements AExprPrinter
{
	extension OpPrinter opPrinter = new OpPrinter
	extension OpPrecedence opPrecedence = new OpPrecedence
	extension NamePrinter namePrinter = new NamePrinter
	
	override toText(AExpr e)
	{
		relativeToText(e, Op.NOOP)
	}
	
	private def dispatch String relativeToText(Num e, Op parentOp)
	'''«e.value»'''
	
	private def dispatch String relativeToText(Var e, Op parentOp)
	'''«e.name.toText»'''
	
	private def dispatch String relativeToText(UnaryExpr e, Op parentOp)
	'''«IF bindsTighter(e.op, parentOp)»«e.unaryExprText»«ELSE»(«e.unaryExprText»)«ENDIF»'''
	
	private def dispatch String relativeToText(BinExpr e, Op parentOp)
	'''«IF bindsTighter(e.op, parentOp)»«e.binExprText»«ELSE»(«e.binExprText»)«ENDIF»'''
	
	private def unaryExprText(UnaryExpr e) '''«e.op.opText»«e.operand.relativeToText(e.op)»'''
	
	private def binExprText(BinExpr e) 	'''«e.left.relativeToText(e.op)» «e.op.opText» «e.right.relativeToText(e.op)»'''
}
