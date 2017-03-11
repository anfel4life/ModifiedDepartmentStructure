package com.liashenko.departments.userInterface;


public class CommandsParser {
    private static final String INCORRECT_INPUT = "Incorrect input! Enter \"help\" for commands list. ";

    private CommandsController comControl;

    public CommandsParser() {
        comControl = new CommandsControllerImpl();
    }

    private static String[] enterUsersCommand(String str) {
        str = str.trim();
        return str.split(" ");
    }

    public String usersCommandProcessing(String str) {
        String resultMessage;
        String[] commandsArr = enterUsersCommand(str);
        switch (commandsArr[0].toLowerCase()) {
            case "":
                resultMessage = "You didn't enter any commands";
                break;
            case "create":
                resultMessage = (commandsArr.length > 1) ? createStaffUnit(commandsArr) : INCORRECT_INPUT;
                break;

            case "rm":
                resultMessage = (commandsArr.length > 1) ? removeStaffUnitParse(commandsArr) : INCORRECT_INPUT;
                break;

            case "open":
                resultMessage = (commandsArr.length > 1) ? openStaffTree(commandsArr) : INCORRECT_INPUT;
                break;

            case "help":
                resultMessage = comControl.help();
                break;

            case "departments":
                resultMessage = comControl.departmentsList();
                break;

            case "update":
                resultMessage = (commandsArr.length > 1) ? updateStaffUnitParse(commandsArr) : INCORRECT_INPUT;
                break;

//            case "exit":
//                resultMessage = (commandsArr.length > 1) ? System.exit(0) : INCORRECT_INPUT;
//                break;

            case "all":
                resultMessage = comControl.all();
                break;

            case "top":
                resultMessage = (commandsArr.length >= 1) ? departmentWithTopEmployeesParse(commandsArr)
                        : INCORRECT_INPUT;
                break;

            case "search":
                resultMessage = (commandsArr.length >= 1) ? searchEmployeeParse(commandsArr)
                        : INCORRECT_INPUT;
                break;

            default:
                resultMessage = INCORRECT_INPUT;
        }
        return resultMessage;
    }

    //create -e|-d
    private String createStaffUnit(String[] commandsArr) {
        String resultMessage;
        switch (commandsArr[1].toLowerCase()) {
            case "-d":
                //"create -d";
                resultMessage = (commandsArr.length >= 3) ? comControl.createNewDepartment(commandsArr[2])
                        : INCORRECT_INPUT;
                break;

            case "-e":
                //"create -e";
                resultMessage = (commandsArr.length >= 3) ? createEmployeeParse(commandsArr)
                        : INCORRECT_INPUT;
                break;

            default:
                resultMessage = INCORRECT_INPUT;
        }
        return resultMessage;
    }

    //update -e
    private String updateStaffUnitParse(String[] commandsArr) {
        String resultMessage = INCORRECT_INPUT;
        if (commandsArr[1].toLowerCase().equals("-e")) {
            resultMessage = (commandsArr.length >= 3) ? updateEmployeeParse(commandsArr) : INCORRECT_INPUT;
        }
        return resultMessage;
    }

    //open -d | open -e
    private String openStaffTree(String[] commandsArr) {
        String resultMessage = INCORRECT_INPUT;
        if (commandsArr.length >= 3) {
            switch (commandsArr[1].toLowerCase()) {
                case "-d":
                    resultMessage = comControl.openDepartment(commandsArr[2]);
                    break;
                case "-e":
                    resultMessage = comControl.openEmployee(commandsArr[2]);
                    break;
                default:
                    resultMessage = INCORRECT_INPUT;
            }
        }
        return resultMessage;
    }

    //rm -d | rm -e
    private String removeStaffUnitParse(String[] commandsArr) {
        String resultMessage = INCORRECT_INPUT;
        if (commandsArr.length >= 3) {
            switch (commandsArr[1].toLowerCase()) {
                case "-d":
                    resultMessage = comControl.removeDepartment(commandsArr[2]);
                    break;
                case "-e":
                    resultMessage = comControl.removeEmployee(commandsArr[2]);
                    break;
                default:
                    resultMessage = INCORRECT_INPUT;
            }
        }
        return resultMessage;
    }

