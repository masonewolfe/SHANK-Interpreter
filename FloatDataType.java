public class FloatDataType extends InterpreterDataType
{
	public float value;
	
	public FloatDataType()
	{
		
	}
	public FloatDataType(float num)
	{
		value = num;
	}
	public String toString()
	{
		return Float.toString(value);
	}
	
	public void fromString(String input)
	{
		try
		{
			value = Float.parseFloat(input);
		}
		catch(Exception e)
		{
			throw new RuntimeException("COULD NOT PARSE STRING INTO FLOAT FOR FLOATDATATYPE");
		}
	}











}