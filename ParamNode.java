public class ParamNode extends Node
{
	public boolean isVar;
	public Node paramType;
	
	public ParamNode(boolean vorn, Node type)
	{
		isVar = vorn;
		paramType = type;
	}
	
	public Node getNode()
	{
		return paramType;
	}
	
	public String toString()
	{
		if(isVar==true)
		{
			return "var " + typeOfParam() + " " + paramType.toString();
		}
		else
		{
			return typeOfParam() + " " + paramType.toString();
		}
	}
	
	private String typeOfParam()
	{
		if(paramType instanceof VariableReferenceNode) 
		{
			return "VarRefNode";
		}
		else if(paramType instanceof FloatNode)
		{
			return "FloatNode";
		}
		else if(paramType instanceof IntegerNode)
		{
			return "IntNode";
		}
		else if(paramType instanceof StringNode)
		{
			return "StringNode";
		}
		else if(paramType instanceof CharNode)
		{
			return "CharNode";
		}
		else if(paramType instanceof BoolNode)
		{
			return "BoolNode";
		}
		else
		{
			return "null";
		}
		
	}







}