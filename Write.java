import java.util.*;

public class Write extends BuiltInFunctionNode
{
	String functionName = "Write";
	boolean isVariadic = true;
	
	public void Execute(List<InterpreterDataType> data)
	{
		for(InterpreterDataType dt : data)
		{
			System.out.print(dt.toString()+" ");
		}
		System.out.println();
	}
}