package configuration;

import command.framework.Outputter;

public class DemoConfigurator extends Configurator {

    private static final Outputter NULL_OUTPUTTER = new Outputter() {
        @Override
        public char readSingleCharacter() {
            return 0;
        }

        @Override
        public void printLn(String line) {
        }

        @Override
        public void print(String text) {
        }

        @Override
        public void newline() {
        }
    };

    @Override
    public void configureBaseSystem() {
        super.configureBaseSystem();

        invoker.executeCommand("mkdir a", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir b", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir c", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile 1", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile 2 22222222", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile 3 33333333", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile 4.txt 4444444", NULL_OUTPUTTER);

        invoker.executeCommand("cd a", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir x", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir y", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile f fffffffff", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile ff.txt xxxxxxxxx", NULL_OUTPUTTER);

        invoker.executeCommand("cd ..", NULL_OUTPUTTER);

        invoker.executeCommand("cd b", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir xx", NULL_OUTPUTTER);
        invoker.executeCommand("mkdir yy", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile r rrrrrrrrrrr", NULL_OUTPUTTER);
        invoker.executeCommand("mkfile rr.txt yyyyyyyyyyy", NULL_OUTPUTTER);

        invoker.executeCommand("cd ..", NULL_OUTPUTTER);
    }

    /**
     * Method main(). Called initially.
     */
    public static void main(String[] args) {
        Configurator config = new DemoConfigurator();
        config.startConsole();
    }
}
