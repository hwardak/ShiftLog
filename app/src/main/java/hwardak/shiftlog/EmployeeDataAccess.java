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

public class EmployeeDataAccess {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String LOGTAG = "DATABASE E: ";

    public static final String[] ALL_EMPLOYEE_TABLE_COLUMNS = {
            ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID,
            ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME
    };


    public EmployeeDataAccess(Context context) {
        dbHelper = new ShiftLogDBOpenHelper(context);
    }

    public void open() {
        Log.d(LOGTAG, "Database opened.");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(LOGTAG, "Database closed.");
        dbHelper.close();
    }

    public boolean addEmployeeToTable(int i, String s) {
        if (!doesEmployeeExist(i)) {
            this.open();
            ContentValues values = new ContentValues();
            values.put(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID, String.valueOf(i));
            values.put(ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_NAME, s);
            database.insert(ShiftLogDBOpenHelper.TABLE_EMPLOYEES, null, values);
            Log.d(LOGTAG, "Employee " + i + " added");
            return true;
        } else {
            Log.d(LOGTAG, "Employee " + i + " Exists already");
        }
        this.close();
        return false;
    }

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

    public void deleteEmployee(String substring) {
        this.open();
        database.delete(ShiftLogDBOpenHelper.TABLE_EMPLOYEES, ShiftLogDBOpenHelper.EMPLOYEES_COLUMN_EMPLOYEE_ID + " = " + substring + ";", null);
        this.close();
    }



//    public boolean isEmployeeSignedIn() {
//
//        return employeeSignedIn;
//    }
}