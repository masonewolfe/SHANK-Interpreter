import java.util.*;
abstract class BuiltInFunctionNode extends CallableNode
{
	public boolean isVariadic;
	abstract void Execute(List<InterpreterDataType> data);
}