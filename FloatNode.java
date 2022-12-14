public class FloatNode extends Node
{
	private float deciNum;
	//BASIC CONSTRUCTOR
	public FloatNode()
	{
		deciNum = -1;
	}
	//PARAMETERIZED CONSTRUCTOR
	public FloatNode(float val)
	{
		deciNum = val;
	}
	//RETURN VALUE OF FLOAT WE ARE STORING WITHIN THIS NODE
	public float getVal()
	{
		return deciNum;
	}
	//RETURNS VALUE AS A STRING
	public String toString()
	{
		return Float.toString(deciNum);
	}











}