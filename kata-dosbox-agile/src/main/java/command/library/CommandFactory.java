/*
 * Course Agile Software Development
 */
package command.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import command.framework.Command;

import filesystem.Drive;

/**
 * The factory is responsible to create an object of every command supported and
 * to add it to the list of known commands. New commands must be added to the
 * list of known commands here.
 */
public class CommandFactory {

    private ArrayList<Command> commands;
    private Map<String, String> helpTexts;

    /**
     * Constructor; creates all known commands and adds them to the list of
     * supported commands.
     * 
     * @param drive
     *            reference to the drive, the commands operate on.
     */
    public CommandFactory(Drive drive) {
        createCommandList(drive);
        createHelpTexts();
    }

    private void createCommandList(Drive drive) {
        this.commands = new ArrayList<Command>();

        // Add commands here
        this.commands.add(new CmdDir("dir", drive));
        this.commands.add(new CmdCd("cd", drive));
        this.commands.add(new CmdCd("chdir", drive));
        this.commands.add(new CmdMkDir("mkdir", drive));
        this.commands.add(new CmdMkDir("md", drive));
        this.commands.add(new CmdMkFile("mf", drive));
        this.commands.add(new CmdMkFile("mkfile", drive));
        this.commands.add(new CmdFormat("format", drive));
    }

    private void createHelpTexts() {
        helpTexts = new HashMap<String, String>();

        // Add help-text here
        helpTexts.put("dir", "Displays a list of files and subdirectories in a directory.");
        helpTexts.put("cd", "Displays the name of or changes the current directory.");
        helpTexts.put("chdir", "Displays the name of or changes the current directory.");
        helpTexts.put("mkdir", "Creates a directory.");
        helpTexts.put("md", "Creates a directory.");
        helpTexts.put("mf", "Creates a file.");
        helpTexts.put("mkfile", "Creates a file.");
        helpTexts.put("format", "Formats a disk.");
    }

    /**
     * Returns the list of known commands. Is called at configuration time to
     * transfer the supported commands to the invoker.
     * 
     * @return list of known commands.
     */
    public ArrayList<Command> getCommandList() {
        return commands;
    }

    /**
     * Gibt für das entsprechende Kommand den Helptext zurück.
     * 
     * @param commandName
     *            Commandname
     * @return Text oder null, wenn kein Hilfetext existiert
     */
    public String getHelpForCommand(String commandName) {
        return helpTexts.get(commandName);
    }
}
