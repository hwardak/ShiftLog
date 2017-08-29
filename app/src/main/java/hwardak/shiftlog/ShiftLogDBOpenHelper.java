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
    private static int DATABASE_VERSION = 19
            ;

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


    static final String TABLE_SHIFTS = "shifts";
    static final String SHIFTS_COLUMN_SHIFT_ID = "_id";
    static final String SHIFTS_COLUMN_EMPLOYEE_NAME = "name";
    static final String SHIFTS_COLUMN_EMPLOYEE_ID = "employeeId";
    static final String SHIFTS_COLUMN_DATE = "date";
    static final String SHIFTS_COLUMN_DECLARED_START_TIME = "declaredStartTime";
    static final String SHIFTS_COLUMN_ACTUAL_START_TIME = "actualStartTime";
    static final String SHIFTS_COLUMN_DECLARED_END_TIME = "declaredEndTime";
    static final String SHIFTS_COLUMN_ACTUAL_END_TIME = "actualEndTime";
    static final String SHIFTS_COLUMN_HOURS_WORKED = "hoursWorked";
    static final String SHIFTS_COLUMN_TILL_NUMBER = "tillNumber";
    static final String SHIFTS_COLUMN_STARTING_TILL_AMOUNT = "startingTillAmount";
    static final String SHIFTS_COLUMN_FINAL_DROP_AMOUNT = "finalDropAmount";
    static final String SHIFTS_COLUMN_REDEMPTIONS = "redemptions";
    static final String SHIFTS_COLUMN_LOTTO_ID = "lottoId";
    static final String SHIFT_COLUMN_SHIFT_OPEN = "shiftOpen";

    //TODO: perhaps the money values should be stored as a REAL instead of TEXT.

    static final String SHIFTS_TABLE_CREATE
            = "CREATE TABLE " + TABLE_SHIFTS + " ("
            + SHIFTS_COLUMN_SHIFT_ID + " INTEGER PRIMARY KEY, "
            + SHIFTS_COLUMN_EMPLOYEE_NAME + " TEXT, "
            + SHIFTS_COLUMN_EMPLOYEE_ID + " INTEGER, "
            + SHIFTS_COLUMN_DATE + " TEXT, "
            + SHIFTS_COLUMN_DECLARED_START_TIME + " TEXT, "
            + SHIFTS_COLUMN_ACTUAL_START_TIME + " TEXT, "
            + SHIFTS_COLUMN_DECLARED_END_TIME + " TEXT, "
            + SHIFTS_COLUMN_ACTUAL_END_TIME + " TEXT, "
            + SHIFTS_COLUMN_HOURS_WORKED + " INTEGER, "
            + SHIFTS_COLUMN_TILL_NUMBER + " TEXT, "
            + SHIFTS_COLUMN_STARTING_TILL_AMOUNT + " TEXT, "
            + SHIFTS_COLUMN_FINAL_DROP_AMOUNT + " TEXT, "
            + SHIFTS_COLUMN_REDEMPTIONS + " TEXT, "
            + SHIFTS_COLUMN_LOTTO_ID + " INTEGER, "
            + SHIFT_COLUMN_SHIFT_OPEN + " INTEGER "
            + ");";


    static final String TABLE_LOTTO = "lotto";
    static final String LOTTO_ID = "_id";
    static final String LOTTO_COLUMN_SHIFT_ID = "shiftID";

    static final String LOTTO_COLUMN_START_1 = "start1";
    static final String LOTTO_COLUMN_START_2 = "start2";
    static final String LOTTO_COLUMN_START_3 = "start3";
    static final String LOTTO_COLUMN_START_4 = "start4";
    static final String LOTTO_COLUMN_START_5 = "start5";
    static final String LOTTO_COLUMN_START_10 = "start10";
    static final String LOTTO_COLUMN_START_20 = "start20";
    static final String LOTTO_COLUMN_START_30 = "start30";
    static final String LOTTO_COLUMN_START_OTHER = "startOther";
    static final String LOTTO_COLUMN_START_TOTAL = "startTotal";

    static final String LOTTO_COLUMN_END_1 = "end1";
    static final String LOTTO_COLUMN_END_2 = "end2";
    static final String LOTTO_COLUMN_END_3 = "end3";
    static final String LOTTO_COLUMN_END_4 = "end4";
    static final String LOTTO_COLUMN_END_5 = "end5";
    static final String LOTTO_COLUMN_END_10 = "end10";
    static final String LOTTO_COLUMN_END_20 = "end20";
    static final String LOTTO_COLUMN_END_30 = "end30";
    static final String LOTTO_COLUMN_END_OTHER = "endOther";
    static final String LOTTO_COLUMN_END_TOTAL = "endTotal";

    static final String LOTTO_COLUMN_ADDED_1 = "added1";
    static final String LOTTO_COLUMN_ADDED_2 = "added2";
    static final String LOTTO_COLUMN_ADDED_3 = "added3";
    static final String LOTTO_COLUMN_ADDED_4 = "added4";
    static final String LOTTO_COLUMN_ADDED_5 = "added5";
    static final String LOTTO_COLUMN_ADDED_10 = "added10";
    static final String LOTTO_COLUMN_ADDED_20 = "added20";
    static final String LOTTO_COLUMN_ADDED_30 = "added30";
    static final String LOTTO_COLUMN_ADDED_OTHER = "addedOther";
    static final String LOTTO_COLUMN_ADDED_TOTAL = "addedTotal";

    static final String LOTTO_COLUMN_SOLD_1 = "sold1";
    static final String LOTTO_COLUMN_SOLD_2 = "sold2";
    static final String LOTTO_COLUMN_SOLD_3 = "sold3";
    static final String LOTTO_COLUMN_SOLD_4 = "sold4";
    static final String LOTTO_COLUMN_SOLD_5 = "sold5";
    static final String LOTTO_COLUMN_SOLD_10 = "sold10";
    static final String LOTTO_COLUMN_SOLD_20 = "sold20";
    static final String LOTTO_COLUMN_SOLD_30 = "sold30";
    static final String LOTTO_COLUMN_SOLD_OTHER = "soldOther";
    static final String LOTTO_COLUMN_SOLD_TOTAL = "soldTotal";

    static final String LOTTO_COLUMN_PASSPORT_1 = "passport1";
    static final String LOTTO_COLUMN_PASSPORT_2 = "passport2";
    static final String LOTTO_COLUMN_PASSPORT_3 = "passport3";
    static final String LOTTO_COLUMN_PASSPORT_4 = "passport4";
    static final String LOTTO_COLUMN_PASSPORT_5 = "passport5";
    static final String LOTTO_COLUMN_PASSPORT_10 = "passport10";
    static final String LOTTO_COLUMN_PASSPORT_20 = "passport20";
    static final String LOTTO_COLUMN_PASSPORT_30 = "passport30";
    static final String LOTTO_COLUMN_PASSPORT_OTHER = "passportOther";
    static final String LOTTO_COLUMN_PASSPORT_TOTAL = "passportTotal";

    static final String LOTTO_COLUMN_FINAL_DIFFERENCE = "finalDifference";



    static final String LOTTO_TABLE_CREATE
            = "CREATE TABLE " + TABLE_LOTTO + " ("
            + LOTTO_ID + " INTEGER PRIMARY KEY, "
            + LOTTO_COLUMN_SHIFT_ID + " INTEGER, "
            + LOTTO_COLUMN_START_1 + " INTEGER, "
            + LOTTO_COLUMN_START_2 + " INTEGER, "
            + LOTTO_COLUMN_START_3 + " INTEGER, "
            + LOTTO_COLUMN_START_4 + " INTEGER, "
            + LOTTO_COLUMN_START_5 + " INTEGER, "
            + LOTTO_COLUMN_START_10 + " INTEGER, "
            + LOTTO_COLUMN_START_20 + " INTEGER, "
            + LOTTO_COLUMN_START_30 + " INTEGER, "
            + LOTTO_COLUMN_START_OTHER + " INTEGER, "
            + LOTTO_COLUMN_START_TOTAL + " INTEGER, "

            + LOTTO_COLUMN_END_1 + " INTEGER, "
            + LOTTO_COLUMN_END_2 + " INTEGER, "
            + LOTTO_COLUMN_END_3 + " INTEGER, "
            + LOTTO_COLUMN_END_4 + " INTEGER, "
            + LOTTO_COLUMN_END_5 + " INTEGER, "
            + LOTTO_COLUMN_END_10 + " INTEGER, "
            + LOTTO_COLUMN_END_20 + " INTEGER, "
            + LOTTO_COLUMN_END_30 + " INTEGER, "
            + LOTTO_COLUMN_END_OTHER + " INTEGER, "
            + LOTTO_COLUMN_END_TOTAL + " INTEGER, "

            + LOTTO_COLUMN_ADDED_1 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_2 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_3 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_4 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_5 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_10 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_20 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_30 + " INTEGER, "
            + LOTTO_COLUMN_ADDED_OTHER + " INTEGER, "
            + LOTTO_COLUMN_ADDED_TOTAL + " INTEGER, "

            + LOTTO_COLUMN_SOLD_1 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_2 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_3 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_4 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_5 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_10 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_20 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_30 + " INTEGER, "
            + LOTTO_COLUMN_SOLD_OTHER + " INTEGER, "
            + LOTTO_COLUMN_SOLD_TOTAL + " INTEGER, "

            + LOTTO_COLUMN_PASSPORT_1 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_2 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_3 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_4 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_5 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_10 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_20 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_30 + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_OTHER + " INTEGER, "
            + LOTTO_COLUMN_PASSPORT_TOTAL + " INTEGER, "

            + LOTTO_COLUMN_FINAL_DIFFERENCE + " INTEGER, "

            + " FOREIGN KEY " + "(" + LOTTO_COLUMN_SHIFT_ID + ") REFERENCES " + TABLE_SHIFTS + "(" + SHIFTS_COLUMN_SHIFT_ID + ") "

            + " );";










    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_TABLE_CREATE);
        Log.d(LOGTAG, "Settings table created.");

        db.execSQL(EMPLOYEE_TABLE_CREATE);
        Log.d(LOGTAG, "Employees table created.");

        db.execSQL(SHIFTS_TABLE_CREATE);
        Log.d(LOGTAG, "Shifts table created.");

        db.execSQL((LOTTO_TABLE_CREATE));
        Log.d(LOGTAG, "Lotto table created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIFTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOTTO);

        this.onCreate(db);
    }
}
