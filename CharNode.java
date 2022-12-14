public class CharNode extends Node
{
	char val ;
	public CharNode(char c)
	{
		val = c;
	}
	
	public char getChar()
	{
		return val;
	}
	
	public String toString()
	{
		return Character.toString(val);
	}
}