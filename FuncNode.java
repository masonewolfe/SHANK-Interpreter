import java.util.*;
public class FuncNode extends CallableNode
{
	String functionName;
	public List<VariableNode> parameters = new ArrayList<VariableNode>();
	public List<VariableNode> locals = new ArrayList<VariableNode>();
	public List<StatementNode> statements = new ArrayList<StatementNode>();
	
	
	public FuncNode(String name)
	{
		functionName = name;
	}
	
	public String getName()
	{
		return functionName;
	}
	
	public List<VariableNode> getParameters()
	{
		return parameters;
	}
	
	public List<VariableNode> getVariables()
	{
		return locals;
	}
	
	public List<StatementNode> getStatements()
	{
		return statements;
	}
	
	public void addParameters(List<VariableNode> paras)
	{
		parameters = paras;
	}
	
	public void addLocals(List<VariableNode> locs)
	{
		locals = locs;
	}
	
	public void addStatements(List<StatementNode> st)
	{
		statements=st;
	}
	
	public void printFunction()
	{
		System.out.println("FUNCTION NAME: " + functionName);
		System.out.println();
		System.out.println("PARAMETERS:");
		for(VariableNode n : parameters)
		{
			System.out.println(n.toString());
		}
		System.out.println();
		System.out.println("LOCAL VARIABLES:");
		for(VariableNode n : locals)
		{
			System.out.println(n.toString());
		}
		System.out.println();
		System.out.println("STATEMENTS:");
		for(StatementNode n: statements)
		{
			System.out.println(n.toString());
		}
		System.out.println();
	}








}