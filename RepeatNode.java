import java.util.*;
public class RepeatNode extends StatementNode
{
	Node bool;
	List<StatementNode> statements = new ArrayList<StatementNode>();
	
	public RepeatNode(Node tof, List<StatementNode> commands)
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
		String output = "DO: \n";
		for(StatementNode n : statements)
		{
			output = output + n.toString();
		}
		output = output + "WHILE: " + bool.toString();
		return output;
	}










}