import java.util.*;

public class Read extends BuiltInFunctionNode
{
	String functionName = "Read";
	boolean isVariadic = true;
	
	
	public void Execute(List<InterpreterDataType> data)
	{
		Scanner myObj = new Scanner(System.in);
		for(InterpreterDataType idt : data)
		{
			idt.fromString(myObj.nextLine());
		}
	}
}