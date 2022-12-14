public class AssignmentNode extends StatementNode
{
	Node Target;
	Node Expression;
	
	public AssignmentNode(Node t, Node e)
	{
		Target = t;
		Expression = e;
	}
	
	public Node getTarget()
	{
		return Target;
	}
	
	public Node getExpressionNode()
	{
		return Expression;
	}
	
	public String toString()
	{
		return Target.toString() + " := " + Expression.toString() + "\n";
	}
}