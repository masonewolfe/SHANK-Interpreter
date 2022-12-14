import java.util.*;
public class IfNode extends StatementNode
{
	private Node bool;
	private List<StatementNode> statements;
	private IfNode next;
	
	public IfNode(Node torf, List<StatementNode> commands, IfNode nextInLine)
	{
		bool = torf;
		statements = commands;
		next = nextInLine;
	}
	
	public Node GetComparison()
	{
		return bool;
	}
	
	public List<StatementNode> GetStatements()
	{
		return statements;
	}
	
	public IfNode GetNext()
	{
		return next;
	}
	
	public String toString()
	{
		String output;
		if(bool!=null)
		{
			output = "IF: "+bool.toString();
		}
		else
		{
			output = "ELSE \n";
		}
		
		if(statements.isEmpty())
		{
			output = output + "NO STATEMENTS  \n";
		}
		else
		{
			for(StatementNode n : statements)
			{
				output = output + n.toString();
			}
		}
		
		if(next!=null)
		{
			output = output + next.toString();
		}
		
		return output;
	}
	
}