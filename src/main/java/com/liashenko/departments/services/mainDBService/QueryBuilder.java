package com.liashenko.departments.services.mainDBService;


public abstract class QueryBuilder {

    //table EMPLOYEE
    public static final String EMPLOYEE_TABLE_NAME = "EMPLOYEE";
    public static final String EMPLOYEE_COL_LANGUAGE = "language";
    public static final String EMPLOYEE_COL_METHODOLOGY = "methodology";
    public static final String EMPLOYEE_COL_DEPARTMENT_ID = "department_id";
    public static final String EMPLOYEE_COL_AGE = "age";
    public static final String EMPLOYEE_COL_TYPE = "type";
    public static final String EMPLOYEE_COL_NAME = "name";
    public static final String EMPLOYEE_COL_ID = "id";

    public static final String EMPLOYEE_NAME_FIELD_LENGTH = "25";
    public static final String EMPLOYEE_LANGUAGE_FIELD_LENGTH = "20";
    public static final String EMPLOYEE_METHODOLOGY_FIELD_LENGTH = "20";
    public static final String DEPARTMENT_NAME_FIELD_LENGTH = "45";

    //table DEPARTMENT
    public static final String DEPARTMENT_TABLE_NAME = "DEPARTMENT";
    public static final String DEPARTMENT_COL_NAME = "name";
    public static final String DEPARTMENT_COL_ID = "id";

    //Alias for column "name" in the table "DEPARTMENT"
    public static final String EMPLOYEE_COL_DEPARTMENT_NAME = "department name";

    public static final String[] ALL_EMPLOYEE_VIEW_COLUMNS_ARR = {
            QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_NAME,
            QueryBuilder.DEPARTMENT_TABLE_NAME + "." + QueryBuilder.DEPARTMENT_COL_NAME,
            QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_TYPE,
            QueryBuilder.EMPLOYEE_TABLE_NAME + "." + QueryBuilder.EMPLOYEE_COL_AGE
    };
    private final static String DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME = "DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES";
    private final static String TOP_DEPARTMENT_TEMP_TABLE_NAME = "TOP_DEPARTMENT";
    private final static String TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME = "TOP_DEPARTMENTS_WITH_ID";
    private final static String TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE = "EMPLOYEES_FROM_DEPARTMENT_BY_AGE";
    private static final String EMPLOYEE_ID_FIELD_LENGTH = "11";
    private static final String EMPLOYEE_DEPARTMENT_FIELD_LENGTH = "11";
    private static final String EMPLOYEE_TYPE_FIELD_LENGTH = "20";
    private static final String EMPLOYEE_AGE_FIELD_LENGTH = "3";
    private static final String DEPARTMENT_ID_FIELD_LENGTH = "11";

    public static final String createEmployeeTableQuery(){

    return "CREATE TABLE IF NOT EXISTS "+"`STAFF`.`" + EMPLOYEE_TABLE_NAME + "` ("
            + "`" + EMPLOYEE_COL_ID + "` INT(" + EMPLOYEE_ID_FIELD_LENGTH + ") NOT NULL AUTO_INCREMENT, "
            + "`" + EMPLOYEE_COL_NAME + "` VARCHAR("+EMPLOYEE_NAME_FIELD_LENGTH + ") NULL DEFAULT NULL, "
            + "`" + EMPLOYEE_COL_DEPARTMENT_ID + "` INT(" + EMPLOYEE_DEPARTMENT_FIELD_LENGTH + ") NOT NULL, "
            + "`" + EMPLOYEE_COL_TYPE + "` VARCHAR(" + EMPLOYEE_TYPE_FIELD_LENGTH + ") NOT NULL, "
            + "`" + EMPLOYEE_COL_LANGUAGE + "` VARCHAR(" + EMPLOYEE_LANGUAGE_FIELD_LENGTH + ") NULL DEFAULT NULL, "
            + "`" + EMPLOYEE_COL_METHODOLOGY + "` VARCHAR(" + EMPLOYEE_METHODOLOGY_FIELD_LENGTH + ") NULL DEFAULT NULL, "
            + "`" + EMPLOYEE_COL_AGE + "` SMALLINT(" + EMPLOYEE_AGE_FIELD_LENGTH + ") NULL DEFAULT NULL, "
            + " PRIMARY KEY (`" + EMPLOYEE_COL_ID + "`),  "
            + " UNIQUE INDEX `id_UNIQUE` (`" + EMPLOYEE_COL_ID + "` ASC)) "
            + " ENGINE = InnoDB "
            + " DEFAULT CHARACTER SET = utf8";
    }

