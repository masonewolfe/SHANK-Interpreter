public class CharDataType extends InterpreterDataType
{
	public char value;
	
	public CharDataType()
	{
		
	}
	public CharDataType(char symbol)
	{
		value = symbol;
	}
	public String toString()
	{
		return Character.toString(value);
	}
	
	public void fromString(String input)
	{
		if(input.length()==1)
		{
			value = input.charAt(0);
		}
		else{throw new RuntimeException("Cannot cast string to char");}
	}
}