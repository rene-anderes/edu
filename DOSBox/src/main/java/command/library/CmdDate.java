package command.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import command.framework.Outputter;

import filesystem.Drive;

/**
 * Befehl zum Anzeigen und Setzen des Datums
 * 
 * @author fu, mak
 */
public class CmdDate extends CmdDateTime {

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected CmdDate(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        if (getParameters().isEmpty()) {
            return true;
        }

        if (getParameters().get(0).matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            try {
                formatter.parse(getParameters().get(0));
                return true;
            } catch (ParseException e) {
                outputter.printLn("Invalid date input.");
                return false;
            }
        } else {
            outputter.printLn("Wrong date format. It must be YYYY-MM-DD.");
            return false;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void execute(Outputter outputter) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis() + delta);
        if (!getParameters().isEmpty()) {
            StringTokenizer st = new StringTokenizer(getParameters().get(0), "-");
            if (st.countTokens() == 3) {
                long systemTime = System.currentTimeMillis();
                c.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));
                c.set(Calendar.MONTH, Integer.parseInt(st.nextToken()) - 1); // 0-basiert!!!
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
                delta = c.getTimeInMillis() - systemTime;
            } else {
                outputter.printLn("The new date could not be set.");
            }
        }
        String output = String.format("the current date is %tY-%tm-%td", c, c, c);
        outputter.printLn(output);
    }

}
