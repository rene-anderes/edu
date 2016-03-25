package command.library;

import java.util.List;

import command.framework.Command;
import command.framework.Outputter;
import filesystem.Drive;

/**
 * Gibt das Help für ein entsprechendes Commando aus.
 * 
 * @author ra, hum
 *
 */
public class CmdHelp extends Command {
	
	private CommandFactory commandFactory;

    public CmdHelp(String cmdName, Drive drive, CommandFactory commandFactory) {
		super(cmdName, drive);
		this.commandFactory = commandFactory;
		
	}

	@Override
	protected boolean checkNumberOfParameters(int number) {
		return (number == 0 || number == 1);
	}

	@Override
	protected boolean checkParameterValues(Outputter outputter) {
		if (this.getParameters().size() == 0) {
			return true;
		}
		String commandName = this.getParameters().get(0).toLowerCase();
		List<Command> commands = commandFactory.getCommandList();
		for (Command command : commands) {
			if (command.compareCmdName(commandName)) {
				return true;
			}
		}
		outputter.printLn("Command does not exists");
		return false;
	}

	@Override
	public void execute(Outputter outputter) {
		if (this.getParameters().size() == 0) {
			this.printAllCommand(outputter);
		} else {
			String commandName = this.getParameters().get(0).toLowerCase();
			if (commandName.equalsIgnoreCase(toString())) {
				this.printAllCommand(outputter);
			} else {
				this.printHelpForCommand(outputter, commandName);
			}
		}
		
	}

	private void printHelpForCommand(Outputter outputter, String commandName) {
		outputter.printLn(commandFactory.getHelpForCommand(commandName));
		
	}

	private void printAllCommand(Outputter outputter) {
		List<Command> commands = commandFactory.getCommandList();
		outputter.printLn("For more information on a specific command, type HELP command-name");
		for (Command command : commands) {
			outputter.printLn(command.toString());
		}
		
	}

}
