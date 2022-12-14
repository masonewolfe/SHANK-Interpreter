import java.util.*;
public class WhileNode extends StatementNode
{
	Node bool;
	List<StatementNode> statements = new ArrayList<StatementNode>();
	
	public WhileNode(Node tof, List<StatementNode> commands)
	{
		bool = tof;
		statements = commands;
	}
	
	public Node GetComparison()
	{
		return bool;
	}
	
	public List<StatementNode> GetStatements()
	{
		return statements;
	}
	
	public String toString()
	{
		String output = "WHILE: " + bool.toString();
		if(!statements.isEmpty())
		{
			for(StatementNode n : statements)
			{
				output = output + n.toString();
			}
		}
		else
		{
			output = output + "NO STATEMENTS IN WHILE BODY \n";
		}
		return output;
	}
}