package com.liashenko.departments.main;


import com.liashenko.departments.userInterface.CommandsParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ModifiedStaffStructureConstructor {
//    static final Logger logger = LogManager.getLogger(ModifiedStaffStructureConstructor.class.getName());
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger classLogger = LogManager.getLogger(ModifiedStaffStructureConstructor.class);

    public static void main(String[] args) {
        BufferedReader br = null;
        boolean isContinue = true;
        classLogger.info("Logger run");
//        if (ConnectionUtils.isTablesExist()) {
            CommandsParser comParser = new CommandsParser();
            System.out.print("Enter command and press <Enter> (\"help\" for help).");
            System.out.println();
//            new MainServiceImpl().printConnectInfo();
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
            System.out.println("Exit!");
            System.exit(0);
//        } else {
//            System.out.print("Couldn't create tables in database `STAFF`.\nExit.");
//        }
    }
}
