public class CompareNode extends StatementNode
{
	public enum comparisons
	{
		EQUAL,
		NOTEQUAL,
		GREATERTHAN,
		LESSTHAN,
		GREATERTHANOREQUALTO,
		LESSTHANOREQUALTO
	}
	private Node left, right;
	private comparisons middle;
	public CompareNode(Node l, Token comp, Node r)
	{
		left = l;
		right = r;
		setMiddle(comp.toString());
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public comparisons getComp()
	{
		return middle;
	}
	public void setMiddle(String comp)
	{
		switch(comp)
		{
			case "=": middle = comparisons.EQUAL; break;
			case "<>": middle=comparisons.NOTEQUAL; break;
			case ">": middle=comparisons.GREATERTHAN; break;
			case "<":  middle=comparisons.LESSTHAN; break;
			case ">=":  middle=comparisons.GREATERTHANOREQUALTO; break;
			case "<=":  middle=comparisons.LESSTHANOREQUALTO; break;
		}
	}
	
	public String toString()
	{
		return left.toString() + " " + middle + " " + right.toString() + "\n";
	}
}