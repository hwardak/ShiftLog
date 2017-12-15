package hwardak.shiftlog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HWardak on 2017-08-06.
 */

public class ShiftLogDBOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "DATABASE: ";
    private static final String DATABASE_NAME ="shiftLog.db";
    private static int DATABASE_VERSION = 29;

    private static final String TABLE_SETTINGS = "settings";
    private static final String SETTINGS_COLUMN_USER_EMAIL = "userEmail";
    private static final String SETTINGS_COLUMN_EMAIL_PW = "emailPW";
    private static final String SETTINGS_COLUMN_RECIPIENT_EMAIL = "recipientEmail";
    private static final String SETTINGS_COLUMN_STORE_NUMBER = "storeNumber";

    private static final String SETTINGS_TABLE_CREATE
            = "CREATE TABLE " + TABLE_SETTINGS + " ("
            + SETTINGS_COLUMN_USER_EMAIL + " TEXT, "
            + SETTINGS_COLUMN_EMAIL_PW + " TEXT, "
            + SETTINGS_COLUMN_RECIPIENT_EMAIL + " TEXT, "
            + SETTINGS_COLUMN_STORE_NUMBER + " INTEGER"
            + ");";


    static final String TABLE_EMPLOYEES = "employees";
    static final String EMPLOYEES_COLUMN_EMPLOYEE_ID = "_id";
    static final String EMPLOYEES_COLUMN_EMPLOYEE_NAME = "name";


    private static final String EMPLOYEE_TABLE_CREATE
            = "CREATE TABLE " + TABLE_EMPLOYEES + " ("
            + EMPLOYEES_COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY, "
            + EMPLOYEES_COLUMN_EMPLOYEE_NAME + " TEXT );";

    public ShiftLogDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //TODO: Date should include the entire date according to SimpleDateFormat.
    //TODO" The date should also be stored in the seperate columns such as Month,DayOfWeek,year, and such...
    static final String TABLE_SHIFTS = "shifts";
    static final String SHIFTS_COLUMN_SHIFT_ID = "_id";
    static final String SHIFTS_COLUMN_EMPLOYEE_ID = "employeeId";
    static final String SHIFTS_COLUMN_EMPLOYEE_NAME = "name";
    static final String SHIFTS_COLUMN_DATE = "date";
    static final String SHIFTS_COLUMN_YEAR = "year";
    static final String SHIFTS_COLUMN_MONTH = "month";
    static final String SHIFTS_COLUMN_DAY_OF_MONTHS = "dayOfMonth";
    static final String SHIFTS_COLUMN_DAY_OF_WEEK = "dayOfWeek";
    static final String SHIFTS_COLUMN_DECLARED_START_TIME = "declaredStartTime";
    static final String SHIFTS_COLUMN_ACTUAL_START_TIME = "actualStartTime";
    static final String SHIFTS_COLUMN_DECLARED_END_TIME = "declaredEndTime";
    static final String SHIFTS_COLUMN_ACTUAL_END_TIME = "actualEndTime";
    static final String SHIFTS_COLUMN_HOURS_WORKED = "hoursWorked";
    static final String SHIFTS_COLUMN_TILL_NUMBER = "tillNumber";
    static final String SHIFTS_COLUMN_STARTING_TILL_AMOUNT = "startingTillAmount";
    static final String SHIFTS_COLUMN_REDEMPTIONS = "redemptions";
    static final String SHIFTS_COLUMN_DRIVE_OFFS = "driveOffs";
    static final String SHIFTS_COLUMN_FINAL_DROP_AMOUNT = "finalDropAmount";
    static final String SHIFTS_COLUMN_SHORT_OVER = "shortOver";
    static final String SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT = "printOutTermninalCount";
    static final String SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT = "printOutPassportCount";
    static final String SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE = "printOutDifferenceCount";
    static final String SHIFTS_COLUMN_SCRATCH_START = "scratchStartCount";
    static final String SHIFTS_COLUMN_SCRATCH_ADD = "scratchAddCount";
    static final String SHIFTS_COLUMN_SCRATCH_CLOSE = "scratchCloseCount";
    static final String SHIFTS_COLUMN_SCRATCH_SOLD = "scratchSoldCount";
    static final String SHIFTS_COLUMN_SCRATCH_PASSPORT = "scratchPassportCount";
    static final String SHIFTS_COLUMN_SCRATCH_DIFFERENCE = "scratchDifferenceCount";
    static final String SHIFTS_COLUMN_SHIFT_OPEN = "shiftOpen";

    //TODO: perhaps the money values should be stored as a REAL instead of TEXT.

    static final String SHIFTS_TABLE_CREATE
            = "CREATE TABLE " + TABLE_SHIFTS + " ("
            + SHIFTS_COLUMN_SHIFT_ID + " INTEGER PRIMARY KEY, "
            + SHIFTS_COLUMN_EMPLOYEE_ID + " INTEGER, "
            + SHIFTS_COLUMN_EMPLOYEE_NAME + " TEXT, "
            + SHIFTS_COLUMN_DATE + " TEXT, "
            + SHIFTS_COLUMN_DECLARED_START_TIME + " TEXT, "
            + SHIFTS_COLUMN_YEAR + " INTEGER, "
            + SHIFTS_COLUMN_MONTH + " INTEGER, "
            + SHIFTS_COLUMN_DAY_OF_MONTHS + " INTEGER, "
            + SHIFTS_COLUMN_DAY_OF_WEEK + " TEXT, "
            + SHIFTS_COLUMN_ACTUAL_START_TIME + " TEXT, "
            + SHIFTS_COLUMN_DECLARED_END_TIME + " TEXT, "
            + SHIFTS_COLUMN_ACTUAL_END_TIME + " TEXT, "
            + SHIFTS_COLUMN_HOURS_WORKED + " INTEGER, "
            + SHIFTS_COLUMN_TILL_NUMBER + " TEXT, "
            + SHIFTS_COLUMN_STARTING_TILL_AMOUNT + " TEXT, "
            + SHIFTS_COLUMN_REDEMPTIONS + " REAL, "
            + SHIFTS_COLUMN_DRIVE_OFFS + " REAL, "
            + SHIFTS_COLUMN_FINAL_DROP_AMOUNT + " REAL, "
            + SHIFTS_COLUMN_SHORT_OVER + " REAL, "
            + SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT + " REAL, "
            + SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT + " REAL, "
            + SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE + " REAL, "
            + SHIFTS_COLUMN_SCRATCH_START + " INTEGER, "
            + SHIFTS_COLUMN_SCRATCH_ADD + " INTEGER, "
            + SHIFTS_COLUMN_SCRATCH_CLOSE + " INTEGER, "
            + SHIFTS_COLUMN_SCRATCH_SOLD + " INTEGER, "
            + SHIFTS_COLUMN_SCRATCH_PASSPORT + " INTEGER, "
            + SHIFTS_COLUMN_SCRATCH_DIFFERENCE + " INTEGER, "
            + SHIFTS_COLUMN_SHIFT_OPEN + " INTEGER "
            + ");";




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_TABLE_CREATE);
        Log.d(LOGTAG, "Settings table created.");

        db.execSQL(EMPLOYEE_TABLE_CREATE);
        Log.d(LOGTAG, "Employees table created.");

        db.execSQL(SHIFTS_TABLE_CREATE);
        Log.d(LOGTAG, "Shifts table created.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIFTS);


        this.onCreate(db);
    }
}