    public static String createDepartmentTableQuery() {
        return  "CREATE TABLE IF NOT EXISTS "
                + "`STAFF`.`" + DEPARTMENT_TABLE_NAME + "` ("
                + "`" + DEPARTMENT_COL_ID + "` INT(" + DEPARTMENT_ID_FIELD_LENGTH + ") NOT NULL AUTO_INCREMENT, "
                + "`" + DEPARTMENT_COL_NAME + "` VARCHAR(" + DEPARTMENT_NAME_FIELD_LENGTH + ") NOT NULL, "
                + "PRIMARY KEY (`" + DEPARTMENT_COL_ID + "`), "
                + "UNIQUE INDEX `id_UNIQUE` (`" + DEPARTMENT_COL_ID + "` ASC), "
                + "UNIQUE INDEX `name_UNIQUE` (`" + DEPARTMENT_COL_NAME + "` ASC)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8";
    }

    public static String createDepartmentQuery() {
        return "INSERT INTO " + DEPARTMENT_TABLE_NAME + "("
                + "`" + DEPARTMENT_COL_NAME + "`" + ") VALUES (?);";
    }

    public static String getDepartmentListQuery() {
        return "SELECT * FROM " + DEPARTMENT_TABLE_NAME + ";";
    }

    public static String getDepartmentByNameQuery() {
        return "SELECT * FROM " + DEPARTMENT_TABLE_NAME
                + " WHERE " + DEPARTMENT_COL_NAME + "=?;";
    }

    public static String getDepartmentByIdQuery() {
        return "SELECT * FROM " + DEPARTMENT_TABLE_NAME
                + " WHERE " + DEPARTMENT_COL_ID + "=?;";
    }

    public static String getEmployeeByIdQuery() {
        return "SELECT * FROM " + EMPLOYEE_TABLE_NAME
                + " WHERE " + EMPLOYEE_COL_ID + "=?;";
    }

    public static String updateEmployeeQuery() {
        return "UPDATE " + EMPLOYEE_TABLE_NAME + " SET "
                + EMPLOYEE_COL_NAME + "=?, "
                + EMPLOYEE_COL_LANGUAGE + "=?, "
                + EMPLOYEE_COL_METHODOLOGY + "=?, "
                + EMPLOYEE_COL_AGE + "=? "
                + "WHERE " + EMPLOYEE_COL_ID + "=?;";
    }

    public static String getAllEmployeeViewQuery() {
        return "SELECT " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_NAME + ", "
                + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_NAME + ", "
                + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_TYPE + ", "
                + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_AGE
                + " FROM " + EMPLOYEE_TABLE_NAME + ", " + DEPARTMENT_TABLE_NAME
                + " WHERE " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID
                + "=" + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_ID
                + " ORDER BY " + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_NAME + ";";
    }

    public static String getLastDepartmentQuery() {
        return "SELECT MAX(" + DEPARTMENT_COL_NAME + ")"
                + " FROM " + DEPARTMENT_TABLE_NAME + ";";
    }

    public static String dropTempTableForDepartmentsWithMaxCountsQuery() {
        return "DROP TEMPORARY TABLE IF EXISTS "
                + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + ";";
    }

    public static String dropTempTableForTopDepartmentQuery() {
        return "DROP TEMPORARY TABLE IF EXISTS " + TOP_DEPARTMENT_TEMP_TABLE_NAME + ";";
    }

    public static String dropTempTableForTopDepartmentsWithIdQuery() {
        return "DROP TEMPORARY TABLE IF EXISTS " + TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME + ";";
    }

    public static String dropTempTableWithEmployeesFromDepartmentByAge() {
        return "DROP TEMPORARY TABLE IF EXISTS " + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE + ";";
    }

