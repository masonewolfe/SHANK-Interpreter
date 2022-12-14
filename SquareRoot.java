import java.util.*;
import java.lang.Math; 

public class SquareRoot extends BuiltInFunctionNode
{
	String functionName = "SquareRoot";
	boolean isVariadic = false;

	
	public void Execute(List<InterpreterDataType> data)
	{
		if(data.size()==2)
		{
			if(data.get(0) instanceof FloatDataType && data.get(1) instanceof FloatDataType)
			{
				try
				{
					Double ans = Double.parseDouble(data.get(0).toString());
					ans = Math.sqrt(ans);
					data.get(1).fromString(Double.toString(ans));
					
				}catch(Exception e)
				{
					throw new RuntimeException("Variable passed hasnt been instaniated with a value");
				}
			}
			else
			{
				throw new RuntimeException("Value passed is not a float");
			}
		}
		else
		{
			throw new RuntimeException("SquareRoot only excepts one number");
		}
	}
}