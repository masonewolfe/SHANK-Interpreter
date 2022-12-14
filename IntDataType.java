public class IntDataType extends InterpreterDataType
{
	public int value;
	
	public IntDataType()
	{
		
	}
	public IntDataType(int num)
	{
		value = num;
	}
	public String toString()
	{
		return Integer.toString(value);
	}
	
	public void fromString(String input)
	{
		try
		{
			value = Integer.parseInt(input);
		}
		catch(Exception e)
		{
			throw new RuntimeException("COULD NOT PARSE STRING INTO INTEGER FOR INTDATATYPE");
		}
	}
}