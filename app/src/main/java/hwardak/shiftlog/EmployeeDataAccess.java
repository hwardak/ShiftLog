package hwardak.shiftlog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HWardak on 2017-05-09.
 */


/**
 * This class is the data access layer, in between the database file and the UI.
 * Handles all the Employee Table requests; adding, modifying, deleting and retrieving Employee data.
 */
public class EmployeeDataAccess {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String LOGTAG = "DATABASE E: ";


    public static final String[] ALL_EMPLOYEE_TABLE_COLUMNS = {
            ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID,
            ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME
    };


    /**
     * Default constructor.
     * @param context
     */
    public EmployeeDataAccess(Context context) {
        dbHelper = new ShiftLogDBOpenHelper(context);
    }

    /**
     * Opens Database.
     */
    public void open() {
        Log.d(LOGTAG, "Database opened.");
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes Database.
     */
    public void close() {
        Log.d(LOGTAG, "Database closed.");
        dbHelper.close();
    }


    /**
     * Adds the employee name and id to the employee table.
     *
     * @param employeeId
     * @param employee
     * @return
     */
    public boolean addEmployeeToTable(int employeeId, String employee) {
        if (!doesEmployeeExist(employeeId)) {
            this.open();
            ContentValues values = new ContentValues();
            values.put(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID, String.valueOf(employeeId));
            values.put(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME, employee);
            database.insert(ShiftLogDBOpenHelper.TABLE_EMPLOYEES, null, values);
            Log.d(LOGTAG, "Employee " + employeeId + " added");
            return true;
        } else {
            Log.d(LOGTAG, "Employee " + employeeId + " Exists already");
        }
        this.close();
        return false;
    }

    /**
     * Checks if the given employee exists in the employee table, by the employee id.
     *
     * @param employeeId
     * @return
     */
    public boolean doesEmployeeExist(int employeeId) {
        this.open();

        Cursor cursor =
                database.rawQuery("SELECT 1 FROM " + ShiftLogDBOpenHelper.TABLE_EMPLOYEES + " WHERE _id= \"" + employeeId + "\" LIMIT 1", null);


        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows");

        if (cursor.getCount() > 0) {
            this.close();
            return true;
        } else {
            this.close();
            return false;
        }

    }

    /**
     * Returns the name of the employee given an employee id, returns null if the employee does not exist.
     *
     * @param employeeId
     * @return
     */
    public String getEmployeeName(int employeeId) {
        this.open();
        Cursor cursor =
                database.query(ShiftLogDBOpenHelper.TABLE_EMPLOYEES,
                        new String[]{ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME},
                        ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID + " = ?",
                        new String[]{Integer.toString(employeeId)}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME));
        }
        this.close();
        return null;
    }


    /**
     * Returns and String Arraylist of employee names and id's within the employee table.
     * @return Arraylist of employee names and id, seperated by a semi colon.
     */
    public ArrayList<String> getEmployeeList() {
        this.open();
        ArrayList<String> employeeList = new ArrayList<>();
        String employeeRow = "";

        Cursor cursor
                = database.rawQuery("Select * from "
                + ShiftLogDBOpenHelper.TABLE_EMPLOYEES
                + ";", null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                employeeRow = "";

                employeeRow += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID));
                employeeRow += " : ";
                employeeRow += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME));

                employeeList.add(employeeRow);
            }
        }
        this.close();
        return employeeList;
    }

    /**
     * Deletes the employee with the provided employee id.
     * @param employeeId
     */
    public void deleteEmployee(String employeeId) {
        this.open();
        database.delete(ShiftLogDBOpenHelper.TABLE_EMPLOYEES, ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID + " = " + employeeId + ";", null);
        this.close();
    }



}