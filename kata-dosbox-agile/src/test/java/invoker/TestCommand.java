/*
 * Course Agile Software Development
 */ 
package invoker;

import java.util.ArrayList;

import command.framework.Command;
import command.framework.Outputter;

import filesystem.Drive;

public class TestCommand extends Command {

	public boolean executed = false;
	public int numberOfParamsPassed = -1;
	public boolean checkNumberOfParametersReturnValue = true;
	public boolean checkParameterValuesReturnValue = true;

	protected TestCommand(String cmdName, Drive drive) {
		super(cmdName, drive);
	}
	
	public ArrayList<String> getParams() {
		return this.getParameters();
	}
	
	@Override
	protected boolean checkNumberOfParameters(int number) {
		this.numberOfParamsPassed  = number;
		return this.checkNumberOfParametersReturnValue;
	}
	
	@Override
	protected boolean checkParameterValues(Outputter outputter) {
		return this.checkParameterValuesReturnValue;
	}

	@Override
	public void execute(Outputter outputter) {
		this.executed  = true;
	}

}
