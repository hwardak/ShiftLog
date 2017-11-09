package hwardak.shiftlog;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.input;

public class ShiftLogRecord extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ShiftsDataAccess shiftsDataAccess = new ShiftsDataAccess(this);

    Spinner displayBySpinner;
    Spinner employeeSpinner;
    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;

    TextView totalHoursEditText;
    TextView overLapHoursTextView;
    TextView overLapHoursPerDayTextView;
    TextView detailedDayDateTextView;

    int monthNumber;
    String monthString;

    int year;

    ArrayList<String> shiftList;

    int employeeId;
    String employee;

    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_log_record);

        totalHoursEditText = (TextView) findViewById(R.id.totalHoursTextView);
        overLapHoursTextView = (TextView) findViewById(R.id.overLapHoursTextView);
        overLapHoursPerDayTextView = (TextView) findViewById(R.id.overLapHoursPerDayTextView);
        detailedDayDateTextView = (TextView) findViewById(R.id.detailedDayDateTextView);

        loadShifts(shiftsDataAccess.getShiftsCursor());

        loadHoursDetails(shiftsDataAccess.getTotalHours(), shiftsDataAccess.getTotalDistinctDates());


        instantiateDisplayBySpinner();
        instantiateEmployeeSpinner();
        instantiateDaySpinner();
        instantiateMonthSpinner();
        instantiateYearSpinner();


    }




    private void instantiateDisplayBySpinner() {
        displayBySpinner = (Spinner) findViewById(R.id.displayBySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.displayByArray, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        displayBySpinner.setAdapter(adapter);

        displayBySpinner.setOnItemSelectedListener(this);


    }

    private void instantiateEmployeeSpinner() {
        employeeSpinner = (Spinner) findViewById(R.id.employeeSpinner);

        ArrayList<String> employeeList = shiftsDataAccess.getEmployeeList();
        employeeList.add(0, "All Employees");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeeList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(adapter);

        employeeSpinner.setOnItemSelectedListener(this);

    }

    private void instantiateYearSpinner() {
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        ArrayList<String> yearList = shiftsDataAccess.getYearList();
        yearList.add(0, "All Years");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, yearList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

        yearSpinner.setOnItemSelectedListener(this);

    }

    private void instantiateDaySpinner() {
        daySpinner = (Spinner) findViewById(R.id.daySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        daySpinner.setOnItemSelectedListener(this);

    }

    private void instantiateMonthSpinner() {
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        monthSpinner.setOnItemSelectedListener(this);

    }


    /**
     * Whenever any of the Spinners in this activity are interacted with, this method is invoked.
     * Depending on which Spinner (parent) has invoked it, and the selection(s) made, the respective
     * set of selections from each spinner are passed to a method in the dataAccess layer. Which
     * will return a new cursor, whihc is passed to the loadShifts method, which will refresh the
     * ListView with the data in the new cursor.
     * Depending on the above Spinner selections, the respective amount of hours is also pulled from
     * the DB and passed to the loadHoursDetails method.
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        counter++;
        if (counter >= 4) {

            if (parent == monthSpinner) {
                if (position != 0) {
                    //The position number is one higher than month's number value, so we negate one to
                    //level them.
                    monthNumber = position - 1;
                } else {
                    monthNumber = -1;
                }
                monthString = parent.getItemAtPosition(position).toString();
            }

            if (parent == yearSpinner) {
                if (position != 0) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                } else {
                    year = 0;
                }
            }

            if (parent == employeeSpinner) {
                if (position != 0) {
                    employee = parent.getItemAtPosition(position).toString();
                } else {
                    employee = null;
                }
            }

            String[] monthYearEmployee = {String.valueOf(monthNumber), String.valueOf(year), employee};


            if (parent == displayBySpinner) {
                if (position == 0) { // All shifts
                    detailedDayDateTextView.setVisibility(View.GONE);
                    loadShifts(shiftsDataAccess.getShiftsCursor(monthYearEmployee));
                    loadHoursDetails(shiftsDataAccess.getTotalHours(monthYearEmployee), shiftsDataAccess.getTotalDistinctDates(monthYearEmployee));

                } else if (position == 1) { //Detailed day
                    detailedDayDateTextView.setVisibility(View.VISIBLE);
                    loadDetailedDay(null);

                } else if (position == 2) { //Pay period
                    loadPayPeriod();
                }
            }

            if (displayBySpinner.getSelectedItemPosition() == 0) {
                loadShifts(shiftsDataAccess.getShiftsCursor(String.valueOf(monthNumber), String.valueOf(year), employee));
                loadHoursDetails(shiftsDataAccess.getTotalHours(String.valueOf(monthNumber), String.valueOf(year), employee), shiftsDataAccess.getTotalDistinctDates(String.valueOf(monthNumber), String.valueOf(year), employee));
            } else if (displayBySpinner.getSelectedItemPosition() == 1) {
                //TODO: load detailed day textview date.
            }
        }
    }

    private void loadPayPeriod() {


    }


    /**
     * This method instantiates,
     * a String[] of Columns name who's contents we would like, and an Array of ints, each
     * representing the resource id of the view within the row layout. There must be an equal count
     * of resource ID's and Column names.
     * The resource id, column name arrays, along with the resource id of the row layout we
     * want to use are passed the SimpleListAdapter constructor
     *
     * @param cursor
     */
    private void loadShifts(Cursor cursor) {

        //All shifts are retrieved if the cursor is null. Like when this activity is first created.
        if (cursor == null) {
            cursor = shiftsDataAccess.getShiftsCursor();
        }

        //Array of columns that are needed for our row layout.
        String[] columns = {
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_ID,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_START_TIME,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACT
        };


        //Int array of resource ID's, one for each column in the columns String array.
        int[] resourceIds = {
                R.id.listviewRowEmployeeName,
                R.id.listviewRowDate,
                R.id.listviewRowStartTime,
                R.id.listviewRowEndTime,
                R.id.listviewRowHoursWorked

        };

        //Resource ID of the Listview we want to populate.
        ListView listView = (ListView) findViewById(R.id.shiftRecordListView);


        //The array of resource ID's, column name's, and layout for the listview row are passed to
        // the below constructor to be assemebled.
        ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.shift_listview_row,
                cursor, columns, resourceIds, 0);

        listView.setAdapter(listAdapter);

        listView.invalidateViews();

    }


    private void loadDetailedDay(String date) {
        Cursor cursor;

        if(date == null) {
            date = shiftsDataAccess.getMostRecentDate();
        }

        date = date.trim();

        if(date.length() == 15) {
            cursor = shiftsDataAccess.getShiftsCursorByDate(date.substring(0,10), date.substring(11));
            detailedDayDateTextView.setText(date.substring(0, 10) + " " + date.substring(10));
        }  else {
            cursor = shiftsDataAccess.getShiftsCursorByDate(date.substring(0, 10), "0");
            detailedDayDateTextView.setText(date.substring(0, 10) + " " + date.substring(10));

        }

        //Array of columns that are needed for our row layout.
        String[] columns = {
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_NAME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DATE,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_START_TIME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DECLARED_END_TIME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_HOURS_WORKED,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHIFT_ID,
//                ShiftLogDBOpenHelper.SHIFTS_COLUMN_EMPLOYEE_ID,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_TILL_NUMBER,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_STARTING_TILL_AMOUNT,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_REDEMPTIONS,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_DRIVE_OFFS,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_FINAL_DROP_AMOUNT,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SHORT_OVER,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_TERMINAL_COUNT,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_PASSPORT_COUNT,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_PRINT_OUT_DIFFERENCE,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_START,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_ADD,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_CLOSE,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_SOLD,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_PASSPORT,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_SCRATCH_DIFFERENCE,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_START_TIME,
                ShiftLogDBOpenHelper.SHIFTS_COLUMN_ACTUAL_END_TIME};


        int[] resourceIds = {
                R.id.detailedDayListViewRowName,
                R.id.detailedDayListViewRowDate,
                R.id.detailedDayListViewRowStartTime,
                R.id.detailedDayListViewRowEndTime,
                R.id.detailedDayListViewRowHoursWorked,
                R.id.detailedDayListViewRowTill,
                R.id.detailedDayListViewStartingTill,
                R.id.detailedDayListViewRedemptions,
                R.id.detailedDayListViewDriveOffs,
                R.id.detailedDayListViewFinalDrop,
                R.id.detailedDayListViewShortOver,
                R.id.detailedDayListViewPrintOutTerminal,
                R.id.detailedDayListViewPrintOutPassport,
                R.id.detailedDayListViewPrintOutDifference,
                R.id.detailedDayListViewScratchStart,
                R.id.detailedDayListViewScratchAdd,
                R.id.detailedDayListViewScratchClose,
                R.id.detailedDayListViewScratchSold,
                R.id.detailedDayListViewScratchPassport,
                R.id.detailedDayListViewScratchDifference


        };


        ListView listView = (ListView) findViewById(R.id.shiftRecordListView);

        ListAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.detailed_day_listview_row,
                cursor, columns, resourceIds, 0);

        listView.setAdapter(listAdapter);

        listView.invalidateViews();

    }




    /**
     * This method receives the total hours and days for a given query of shifts. The data is then
     * presented to the user through its respective EditText.
     * Total hours, overlap hours, and overlap hours per day are set in the EditTexts.
     *
     * Since different stores have different hours of operations, the variable numberOfHoursOpen,
     * should be updated, through a settings activity.
     *
     * @param totalHours
     * @param totalDays
     */
    private void loadHoursDetails(double totalHours, int totalDays) {
        int numberOfHoursOpen = 19;

        totalHoursEditText.setText(String.valueOf(Math.floor(totalHours*4)/4));

        overLapHoursPerDayTextView.setText(String.valueOf(Math.floor(totalHours/totalDays*4)/4));

        double overLapHours = totalHours - (totalDays * numberOfHoursOpen);
        overLapHours = Math.floor((overLapHours*4)/4);

        overLapHoursTextView.setText(String.valueOf(overLapHours));

    }


    @Override
    public void onNothingSelected (AdapterView < ? > parent){

    }


    public void onPreviousButtonClick(View view) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyyy");
        Date myDate = simpleDateFormat.parse(detailedDayDateTextView.getText().toString());
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(myDate);
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate = cal1.getTime();

        Log.d("previous date ", String.valueOf(previousDate).substring(0,10) + " " + String.valueOf(previousDate).substring(24));

        loadDetailedDay(String.valueOf(previousDate).substring(0,10) + " " + String.valueOf(previousDate).substring(24));

    }

    public void onNextButtonClick(View view) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyyy");
        Date myDate = simpleDateFormat.parse(detailedDayDateTextView.getText().toString());
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(myDate);
        cal1.add(Calendar.DAY_OF_YEAR, +1);
        Date previousDate = cal1.getTime();

        Log.d("previous date ", String.valueOf(previousDate).substring(0,10) + " " + String.valueOf(previousDate).substring(24));

        loadDetailedDay(String.valueOf(previousDate).substring(0,10) + " " + String.valueOf(previousDate).substring(24));
    }
}
