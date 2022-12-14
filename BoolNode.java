public class BoolNode extends Node
{
	boolean val;
	public BoolNode(boolean b)
	{
		val = b;
	}
	
	public boolean getBool()
	{
		return val;
	}
	
	public String toString()
	{
		return Boolean.toString(val);
	}
}