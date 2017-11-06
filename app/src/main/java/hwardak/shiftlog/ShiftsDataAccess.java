package hwardak.shiftlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telecom.StatusHints;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HWardak on 2017-08-24.
 */

public  class ShiftsDataAccess {

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

    public void closeShift(String declaredEndTime, String actualEndTime, double redemptionsAmount,
                           double driveOffs, double finalDrop, double shortOver,
                           double printOutTerminal, double printOutPassport,
                           double printOutDifference, int scratchAdd, int scratchClose,
                           int scratchPassport, int scratchSold, int scratchDifference,
                           int shiftID, int shiftOpen, double hoursWorked) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME, actualEndTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME, declaredEndTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED, 0);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS, redemptionsAmount);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS, driveOffs);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT, finalDrop);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER, shortOver);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT, printOutTerminal);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT, printOutPassport);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE, printOutDifference);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD, scratchAdd);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE, scratchClose);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD, scratchSold);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT, scratchPassport);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE, scratchDifference);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_OPEN, shiftOpen);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED, hoursWorked);
        database.update(ShiftLogDBOpenHelper.TABLE_SHIFTS, values,"_id = " + shiftID, null);
        this.close();

    }


    /**
     *
     * @param employeeName
     * @param userID
     * @param dateStart
     * @param yearStart
     * @param monthStart
     * @param dayOfMonthStart
     * @param dayOfWeek
     * @param declaredStartTime
     * @param actualStartTime
     * @param tillNumber
     * @param startingTillAmount
     * @param scratchStart
     * @param shiftOpen
     */
    public void openNewShift(String employeeName, int userID, String dateStart, int yearStart,
                             int monthStart, int dayOfMonthStart, String dayOfWeek,
                             String declaredStartTime, String actualStartTime, String declaredEndTime,
                             int tillNumber, double startingTillAmount, int scratchStart,
                             int shiftOpen) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID, userID);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME, employeeName);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE, dateStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR, yearStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_MONTH, monthStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DAY_OF_MONTHS, dayOfMonthStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DAY_OF_WEEK, dayOfWeek);

        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME, declaredStartTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_START_TIME, actualStartTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER, tillNumber);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT, startingTillAmount);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START, scratchStart);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_OPEN, shiftOpen);

        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME, declaredEndTime);
        values.put(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME, "00:00AM");

        //These columns are assigned a value once the shift is closed.
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
    public ArrayList<String> getEmployeeOpenShiftData(int userID){
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
        shiftData.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR)))); // 22


        return shiftData;
    }

    //TODO: Change this method name, we only need five columns from here, for a quick look of the shifts.
    /**
     * This method contains a 'dynamic' query.
     * Depending on the values contained with the monthYearEmployee argument, the query will be
     * structured to request the appropriate data with correct syntax.
     * monthYearEmployee should always have 3 String values. If not, it will be caught in the first
     * if block, where the method will return a Cursor with all rows from the shift table.
     * @param monthYearEmployee String array containing numerical value of the month (Jan = 0) and
     *        year, and possibly the employees name.
     * employees name.
     * @return Cursor containing all the rows from the shift table, or on the rows that meet the
     *         requirments of the where clauses.
     */
    public Cursor getShiftsCursor(String... monthYearEmployee){
        this.open();

        String month;
        String employee;
        String year;
        Cursor cursor;

        // If the
        if(monthYearEmployee.length < 2){
            cursor = database.rawQuery("Select * from shifts;", null);
            return cursor;
        } else {

            if (Integer.parseInt(monthYearEmployee[0]) > 0) {
                month = " = " + (monthYearEmployee[0]);
            } else {
                month = " > -1";
            }

            if (Integer.parseInt(monthYearEmployee[1]) > 0) {
                year = " = " + monthYearEmployee[1];
            } else {
                year = " > 0 ";
            }

            if (monthYearEmployee[2] != null) {
                employee = "\'" + monthYearEmployee[2] + "\'";
            } else {
                employee = "\'%\'";
            }

            Log.d("monthYearEmployee", "" + month + year + employee);


            ArrayList<String> shiftRows = new ArrayList<>();
            String shiftData = "";

            String query = "Select * from  "
                    + ShiftLogDBOpenHelper.TABLE_SHIFTS
                    + " Where " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_MONTH + month
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR + year
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME + " LIKE " + employee;

            Log.d("Query", query);


            cursor
                    = database.rawQuery(query, null);
        }
        return cursor;

    }


    public ArrayList<String> getEmployeeList() {

        this.open();
        ArrayList<String> employeeList = new ArrayList<>();
        String employee = "";
        Cursor cursor = database.rawQuery("Select DISTINCT name from shifts;", null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                employee = "" + cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME));
                employeeList.add(employee);
            }
        }
        this.close();
        return employeeList;
    }

    public ArrayList<String> getYearList() {
        this.open();
        ArrayList<String> yearList = new ArrayList<>();
        String year = "";
        Cursor cursor = database.rawQuery("Select DISTINCT year from shifts;", null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                year = "" + cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR));
                yearList.add(year);
            }
        }
        this.close();
        return yearList;

    }


    public Cursor getAllShiftsCursor(){
        this.open();
        Cursor cursor;
        cursor = database.rawQuery("Select * from  " + ShiftLogDBOpenHelper.TABLE_SHIFTS, null);
        return cursor;
    }



    /*
    Prints table to console, for debugging purposes.
     */
    public void printTable(){

        this.open();
        ArrayList<String> shiftRows = new ArrayList<>();
        String shiftData = "";
        Cursor cursor
                = database.rawQuery("Select * from  " + ShiftLogDBOpenHelper.TABLE_SHIFTS, null);

        while(cursor.moveToNext()) {
            shiftData = "";

            shiftData += " SHIFT ID: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_ID));

            shiftData += " EMPLOYEE: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME));

            shiftData += " ID: " ;
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID));

            shiftData += " DATE: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE));

            shiftData += " START: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME));

            shiftData += " END: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME));

            shiftData += " HOURS WORKED: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED));

            shiftData += " TILL NUMBER:";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER));

            shiftData += " STARTING TILL: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT));

            shiftData += " REDEMPTIONS: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS));

            shiftData += " DRIVE OFFS: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS));

            shiftData += " FINAL DROP: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT));

            shiftData += " SHORT OVER: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER));

            shiftData += " P TERMINAL: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT));

            shiftData += " P PASSPORT: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT));

            shiftData += " P DIFFERENCE: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE));

            shiftData += " S START: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START));

            shiftData += " S ADD: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD));

            shiftData += " S CLOSE: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE));

            shiftData += " S SOLD: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD));

            shiftData += " S PASSPORT: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT));

            shiftData += " S DIFFERENCE: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE));

            shiftData += " A START: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_START_TIME));

            shiftData += " A END: ";
            shiftData += cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME));

            shiftRows.add(shiftData);
        }

        Log.d("ShiftTable", " Rows: " + cursor.getCount());




        for(int i = 0; i < shiftRows.size(); i++){
            Log.d("ShiftRow", shiftRows.get(i));

        }





    }

    public double getTotalHours(String... monthYearEmployee) {

        this.open();

        String month;
        String employee;
        String year;
        Cursor cursor;

        // If the
        if(monthYearEmployee.length < 2){
            cursor = database.rawQuery("Select SUM(" + ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED + ") from shifts;", null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {

            if (Integer.parseInt(monthYearEmployee[0]) > 0) {
                month = " = " + (monthYearEmployee[0]);
            } else {
                month = " > 0";
            }

            if (Integer.parseInt(monthYearEmployee[1]) > 0) {
                year = " = " + monthYearEmployee[1];
            } else {
                year = " > 0 ";
            }

            if (monthYearEmployee[2] != null) {
                employee = "\'" + monthYearEmployee[2] + "\'";
            } else {
                employee = "\'%\'";
            }

            Log.d("monthYearEmployee", "" + month + year + employee);


            ArrayList<String> shiftRows = new ArrayList<>();
            String shiftData = "";

            String query = "Select SUM(" + ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED + ") from  "
                    + ShiftLogDBOpenHelper.TABLE_SHIFTS
                    + " Where " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_MONTH + month
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR + year
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME + " LIKE " + employee;

            Log.d("Query", query);


            cursor
                    = database.rawQuery(query, null);
        }


        cursor.moveToFirst();
        return cursor.getDouble(0);
    }

    public int getTotalDistinctDates(String... monthYearEmployee) {
        this.open();

        String month;
        String employee;
        String year;
        Cursor cursor;

        // If the
        if(monthYearEmployee.length < 2){
            cursor = database.rawQuery("Select COUNT(DISTINCT " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED + ") from shifts;", null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {

            if (Integer.parseInt(monthYearEmployee[0]) > 0) {
                month = " = " + (monthYearEmployee[0]);
            } else {
                month = " > 0";
            }

            if (Integer.parseInt(monthYearEmployee[1]) > 0) {
                year = " = " + monthYearEmployee[1];
            } else {
                year = " > 0 ";
            }

            if (monthYearEmployee[2] != null) {
                employee = "\'" + monthYearEmployee[2] + "\'";
            } else {
                employee = "\'%\'";
            }

            Log.d("monthYearEmployee", "" + month + year + employee);


            ArrayList<String> shiftRows = new ArrayList<>();
            String shiftData = "";

            String query = "Select COUNT(DISTINCT " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED + ") from  "
                    + ShiftLogDBOpenHelper.TABLE_SHIFTS
                    + " Where " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_MONTH + month
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_YEAR + year
                    + " AND " + ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME + " LIKE " + employee;

            Log.d("Query", query);


            cursor
                    = database.rawQuery(query, null);
        }


        cursor.moveToFirst();
        return cursor.getInt(0);

    }

    public String getMostRecentDate() {
        String mostRecentDate;

        this.open();
        Cursor cursor;
        cursor = database.rawQuery("SELECT date, _id \n" +
                "FROM shifts \n" +
                "WHERE _id=(\n" +
                "    SELECT max(_id) FROM shifts\n" +
                "    )", null);

        cursor.moveToNext();
        mostRecentDate = cursor.getString(cursor.getColumnIndex(ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE));

        return mostRecentDate;
    }

    public Cursor getShiftsCursorByDate(String date) {
        this.open();

        return  database.rawQuery("SELECT * FROM shifts WHERE date = \"" + date +"\"", null);




    }
}

























