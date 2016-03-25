package command.library;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import command.framework.Outputter;

import filesystem.Drive;

/**
 * Befehl zum Anzeigen und Setzen der Zeit
 * 
 * @author fu, mak
 */
public class CmdTime extends CmdDateTime {

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected CmdTime(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        if (getParameters().isEmpty()) {
            return true;
        }

        if (getParameters().get(0).matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setLenient(false);
            try {
                formatter.parse(getParameters().get(0));
                return true;
            } catch (ParseException e) {
                outputter.printLn("Invalid time input.");
                return false;
            }
        } else {
            outputter.printLn("Wrong time format. It must be HH:MM:SS");
            return false;
        }
    }

    @Override
    public void execute(Outputter outputter) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis() + delta);
        if (!getParameters().isEmpty()) {
            StringTokenizer st = new StringTokenizer(getParameters().get(0), ":");
            if (st.countTokens() == 3) {
                long systemTime = System.currentTimeMillis();
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
                c.set(Calendar.MINUTE, Integer.parseInt(st.nextToken()));
                c.set(Calendar.SECOND, Integer.parseInt(st.nextToken()));
                delta = c.getTimeInMillis() - systemTime;
            } else {
                outputter.printLn("The new time could not be set.");
            }
        }
        String time = DateFormat.getTimeInstance().format(c.getTime());
        String output = String.format("the current time is %s", time);
        outputter.printLn(output);
    }

}
