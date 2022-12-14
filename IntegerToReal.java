import java.util.*;

public class IntegerToReal extends BuiltInFunctionNode
{
	String functionName = "IntegerToReal";
	boolean isVariadic = false;
	
	
	public void Execute(List<InterpreterDataType> data)
	{
		if(data.size()==2 && data.get(0) instanceof IntDataType && data.get(1) instanceof FloatDataType)
		{
			try
			{
				float num = (float)Integer.parseInt(data.get(0).toString());
				data.get(1).fromString(Float.toString(num));
			}catch(Exception e)
			{
				throw new RuntimeException("Value passed to IntegerToReal has no value");
			}
			
		}
		else
		{
			throw new RuntimeException("Wrong arguments passed to IntToReal");
		}
	}
}