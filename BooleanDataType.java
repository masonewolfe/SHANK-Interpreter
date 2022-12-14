public class BooleanDataType extends InterpreterDataType
{
	public boolean value;
	
	public BooleanDataType()
	{
		
	}
	public BooleanDataType(boolean symbol)
	{
		value = symbol;
	}
	public String toString()
	{
		return Boolean.toString(value);
	}
	
	public void fromString(String input)
	{
		if(input.equals("true"))
		{
			value = true;
		}
		else if(input.equals("false"))
		{
			value = false;
		}
		else
		{
			throw new RuntimeException("CANNOT CAST STRING TO BOOLEAN");
		}
	}
}