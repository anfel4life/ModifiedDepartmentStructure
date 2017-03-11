package com.liashenko.departments.main;


import com.liashenko.departments.userInterface.CommandsParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ModifiedStaffStructureConstructor {
    private static final Logger rootLogger = LogManager.getRootLogger();

    public static void main(String[] args) {
        BufferedReader br = null;
        boolean isContinue = true;

        rootLogger.info("Application has been run.");
        CommandsParser comParser = new CommandsParser();
        System.out.print("Enter command and press <Enter> (\"help\" for help).");
        System.out.println();
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (isContinue) {
                System.out.print(">_ ");
                String input = br.readLine();
                if ("exit".equals(input)) {
                    isContinue = false;
                } else {
                    System.out.println(comParser.usersCommandProcessing(input));
                    System.out.println("-----------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        rootLogger.info("Exit");
        System.out.println("Exit!");
        System.exit(0);
    }
}
