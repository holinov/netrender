package org.fruttech.rendering;

import com.google.common.base.Strings;
import com.google.inject.Injector;
import org.apache.commons.cli.*;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.services.KafkaProducerService;

/**
 * CLI interface for publishing render jobs
 */
public class CliClient {
    public static void main(String[] args) {


        final AppArgs appArgs = parseArgs(args);
        if (!appArgs.wrongSettings) {
            final Injector injector = ApplicationContext
                    .withModules(
                            new PropertiesModule(),
                            new KafkaModule()
                    ).getInjector();

            final KafkaProducerService kafkaProducerService = injector.getInstance(KafkaProducerService.class);
            kafkaProducerService.sendJob(new RenderingJob(appArgs.scene, appArgs.from, appArgs.to));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                /* NOP */
            }
        }
    }

    private static AppArgs parseArgs(String[] args) {
        final Options options = new Options();

        final Option fileOption = Option.builder("f")
                .longOpt("scene")
                .argName("file")
                .hasArg()
                .desc("scene to start render job for")
                .build();

        final Option startOption = Option.builder("s")
                .longOpt("startFrame")
                .argName("start")
                .hasArg()
                .desc("start from frame")
                .build();

        final Option endOption = Option.builder("e")
                .longOpt("endFrame")
                .argName("end")
                .hasArg()
                .desc("end with frame")
                .build();


        options.addOption(fileOption);
        options.addOption(startOption);
        options.addOption(endOption);

        final AppArgs appArgs = new AppArgs();

        // create the parser
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            appArgs.from = Integer.parseInt(line.getOptionValue("s"));
            appArgs.to = Integer.parseInt(line.getOptionValue("s"));
            appArgs.scene = line.getOptionValue("f");
        } catch (Exception exp) {
            printExitMessageWithHelp("Wrong command line.", options);
            appArgs.wrongSettings = true;
        }

        return appArgs;
    }

    private static void printExitMessageWithHelp(String message, Options options) {
        if (!Strings.isNullOrEmpty(message)) System.err.println(message);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("CliClient", options);
    }
}
