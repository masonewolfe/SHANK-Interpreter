public class VariableReferenceNode extends Node
{
	String variableName;
	
	public VariableReferenceNode(String name)
	{
		variableName = name;
	}
	
	public String toString()
	{
		return variableName;
	}
}