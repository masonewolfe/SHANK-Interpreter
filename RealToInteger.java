import java.util.*;

public class RealToInteger extends BuiltInFunctionNode
{
	String functionName = "RealToInteger";
	boolean isVariadic = false;
	
	
	public void Execute(List<InterpreterDataType> data)
	{
		if(data.size()==2 && data.get(0) instanceof FloatDataType && data.get(1) instanceof IntDataType)
		{
			try
			{
				int num = (int)Float.parseFloat(data.get(0).toString());
				data.get(1).fromString(Integer.toString(num));
			}catch(Exception e)
			{
				throw new RuntimeException("Value passed to RealToInteger has no value");
			}
			
		}
		else
		{
			throw new RuntimeException("Wrong amount of arguments passed to RealToint");
		}
	}
}