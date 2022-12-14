import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class SemanticAnalysis
{
	public List<FuncNode> functions;
	public SemanticAnalysis(List<FuncNode> f)
	{
		functions = f;
	}
	
	public void Semantisize()
	{
		for(FuncNode f : functions)
		{
			List<VariableNode> vars = new ArrayList<VariableNode>();
			vars.addAll(f.getParameters());
			vars.addAll(f.getVariables());
			checkBlock(f.getStatements(), vars);
		}
	}
	
	public void checkBlock(List<StatementNode> statements, List<VariableNode> vars)
	{
		for(StatementNode s : statements)
		{
			if(s instanceof AssignmentNode)
			{
				AssignmentNode temp = (AssignmentNode) s;
				checkAssignment(temp, vars);
			}
			else if(s instanceof IfNode)
			{
				IfNode temp = (IfNode)s;
				do
				{
					checkBlock(temp.GetStatements(),vars);
					temp = temp.GetNext();
				}
				while(temp!=null);
			}
			else if(s instanceof WhileNode)
			{
				WhileNode temp = (WhileNode)s;
				checkBlock(temp.GetStatements(),vars);
			}
			else if(s instanceof RepeatNode)
			{
				RepeatNode temp = (RepeatNode)s;
				checkBlock(temp.GetStatements(),vars);
			}
			else if(s instanceof ForNode)
			{
				ForNode temp = (ForNode)s;
				checkBlock(temp.GetStatements(),vars);
			}
		}
	}
	
	public void checkAssignment(AssignmentNode assignment, List<VariableNode> vars)
	{
		VariableNode v = findVar(assignment.getTarget().toString(), vars);
		if(v==null)
		{
			throw new RuntimeException("TARGET FOR ASSIGNMENT: " + assignment.getTarget().toString() + " IS UNDEFINED");
		}
		checkNode(v.type, assignment.getExpressionNode(), vars);
	}
	
	public void checkNode(VariableNode.dataType dt, Node expression, List<VariableNode> vars)
	{
		if(expression instanceof MathOpNode)
		{
			if(dt==VariableNode.dataType.REAL || dt==VariableNode.dataType.INT || dt==VariableNode.dataType.STRING)
			{
				checkNode(dt, expression.getLeft(), vars);
				checkNode(dt, expression.getRight(), vars);
			}
			else
			{
				throw new RuntimeException(expression.toString() + " DOES NOT RESULT IN A " + dt);
			}
		}
		else if(expression instanceof CompareNode)
		{
			if(dt!=VariableNode.dataType.BOOL)
			{
				throw new RuntimeException(expression.toString() + " DOES NOT RESULT IN A " + dt);
			}
		}
		else if(expression instanceof VariableReferenceNode)
		{
			if(findVar(expression.toString(),vars)==null)
			{
				throw new RuntimeException(expression.toString() + " HAS NOT BEEN DECLARED ");
			}
			else if(findVar(expression.toString(),vars).type!=dt)
			{
				if(!(dt==VariableNode.dataType.STRING && findVar(expression.toString(),vars).type==VariableNode.dataType.CHAR))
				{
					throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
				}
			}
		}
		else if(expression instanceof IntegerNode)
		{
			if(dt!=VariableNode.dataType.INT)
			{
				throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
			}
		}
		else if(expression instanceof FloatNode)
		{
			if(dt!=VariableNode.dataType.REAL)
			{
				throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
			}
		}
		else if(expression instanceof CharNode)
		{
			if(dt!=VariableNode.dataType.CHAR && dt!=VariableNode.dataType.STRING)
			{
				throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
			}
		}
		else if(expression instanceof StringNode)
		{
			if(dt!=VariableNode.dataType.STRING)
			{
				throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
			}
		}
		else if(expression instanceof BoolNode)
		{
			if(dt!=VariableNode.dataType.BOOL)
			{
				throw new RuntimeException(expression.toString() + " IS NOT A " + dt);
			}
		}
		
			
	}
	
	
	public VariableNode findVar(String name, List<VariableNode> vars)
	{
		for(VariableNode v : vars)
		{
			if(name.equals(v.getName()))
			{
				return v;
			}
		}
		return null;
	}
	
	
}