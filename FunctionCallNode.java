import java.util.*;
public class FunctionCallNode extends StatementNode
{
	String name;
	List<ParamNode> params;
	
	public FunctionCallNode(String n, List<ParamNode> p)
	{
		name=n;
		params=p;
	}
	
	public List<ParamNode> getParams()
	{
		return params;
	}
	public String getName()
	{
		return name;
	}
	public String toString()
	{
		String output = name+"(";
		for(ParamNode p : params)
		{
			output = output + p.toString()+",";
		}
		return output + ")\n";
	}













}