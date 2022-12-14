import java.util.*;
public class ForNode extends StatementNode
{
	Node start, end;
	VariableReferenceNode var;
	List<StatementNode> statements = new ArrayList<StatementNode>();
	
	public ForNode(VariableReferenceNode local, Node begin, Node finish, List<StatementNode> commands)
	{
		var = local;
		start = begin;
		end = finish;
		statements = commands;
	}
	
	public Node GetStartValue()
	{
		return start;
	}
	
	public Node GetEndValue()
	{
		return end;
	}
	
	public VariableReferenceNode GetVariable()
	{
		return var;
	}
	
	public List<StatementNode> GetStatements()
	{
		return statements;
	}
	
	public String toString()
	{
		String output = var.toString() + " starts at " + start.toString() + " and ends at " + end.toString() + "\n";
		for(StatementNode n: statements)
		{
			output = output + n.toString() + "\n";
		}
		return output;
	}
	


}