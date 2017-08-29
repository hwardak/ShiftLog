package hwardak.shiftlog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HWardak on 2017-08-24.
 */

public class ShiftsDataAccess {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String LOGTAG = "DATABASE S: ";


    public ShiftsDataAccess(Context context){
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

    public boolean doesEmployeeHaveOpenShift(int userID){
        this.open();

        Cursor cursor
                = database.rawQuery("Select * from  "
                        + ShiftLogDBOpenHelper.TABLE_SHIFTS
                        + " where " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID
                        + " = ? and " + ShiftLogDBOpenHelper.SHIFT_COLUMN_SHIFT_OPEN +  " = 1 ;",
                new String[]{Integer.toString(userID)});

        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if employee has any open shift. Employees can only have one open shift at a time.
     * @param userID
     * @return ArrayList of Strings with the shift info.
     */
    public ArrayList<String> getEmployeesOpenShiftData(int userID){
        this.open();
        ArrayList<String> shiftData = new ArrayList<>();

        Cursor cursor
                = database.rawQuery("Select * from  "
                + ShiftLogDBOpenHelper.TABLE_SHIFTS
                + " where " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID
                + " = ? and " + ShiftLogDBOpenHelper.SHIFT_COLUMN_SHIFT_OPEN +  " = 1 ;",
                new String[]{Integer.toString(userID)});
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME)));
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME)));
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE)));
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER)));
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT)));
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_LOTTO_ID)));

    return shiftData;
    }

}
