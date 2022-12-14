public class IntegerNode extends Node
{
	private int num;
	
	//BASIC CONSTRUCTOR
	public IntegerNode()
	{
		num = -1;
	}
	//PARAMTERIZED CONSTRUCTOR
	public IntegerNode(int val)
	{
		num = val;
	}
	//RETURN VALUE OF INTEGER STORED AS A FLOAT BECAUSE WE ARE DOING FLOAT MATH
	public float getVal()
	{
		return (float)num;
	}
	//RETURN NUMBER REPRESENTED AS A STRING
	public String toString()
	{
		return String.valueOf(num);
	}
}