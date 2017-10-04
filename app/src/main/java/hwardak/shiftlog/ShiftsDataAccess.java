package hwardak.shiftlog;

import android.content.ContentValues;
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
                        + " = ? and " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_OPEN +  " = 1 ;",
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
                        + " = ? and " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_OPEN +  " = 1 ;",
                new String[]{Integer.toString(userID)});
        cursor.moveToNext();
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME))); //0
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME))); //1
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE))); //2
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER))); //3
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT))); //4
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_ID))); //5th

        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME))); //6
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME))); //7

        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED))); //8
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS))); // 9
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS))); // 10
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT))); //11
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER))); // 12
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT))); //13
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT))); //14
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE))); //15
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START))); // 16
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD))); // 17
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE))); //18
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD))); //19
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT)));  //20
        shiftData.add(cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE))); //21









        return shiftData;
    }

    public void openNewShift(String employeeName, int userID, String dateStart,
                             String declaredStartTime, String actualStartTime,
                             int tillNumber, double startingTillAmount, int shiftOpen,
                             int scratchStart) {

        this.open();
        ContentValues values = new ContentValues();
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID, userID);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME, employeeName);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE, dateStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME, declaredStartTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_START_TIME, actualStartTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER, tillNumber);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT, startingTillAmount);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START, scratchStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_OPEN, 1);

        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME, "00:00AM");
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME, "00:00AM");

        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE, 0);

        database.insert(ShiftLogDBOpenHelper.TABLE_SHIFTS, null, values);
        this.close();
    }

    public void updateShiftForm(int shiftID, String declaredEndTime, double driveOffs,
                                double finalDrop, double shortOver, double printOutTerminal,
                                double printOutPassport, double printOutDifference, int scratchAdd,
                                int scratchClose, int scratchPassport, int scratchSold,
                                int scratchDifference) {

        this.open();
        ContentValues values = new ContentValues();
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME, declaredEndTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS, driveOffs);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT, finalDrop);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER, shortOver);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT, printOutTerminal);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT, printOutPassport);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE, printOutDifference);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD, scratchAdd);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE, scratchClose);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT, scratchPassport);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD, scratchSold);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE, scratchDifference);
        database.update(ShiftLogDBOpenHelper.TABLE_SHIFTS, values,"_id = " + shiftID, null);
        this.close();


    }
}
























