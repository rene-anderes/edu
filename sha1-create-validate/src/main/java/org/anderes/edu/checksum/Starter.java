package org.anderes.edu.checksum;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Starter {

    private final static Path APPLICATIONLOGPATH = Paths.get("ChecksumNG-application.log");
    private final static Path ERRORLOGPATH = Paths.get("ChecksumNG-error.log");
    static {
        LogConfigurationBuilder.init(APPLICATIONLOGPATH, ERRORLOGPATH);
    }
    
    public static void main(String[] commandLineArguments) {
        final Logger logger = LogManager.getLogger();
        logger.info("-------------------- ChecksumNG startet ---------------------");
        logger.info("Application-Log path: '{}'", APPLICATIONLOGPATH.toAbsolutePath());
        logger.info("Error-Log path: '{}'", ERRORLOGPATH.toAbsolutePath());
        logger.info("used algorithm: {}", "SHA1");
        try {
            final CommandLine cmdLine = generateCommandLine(generateOptions(), commandLineArguments);
            if (!cmdLine.hasOption('d') && !cmdLine.hasOption('v')) {
                logger.error("Invalid specification of the parameters");
                logger.warn("-------------------- ChecksumNG completed NOT successfully --\n");
                System.exit(1);
            }
            if (cmdLine.hasOption('d')) {
                final Path theDirectory = Paths.get(cmdLine.getOptionValue('d'));
                final TheCreator creator = TheCreator.build();
                if (cmdLine.hasOption('b')) {
                    final Path theBlacklist = Paths.get(cmdLine.getOptionValue('b'));
                    creator.setBlacklist(theBlacklist);
                }
                creator.createChecksumFromPath(theDirectory);
            } else if (cmdLine.hasOption('v')) {
                final Path theChecksumFile = Paths.get(cmdLine.getOptionValue('v'));
                final boolean allFilesValid = TheValidator.build().validate(theChecksumFile);
                if (!allFilesValid) {
                    logger.warn("Please check the error log file.");
                    logger.info("-------------------- ChecksumNG completed -------------------\n");
                    System.exit(3);
                }
            }
        } catch (ParseException parseException) {
            logger.error("Unable to parse command-line arguments " 
                    + Arrays.toString(commandLineArguments) + " due to: " + parseException.getMessage());
            logger.warn("-------------------- ChecksumNG completed NOT successfully --\n");
            System.exit(1);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.warn("Please check the error log file.");
            logger.warn("-------------------- ChecksumNG completed NOT successfully --\n");
            System.exit(2);
        }
        logger.info("-------------------- ChecksumNG completed successfully ------\n");
        System.exit(0);
    }

    private static CommandLine generateCommandLine(final Options options, final String[] commandLineArguments) throws ParseException {

        final CommandLineParser cmdLineParser = new DefaultParser();
        return cmdLineParser.parse(options, commandLineArguments);
    }
    
    /**
     * "Definition" stage of command-line parsing with Apache Commons CLI.
     * @return Definition of command-line options.
     */
    private static Options generateOptions() {

       final Option directoryOption = Option.builder("d")
              .required(false)
              .hasArg()
              .longOpt("directory")
              .desc("start / home directory")
              .build();
       
       final Option validateOption = Option.builder("v")
               .required(false)
               .hasArg()
               .longOpt("validate")
               .desc("validate the file")
               .build();

       final Option errorLogFileOption = Option.builder("el")
              .required(false)
              .longOpt("errorLog")
              .hasArg()
              .desc("path for error log-file")
              .build();
       
       final Option applicationLogFileOption = Option.builder("al")
               .required(false)
               .longOpt("applicationLog")
               .hasArg()
               .desc("path for application log-file")
               .build();
       final Option blacklistOption = Option.builder("b")
               .required(false)
               .longOpt("blacklist")
               .hasArg()
               .desc("blacklist file")
               .build();

       final Options options = new Options();
       options.addOption(directoryOption);
       options.addOption(errorLogFileOption);
       options.addOption(applicationLogFileOption);
       options.addOption(blacklistOption);
       options.addOption(validateOption);
       return options;

    }
}
