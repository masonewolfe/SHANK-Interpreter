public class StringDataType extends InterpreterDataType
{
	public String value;
	
	public StringDataType()
	{
		
	}
	public StringDataType(String line)
	{
		value = line;
	}
	public String toString()
	{
		return value;
	}
	
	public void fromString(String input)
	{
		value = input;
	}
}