    public static String createTempTableWithEmployeesFromDepartmentByAge() {
        return " CREATE TEMPORARY TABLE " + "`" + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE + "`"
                + "SELECT * FROM " + EMPLOYEE_TABLE_NAME
                + " WHERE " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_AGE + "=?"
                + " AND " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + "="
                + "(SELECT " + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_ID
                + " FROM " + DEPARTMENT_TABLE_NAME
                + " WHERE " + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_NAME + "= ?);";
    }

    public static String getEmployeesFromDepartmentByAge() {
        return "SELECT " + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE + "." + EMPLOYEE_COL_NAME + ", "
                + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE + "." + EMPLOYEE_COL_TYPE + ", "
                + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_NAME + " AS " + "\'" + EMPLOYEE_COL_DEPARTMENT_NAME + "\'"
                + " FROM " + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE
                + " JOIN " + DEPARTMENT_TABLE_NAME
                + " ON " + TABLE_WITH_EMPLOYEES_FROM_DEPARTMENT_BY_AGE + "." + EMPLOYEE_COL_DEPARTMENT_ID
                + "=" + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_ID + ";";
    }


    public static String createTempTableForDepartmentsWithMaxCountsQuery() {
        return " CREATE TEMPORARY TABLE " + "`" + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + "`"
                + " SELECT " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + ", "
                + " COUNT(" + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_ID + ") AS \'employees\'"
                + " FROM " + EMPLOYEE_TABLE_NAME
                + " WHERE " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_TYPE + "= ?"
                + " GROUP BY " + EMPLOYEE_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + ";";
    }

    public static String createTempTableForTopDepartmentQuery() {
        return " CREATE TEMPORARY TABLE " + "`" + TOP_DEPARTMENT_TEMP_TABLE_NAME + "`"
                + " SELECT "
                + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + ", "
                + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + ".employees "
                + " FROM "
                + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME
                + " LIMIT 1" + ";";
    }

    public static String createTempTableForTopDepartmentsWithIdQuery() {
        return " CREATE TEMPORARY TABLE " + "`" + TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME + "`"
                + " SELECT " + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + ", "
                + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + ".employees"
                + " FROM " + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME
                + " JOIN " + TOP_DEPARTMENT_TEMP_TABLE_NAME
                + " ON " + DEPARTMENTS_WITH_MAX_COUNT_EMPLOYEES_TEMP_TABLE_NAME + ".employees "
                + "= " + TOP_DEPARTMENT_TEMP_TABLE_NAME + ".employees;";
    }

    public static String searchDepartmentWithTopEmployeesQuery() {
        return " SELECT " + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_NAME + ", "
                + TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME + ".employees"
                + " FROM " + TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME
                + " JOIN " + DEPARTMENT_TABLE_NAME
                + " ON " + TOP_DEPARTMENTS_WITH_ID_TEMP_TABLE_NAME + "." + EMPLOYEE_COL_DEPARTMENT_ID + "="
                + DEPARTMENT_TABLE_NAME + "." + DEPARTMENT_COL_ID + ";";
    }

    public static String removeDepartmentQuery() {
        return "DELETE FROM " + DEPARTMENT_TABLE_NAME + " WHERE "
               + DEPARTMENT_COL_NAME + "=?;";
    }

    public static String getEmployeesListQuery() {
        return "SELECT * FROM " + EMPLOYEE_TABLE_NAME + " WHERE "
                + EMPLOYEE_COL_DEPARTMENT_ID + "=?;";
    }

    public static String removeEmployeeQuery() {
        return "DELETE FROM " + EMPLOYEE_TABLE_NAME + " WHERE "
                + EMPLOYEE_COL_ID + "=?;";
    }

    public static String addNewEmployeeQuery() {
        return "INSERT INTO " + EMPLOYEE_TABLE_NAME
                + " ("
                + EMPLOYEE_COL_NAME + ", "
                + EMPLOYEE_COL_DEPARTMENT_ID + ", "
                + EMPLOYEE_COL_TYPE + ", "
                + EMPLOYEE_COL_LANGUAGE + ", "
                + EMPLOYEE_COL_METHODOLOGY + ", "
                + EMPLOYEE_COL_AGE
                + ") "
                + "VALUES (?,?,?,?,?,?);";
    }
}