    //search -e -a age_to_search -d department
    private String searchEmployeeParse(String[] commandsArr) {
        String searchKey = "";
        String age = "";
        String department = "";
        boolean isSearchByAgeInDepartment = false;
        int arrElemCounter = commandsArr.length - 1;
        for (int i = 0; i < commandsArr.length; i++) {
            if (commandsArr[i].isEmpty()) continue;

            if (commandsArr[i].equals("-e") && i < arrElemCounter) {
                searchKey = commandsArr[i + 1];
                if (searchKey.equals("-a")) {
                    isSearchByAgeInDepartment = true;
                }
            } else if (commandsArr[i].equals("-d") && i < arrElemCounter) {
                department = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-a") && i < arrElemCounter) {
                age = commandsArr[i + 1];
            }
        }

        if (department.isEmpty() || age.isEmpty() || !isSearchByAgeInDepartment) {
            return INCORRECT_INPUT;
        }
        return comControl.searchEmployeeInDepartmentByAge(department, age);
    }

    //top -d -t type_of_employee
    private String departmentWithTopEmployeesParse(String[] commandsArr) {
        boolean isFilterEmployeeType = false;
        String filter = "";
        String employeeType = "";

        int arrElemCounter = commandsArr.length - 1;

        for (int i = 0; i < commandsArr.length; i++) {
            if (commandsArr[i].isEmpty()) continue;

            if (commandsArr[i].equals("-d") && i < arrElemCounter) {
                filter = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-t") && i < arrElemCounter) {
                employeeType = commandsArr[i + 1];
                if (filter.equals("-t")) {
                    isFilterEmployeeType = true;
                    employeeType = (employeeType.equals("d") || employeeType.equals("m"))
                            ? employeeType : "";
                    break;
                }
            }
        }
        if (employeeType.isEmpty() || !isFilterEmployeeType) {
            return INCORRECT_INPUT;
        }
        return comControl.searchDepartmentWithTopEmployees(employeeType);
    }

    //create -e dn department_name -n employee_name -t m|d  -a age -m\-l
    //create -e -n employee_name -t m|d -a age -m|-l Canban|Java
    private String createEmployeeParse(String[] commandsArr) {
        String name = "";
        String type = "";
        String age = "";
        String language = "";
        String methodology = "";
        String department = "";

        int arrElemCounter = commandsArr.length - 1;
        for (int i = 0; i < commandsArr.length; i++) {
            if (commandsArr[i].isEmpty()) continue;

            if (commandsArr[i].equals("-dn") && i < arrElemCounter) {
                department = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-n") && i < arrElemCounter) {
                name = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-t") && i < arrElemCounter) {
                type = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-a") && i < arrElemCounter) {
                age = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-l") && i < arrElemCounter) {
                language = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-m") && i < arrElemCounter) {
                methodology = commandsArr[i + 1];
            }
        }

        if (type.equals("d") && !methodology.isEmpty()) {
            return "The developer doesn\'t have methodology field";
        } else if (type.equals("m") && !language.isEmpty()) {
            return "The manager doesn\'t have methodology language";
        }
        if (!department.isEmpty()) {
            return comControl.createEmployee(department, name, type, language, methodology, age);
        }
        return comControl.createEmployee(name, type, language, methodology, age);
    }

    //update -e employee_id -n employee_name -a short -m|-l Canban|Java
    private String updateEmployeeParse(String[] commandsArr) {
        String id = "0";
        String name = "";
        String age = "";
        String skillKey = "";
        String skill = "";

        int arrElemCounter = commandsArr.length - 1;
        for (int i = 0; i < commandsArr.length; i++) {
            if (commandsArr[i].isEmpty()) continue;
            if (commandsArr[i].equals("-e") && i < arrElemCounter) {
                id = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-n") && i < arrElemCounter) {
                name = commandsArr[i + 1];
            } else if (commandsArr[i].equals("-a") && i < arrElemCounter) {
                age = commandsArr[i + 1];
            } else if ((commandsArr[i].equals("-l") || commandsArr[i].equals("-m")) && i < arrElemCounter) {
                skillKey = commandsArr[i];
                skill = commandsArr[i + 1];
            }
        }
        return comControl.updateEmployee(id, name, skillKey, skill, age);
    }
}
