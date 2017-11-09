package hwardak.shiftlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShiftFormActivity extends AppCompatActivity {

    int userID;
    int shiftID;

    EmployeeDataAccess employeeDataAccess;
    ShiftsDataAccess shiftsDataAccess;

    Toaster toaster;

    LinearLayout onTillShiftFieldsLinearLayout;

    TextView infoBannerTextView;

    EditText employeeNameEditText;
    EditText dateEditText;

    EditText declaredStartTimeHourEditText;
    EditText declaredStartTimeMinuteEditText;
    ToggleButton startTimeAmPmToggleButton;

    EditText declaredEndTimeHourEditText;
    EditText declaredEndTimeMinuteEditText;
    ToggleButton endTimeAmPmToggleButton;

    RadioGroup tillNumberRadioGroup;
    RadioButton tillOneRadioButton;
    RadioButton tillTwoRadioButton;
    RadioButton offTillRadioButton;
    TextView tillNumberRadioGroupTextView;


    Button openShiftButton;
    Button updateShiftButton;
    Button closeShiftButton;

    EditText startingTillEditText;
    EditText redemptionsEditText;
    EditText driveOffsEditText;
    EditText finalDropEditText;
    EditText tillShortOverEditText;

    EditText printOutTerminalEditText;
    EditText printOutPassportEditText;
    EditText printOutDifferenceEditText;

    EditText scratchStartEditText;
    EditText scratchAddEditText;
    EditText scratchCloseEditText;
    EditText scratchSoldEditText;
    EditText scratchPassportEditText;
    EditText scratchDifferenceEditText;


    boolean employeeHasOpenShift;

    String employeeName;
    String dateStart;
    String dateEnd;
    int yearStart;
    int yearEnd;
    int monthStart;
    int monthEnd;

    double hoursWorked;

    int dayOfMonthStart;
    int dayOfMonthEnd;
    String dayOfWeek;

    String declaredStartTime;
    String actualStartTime;
    String declaredEndTime;
    String actualEndTime;

    int tillNumber;

    double startingTillAmount;
    double finalDropAmount;
    double redemptionsAmount;
    double driveOffs;
    double shortOver;
    double finalDrop;

    double printOutTerminal;
    double printOutPassport;
    double printOutDifference;

    int scratchStart;
    int scratchAdd;
    int scratchClose;
    int scratchPassport;
    int scratchSold;
    int scratchDifference;


    Calendar calendar;

    DecimalFormat df = new DecimalFormat("#.00");


    /**
     * - Views and variables instantiated.
     * - Checks if employee has an open shift.
     * - If employee is not signed in, time and date are collected and a new form is prepared for them.
     * - If employee is signed in, their singed in form is reloaded, and ready to modify or close.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_form);

        this.instantiateVariables();

        //If the employee has previsously signed in, but has not signed out yet, their unclosed
        //shift data is reloaded into a form.
        if (employeeHasOpenShift) {
            toaster.toastUp(getApplicationContext(), "Shift in progress...");
            ArrayList<String> shiftData = shiftsDataAccess.getEmployeeOpenShiftData(userID);
            this.loadExistingForm(shiftData);
            enableClosingViews(true);

        } else {
            toaster.toastUp(getApplicationContext(), "Starting new shift...");

            this.getStartDate();
            this.preloadStartTime();
            this.loadNewForm(userID);
            enableClosingViews(false);


        }

//        try {
//            calculateHoursWorked();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    private void enableClosingViews(boolean b) {

        startingTillEditText.setEnabled(!b);
        redemptionsEditText.setEnabled(b);
        driveOffsEditText.setEnabled(b);
        finalDropEditText.setEnabled(b);
        tillShortOverEditText.setEnabled(b);
        printOutTerminalEditText.setEnabled(b);
        printOutPassportEditText.setEnabled(b);

        declaredStartTimeHourEditText.setEnabled(!b);
        declaredStartTimeMinuteEditText.setEnabled(!b);
        startTimeAmPmToggleButton.setEnabled(!b);

        offTillRadioButton.setEnabled(!b);
        tillOneRadioButton.setEnabled(!b);
        tillTwoRadioButton.setEnabled(!b);

        scratchStartEditText.setEnabled(!b);

        scratchAddEditText.setEnabled(b);
        scratchCloseEditText.setEnabled(b);
        scratchPassportEditText.setEnabled(b);

        closeShiftButton.setEnabled(b);
        updateShiftButton.setEnabled(b);
        openShiftButton.setEnabled(!b);

    }

    private void instantiateVariables() {
        Intent intent = this.getIntent();
        userID = intent.getIntExtra("userID", 0);
        calendar = Calendar.getInstance();

        employeeDataAccess = new EmployeeDataAccess(this);
        shiftsDataAccess = new ShiftsDataAccess(this);

        toaster = new Toaster();

        employeeHasOpenShift = shiftsDataAccess.doesEmployeeHaveOpenShift(userID);


        infoBannerTextView = (TextView) findViewById(R.id.infoBannerTextView);

        employeeNameEditText = (EditText) findViewById(R.id.employeeNameEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        declaredStartTimeHourEditText = (EditText) findViewById(R.id.startTimeHourEditText);
        declaredStartTimeMinuteEditText = (EditText) findViewById(R.id.startTimeMinuteEditText);

        declaredEndTimeHourEditText = (EditText) findViewById(R.id.endTimeHourEditText);
        declaredEndTimeMinuteEditText = (EditText) findViewById(R.id.endTimeMinuteEditText);

        startTimeAmPmToggleButton = (ToggleButton) findViewById(R.id.startTimeAmPmToggleButton);
        endTimeAmPmToggleButton = (ToggleButton) findViewById(R.id.endTimeAmPmToggleButton);

        startingTillEditText = (EditText) findViewById(R.id.startingTillAmountEditText);
        redemptionsEditText = (EditText) findViewById(R.id.redemptionsEditText);
        driveOffsEditText = (EditText) findViewById(R.id.driveOffsEditText);
        finalDropEditText = (EditText) findViewById(R.id.finalDropEditText);
        tillShortOverEditText = (EditText) findViewById(R.id.tillShortOverEditText);


        onTillShiftFieldsLinearLayout = (LinearLayout) findViewById(R.id.onTillShiftFieldsLinearLayout);
        tillNumberRadioGroup = (RadioGroup) findViewById(R.id.tillNumberRadioGroup);
        tillOneRadioButton = (RadioButton) findViewById(R.id.tillOneRadioButton);
        tillTwoRadioButton = (RadioButton) findViewById(R.id.tillTwoRadioButton);
        offTillRadioButton = (RadioButton) findViewById(R.id.offTillRadioButton);
        tillNumberRadioGroupTextView = (TextView) findViewById(R.id.tillNumberRadioGroupTextView);

        printOutTerminalEditText = (EditText) findViewById(R.id.printOutTerminalEditText);
        printOutPassportEditText = (EditText) findViewById(R.id.printOutPassportEditText);
        printOutDifferenceEditText = (EditText) findViewById(R.id.printOutDifferenceEditText);


        scratchStartEditText = (EditText) findViewById(R.id.scratchStartEditText);
        scratchAddEditText = (EditText) findViewById(R.id.scratchAddEditText);
        scratchCloseEditText = (EditText) findViewById(R.id.scratchCloseEditText);
        scratchPassportEditText = (EditText) findViewById(R.id.scratchPassportEditText);
        scratchSoldEditText = (EditText) findViewById(R.id.scratchSoldEditText);
        scratchDifferenceEditText = (EditText) findViewById(R.id.scratchDifferenceEditText);


        openShiftButton = (Button) findViewById(R.id.openShiftButton);
        updateShiftButton = (Button) findViewById(R.id.updateShiftButton);
        closeShiftButton = (Button) findViewById(R.id.closeShiftButton);


        tillNumberRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("checkedId", " " + checkedId);

                //If the overlap radiobutton is clicked, the nonOverLapShiftFieldsLayout is made
                //visible.
                if (offTillRadioButton.getId() == checkedId) {
                    onTillShiftFieldsLinearLayout.setVisibility(View.GONE);
                } else {
                    onTillShiftFieldsLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void preloadStartTime() {
//        Log.d("PreloadStartTime", calendar.getTime().toString().substring(11, 16));
//        Log.d("PreloadStartTimeHour", calendar.getTime().toString().substring(11, 13));
//        Log.d("PreloadStartTimeMinute", calendar.getTime().toString().substring(14, 16));

        int hour = Integer.parseInt(calendar.getTime().toString().substring(11, 13));
        int minute = Integer.parseInt(calendar.getTime().toString().substring(14, 16));

        Log.d("preLoadStartTime Hour", String.valueOf(hour));
        Log.d("preloadStartTime Min", String.valueOf(minute));

//        String hourString = "";

        //This string need incase we need to assign "00" to the minute view.
        String minuteString = "";


        //Convert hour from 24hr to 12hr format and toggle AmPm toggleButton.
        if (hour == 0) {
            hour = 12;
            startTimeAmPmToggleButton.setChecked(false);
        } else if (hour == 12){
            startTimeAmPmToggleButton.setChecked(true);
        } else if (hour < 12){
            startTimeAmPmToggleButton.setChecked(false);
        }else if (hour > 12) {
            hour = hour - 12;
            startTimeAmPmToggleButton.setChecked(true);
        }


//      Round minute to closest quarter.
        if (minute < 7) {
            minuteString = "00";
        } else if (minute > 7 && minute <= 22) {
            minuteString = "15";
        } else if (minute > 22 && minute <= 37) {
            minuteString = "30";
        } else if (minute > 37 && minute <= 52) {
            minuteString = "45";
        } else if (minute > 52) {
            hour++;
            minuteString = "00";
        }


//        hourString = Integer.toString(hour);


//        Log.d("PreloadHour",  " " + hour);
//        Log.d("PreloadMinute",  " " + minute);
//        Log.d("PreloadMinuteString",  " " + minuteString);
//        Log.d("PreloadHourString",  " " + hourString);


        declaredStartTimeHourEditText.setText(String.valueOf(hour));
        declaredStartTimeMinuteEditText.setText(minuteString);


    }

    private void loadNewForm(int userID) {
        employeeName = employeeDataAccess.getEmployeeName(userID);
        employeeNameEditText.setText(employeeName);
        dateEditText.setText(dateStart);

    }




    //TODO: this method needs to be split up...
    /**
     * Recieves a String Array of shiftForm entries from a previously opened shift.
     * Each entry from the String Array is set into its respective EditText.
     * The declaredStartTime comes in the format '1234PM', it is split into 3 subStrings; hour
     * min, and amPm.
     *
     * @param shiftData String Array of entries from when the shift was opened.
     */
    private void loadExistingForm(ArrayList<String> shiftData) {
        shiftID = Integer.parseInt(shiftData.get(5));
        employeeName = shiftData.get(0);
        employeeNameEditText.setText(employeeName);

/////////////////////
        //TODO: Move all the declared time functions below to its own method.


        yearStart = Integer.parseInt(shiftData.get(22));
        dateStart = shiftData.get(2);

        dateEditText.setText(dateStart);

        //declaredStartTime comes in the format '1234PM', it is split into 3 subStrings.
        //hour and min are set into their respective EditTexts, amPm is used to toggle the AMPM
        //toggle button.
        declaredStartTime = shiftData.get(1);
        Log.d("loadStartTime", declaredStartTime);

        //TODO: this could be imporved.
        //If the length of the declaredStart time is less than 4, a space is added to beginning of
        // of the string.
        if (declaredStartTime.length() == 5) {
            declaredStartTime = "0" + declaredStartTime;
        }

        String hour = declaredStartTime.substring(0, declaredStartTime.indexOf(":"));
        String min = declaredStartTime.substring(declaredStartTime.indexOf(":") + 1, 5);
//        String amPM = declaredStartTime.substring(6);



        Log.d("StartTime", hour  + ":" + min + declaredStartTime.substring(6));

//        Log.d("loadStartTimeHour", hour);
//        Log.d("loadStartTimeMin", min);
//        Log.d("loadStartTimeAMPM", amPM);

        declaredStartTimeHourEditText.setText(hour);
        declaredStartTimeMinuteEditText.setText(min);

        if (declaredStartTime.contains("AM")){
            startTimeAmPmToggleButton.setChecked(false);
        } else {
            startTimeAmPmToggleButton.setChecked(true);

        }


        //TODO: Move all the declared time functions below to its own method.

        //declaredStartTime comes in the format '1234PM', it is split into 3 subStrings.
        //hour and min are set into their respective EditTexts, amPm is used to toggle the AMPM
        //toggle button.
        declaredEndTime = shiftData.get(6);
        Log.d("loadEndTime", declaredEndTime);

        //TODO: this could be imporved.
        //If the length of the declaredStart time is less than 4, a space is added to beginning of
        // of the string.
//        if (declaredEndTime.length() == 5) {
//            declaredEndTime = "0" + declaredEndTime;
//        }

        String hourEnd = declaredEndTime.substring(0, declaredEndTime.indexOf(":"));
        String minEnd = declaredEndTime.substring(declaredEndTime.indexOf(":") + 1, 5);


        Log.d("loadEndTimeHour", hourEnd);
        Log.d("loadEndTimeMin", minEnd);
        Log.d("loadEndTimeAMPM", declaredEndTime.substring(6));

        declaredEndTimeHourEditText.setText(hourEnd);
        declaredEndTimeMinuteEditText.setText(minEnd);

        if (declaredEndTime.contains("AM")) {
            endTimeAmPmToggleButton.setChecked(false);
        } else {
            endTimeAmPmToggleButton.setChecked(true);

        }
///////////

        //gets the previously checked radioButton's id, and compares it with each radioButton in the
        //form, which ever one it matches is the one that is checked.

        Log.d("loadTillNumberID", " " + tillNumber);
        Log.d("loadTillNumberOneID", " " + tillOneRadioButton.getId());
        Log.d("loadTillNumberTwoID", " " + tillTwoRadioButton.getId());
        Log.d("loadTillNumberOverLapID", " " + offTillRadioButton.getId());


        //gets the previously checked radioButton's id, and compares it with each radioButton in the
        //form, which ever one it matches is the one that is checked.
        tillNumber = Integer.parseInt(shiftData.get(3));
        if (tillNumber == tillOneRadioButton.getId()) {
            tillOneRadioButton.setChecked(true);
        } else if (tillNumber == tillTwoRadioButton.getId()) {
            tillTwoRadioButton.setChecked(true);
        } else if (tillNumber == offTillRadioButton.getId()) {
            offTillRadioButton.setChecked(true);
        }


        startingTillAmount = Double.parseDouble(shiftData.get(4));
        startingTillEditText.setText(String.valueOf(startingTillAmount));

        redemptionsAmount = Double.parseDouble(shiftData.get(9));
        redemptionsEditText.setText(String.valueOf(redemptionsAmount));

        driveOffs = Double.parseDouble(shiftData.get(10));
        driveOffsEditText.setText(String.valueOf(driveOffs));

        finalDrop = Double.parseDouble(shiftData.get(11));
        finalDropEditText.setText(String.valueOf(finalDrop));

        shortOver = Double.parseDouble(shiftData.get(12));
        tillShortOverEditText.setText(String.valueOf(shortOver));

        printOutTerminal = Integer.parseInt(shiftData.get(13));
        printOutTerminalEditText.setText(String.valueOf(printOutTerminal));

        printOutPassport = Integer.parseInt(shiftData.get(14));
        printOutPassportEditText.setText(String.valueOf(printOutPassport));

        printOutDifference = Integer.parseInt(shiftData.get(15));
        printOutDifferenceEditText.setText(String.valueOf(printOutDifference));

        scratchStart = Integer.parseInt(shiftData.get(16));
        scratchStartEditText.setText(String.valueOf(scratchStart));

        scratchAdd = Integer.parseInt(shiftData.get(17));
        scratchAddEditText.setText(String.valueOf(scratchAdd));

        scratchClose = Integer.parseInt(shiftData.get(18));
        scratchCloseEditText.setText(String.valueOf(scratchClose));

        scratchSold = Integer.parseInt(shiftData.get(19));
        scratchSoldEditText.setText(String.valueOf(scratchSold));

        scratchPassport = Integer.parseInt(shiftData.get(20));
        scratchPassportEditText.setText(String.valueOf(scratchPassport));

        scratchDifference = Integer.parseInt(shiftData.get(21));
        scratchDifferenceEditText.setText(String.valueOf(scratchDifference));


    }

    private void getStartDate() {
        dateStart = calendar.getTime().toString().substring(0, 10); // Wed Aug 09
        yearStart = calendar.get(Calendar.YEAR); // 2017
        monthStart = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonthStart = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = dateStart.substring(0,3);
        Log.d("Day of weeek ", dayOfWeek);

    }

    private void getEndDate() {
        dateEnd = calendar.getTime().toString().substring(0, 10); // Wed Aug 09
        yearEnd = calendar.get(Calendar.YEAR); // 2017


    }

    /**
     * Receives a string of errors and displays them to the user via textview above the scroll view.
     *
     * @param formErrors String of errors
     */
    private void updateInfoBanner(String formErrors) {
        String currentBannerText = infoBannerTextView.getText().toString();
        String newBannerText = currentBannerText + formErrors;
        infoBannerTextView.setText(newBannerText);
    }

    /**
     * Checks if the declared start hour and minutes editText entries are valid.
     *
     * @return Returns true if valid, false if otherwise.
     */
    private boolean isDeclaredStartTimeValid() {
        boolean pass = true;
        String formErrors = "";

        //Check if the hour EditText is not empty.
        if (!declaredStartTimeHourEditText.getText().toString().trim().equals("")) {
            //Check if the hour EditText value is less than 12, since we will be using the AmPm format.
            if (Integer.parseInt(declaredStartTimeHourEditText.getText().toString().trim()) > 12) {
                pass = false;
                formErrors += "Invalid Starting Hour.\n";
            }
        } else {
            pass = false;
            formErrors += "Enter starting time hour.";

        }

        //Check if the minute EditText is not empty.
        if (!declaredStartTimeMinuteEditText.getText().toString().equals("")) {

            //Check if the minute EditText value is less than 59.
            if (Integer.parseInt(declaredStartTimeMinuteEditText.getText().toString()) > 59) {
                pass = false;
                formErrors += "Invalid Starting Minute.\n";
            }
        } else {
            pass = false;
            formErrors += "Enter starting time minute";
        }

        //If the minute edittext only contains one integer, a zero will be added before the
        //integer.  2 >> 02
        if (declaredStartTimeMinuteEditText.getText().toString().length() == 1) {
            String currentStartTimeMinute = declaredStartTimeMinuteEditText.getText().toString();
            String newStartTimeMinute = "0" + currentStartTimeMinute;
            declaredStartTimeMinuteEditText.setText(newStartTimeMinute);
        }

        //If the validation was a fail, info banner will be updated with a list of all form errors.
        if (!pass) {
            updateInfoBanner(formErrors);
        }

        return pass;
    }

    private boolean isDeclaredEndTimeValid() {
        boolean pass = true;
        String formErrors = "";

        //Check if the hour EditText is not empty.
        if (!declaredEndTimeHourEditText.getText().toString().equals("")) {
            //Check if the hour EditText value is less than 12, since we will be using the AmPm format.
            if (Integer.parseInt(declaredEndTimeHourEditText.getText().toString()) > 12) {
                pass = false;
//                formErrors += "Invalid ending Hour.\n";
            }
        } else {
            pass = false;
//            formErrors += "Enter ending time hour.\n";

        }

        //Check if the minute EditText is not empty.
        if (!declaredEndTimeMinuteEditText.getText().toString().equals("")) {
            //Check if the minute EditText value is less than 59.
            if (Integer.parseInt(declaredEndTimeMinuteEditText.getText().toString()) > 59) {
                pass = false;
//                formErrors += "Invalid ending Minute.\n";
            }
        } else {
            pass = false;
//            formErrors += "Enter ending time minute.\n";
        }

        //If the minute edittext only contains only one integer, a zero will be added before the
        //integer.  2 >> 02
        if (declaredEndTimeMinuteEditText.getText().toString().length() == 1) {
            String currentStartTimeMinute = declaredEndTimeMinuteEditText.getText().toString();
            String newStartTimeMinute = "0" + currentStartTimeMinute;
            declaredEndTimeMinuteEditText.setText(newStartTimeMinute);
        }

        //If the validation was a fail, info banner will be updated with a list of all form errors.
        if (!pass) {
            updateInfoBanner(formErrors);
        }

        return pass;
    }

    /**
     * Combines and returns the values from the declared start time editTexts.
     *
     * @return declared start time in AmPm format.
     */
    public String getDeclaredStartTime() {
        String timeAmPm = "";
        timeAmPm
                = declaredStartTimeHourEditText.getText().toString()
                + ":"
                + declaredStartTimeMinuteEditText.getText().toString()
                + startTimeAmPmToggleButton.getText().toString();

        Log.d("startTimeAmPm", timeAmPm);

        return timeAmPm;
    }

    /**
     * Combines and returns the values from the declared end time editTexts.
     *
     * @return declared start time in AmPm format.
     */
    public String getDeclaredEndTime() {
        String timeAmPm;
        timeAmPm
                = declaredEndTimeHourEditText.getText().toString()
                + ":"
                + declaredEndTimeMinuteEditText.getText().toString()
                + endTimeAmPmToggleButton.getText().toString();

        Log.d("endTimeAmPm", timeAmPm);

        return timeAmPm;
    }


    private boolean areManditoryOpenningFormFieldsValid() {
        boolean pass = true;
        infoBannerTextView.setText("");

        //Check the declared start time.
        if (isDeclaredStartTimeValid()) {
            declaredStartTime = getDeclaredStartTime();
        } else {
            pass = false;
        }

        //Collect the start time here, only if the entry is valid. This is because shift ending
        //can change.
        if (isDeclaredEndTimeValid()) {
            declaredEndTime = getDeclaredEndTime();
        } else {
            declaredEndTime = "00:00AM";
        }


        //Till option must be selected before moving onwards.
        //If the 'offTill' option is selected, the methods returns and all onTill form fields are
        //not checked.
        if (tillNumberRadioGroup.getCheckedRadioButtonId() == -1) {
            updateInfoBanner("Must select till option");
        } else {
            //Get the till number, if off till return and do not collect remain form fields.
            tillNumber = tillNumberRadioGroup.getCheckedRadioButtonId();

            //If the shift is an over lap shift, retrun true, the rest of the checks are for on-till
            //shifts
            if (offTillRadioButton.isChecked()) {
                return true;
            }
        }


        //Check and get the starting till amount.
        if (TextUtils.isEmpty(startingTillEditText.getText().toString().trim())) {
            startingTillEditText.setError("Entry needed.");
            pass = false;
        } else {
            startingTillAmount = Double.parseDouble(df.format(Double.parseDouble(startingTillEditText.getText().toString().trim())));
        }
        Log.d("TextUtils", " scratchStart ");


        //Check and get the starting scratch ticket count.
        if (TextUtils.isEmpty(scratchStartEditText.getText().toString().trim())) {
            scratchStartEditText.setError("Entry needed.");
            pass = false;
        } else {
            scratchStart = Integer.parseInt(scratchStartEditText.getText().toString().trim());
        }
        Log.d("TextUtils", " scratchStart ");

        return pass;
    }

    private boolean areManditoryClosingFormFieldsValid() {
        boolean pass = true;
        infoBannerTextView.setText("");


        if (isDeclaredEndTimeValid()) {
            declaredEndTime = getDeclaredEndTime();
        } else {
            updateInfoBanner("End time invalid.");
            pass = false;
        }


        //Get redemptions
        if (TextUtils.isEmpty(redemptionsEditText.getText().toString().trim())) {
            redemptionsEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            redemptionsAmount = Double.parseDouble(df.format(Double.parseDouble(redemptionsEditText.getText().toString().trim())));

        }

        //Get drive offs
        if (TextUtils.isEmpty(driveOffsEditText.getText().toString().trim())) {
            driveOffsEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            driveOffs = Double.parseDouble(df.format(Double.parseDouble(redemptionsEditText.getText().toString().trim())));
        }

        //Get finalDrop
        if (TextUtils.isEmpty(finalDropEditText.getText().toString().trim())) {
            finalDropEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            finalDrop = Double.parseDouble(df.format(Double.parseDouble(finalDropEditText.getText().toString().trim())));
        }

        //Get till short over
        if (TextUtils.isEmpty(tillShortOverEditText.getText().toString().trim())) {
            tillShortOverEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            shortOver = Double.parseDouble(df.format(Double.parseDouble(tillShortOverEditText.getText().toString().trim())));
        }


        if (TextUtils.isEmpty(printOutTerminalEditText.getText().toString().trim())) {
            printOutTerminalEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            printOutTerminal = Double.parseDouble(df.format(Double.parseDouble(printOutTerminalEditText.getText().toString().trim())));
        }

        if (TextUtils.isEmpty(printOutPassportEditText.getText().toString().trim())) {
            printOutPassportEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            printOutPassport = Double.parseDouble(df.format(Double.parseDouble(printOutPassportEditText.getText().toString().trim())));
        }


        //Calculate difference between lotto terminal and passport.
        printOutDifference = printOutPassport - printOutTerminal;
        printOutDifferenceEditText.setText(String.valueOf(printOutDifference));

        //Get added scratch tickets
        if (TextUtils.isEmpty(scratchAddEditText.getText().toString().trim())) {
            scratchAddEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            scratchAdd = Integer.parseInt(scratchAddEditText.getText().toString().trim());
        }

        //Get closing scratch tickets
        if (TextUtils.isEmpty(scratchCloseEditText.getText().toString().trim())) {
            scratchCloseEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            scratchClose = Integer.parseInt(scratchCloseEditText.getText().toString().trim());
        }


        if (TextUtils.isEmpty(scratchPassportEditText.getText().toString().trim())) {
            scratchPassportEditText.setError("Cannot be empty.");
            pass = false;
        } else {
            scratchPassport = Integer.parseInt(scratchPassportEditText.getText().toString().trim());
        }

        if (pass) {
            //Calculate scratch sold count.
            scratchSold = scratchStart + scratchAdd - scratchClose;
            scratchSoldEditText.setText(String.valueOf(scratchSold));


            //Calculate scratch difference.
            scratchDifference = scratchPassport - scratchSold;
            scratchDifferenceEditText.setText(String.valueOf(scratchDifference));
        }

        if (!pass) {
            updateInfoBanner("Please see form errors...");
        }

        return pass;
    }

    /**
     * This method will check all EditTexts that are not manditory for opening a shift, if they
     * contain a valid entry, it will be saved in the db.
     */
    private void checkAndUpdateFormFields() {

        if (isDeclaredEndTimeValid()) {
            declaredEndTime = getDeclaredEndTime();
        } else {
            declaredEndTime = "00:00AM";
        }


        //Get redemptions
        if (TextUtils.isEmpty(redemptionsEditText.getText().toString().trim())) {
            redemptionsAmount = 00;
        } else {
            redemptionsAmount = Double.parseDouble(df.format(Double.parseDouble(redemptionsEditText.getText().toString().trim())));
        }

        //Get drive offs
        if (TextUtils.isEmpty(driveOffsEditText.getText().toString().trim())) {
            driveOffs = 00;
        } else {
            driveOffs = Double.parseDouble(df.format(Double.parseDouble(redemptionsEditText.getText().toString().trim())));
        }

        //Get finalDrop
        if (TextUtils.isEmpty(finalDropEditText.getText().toString().trim())) {
            finalDrop = 00;
        } else {
            finalDrop = Double.parseDouble(df.format(Double.parseDouble(finalDropEditText.getText().toString().trim())));
        }

        //Get till short over
        if (TextUtils.isEmpty(tillShortOverEditText.getText().toString().trim())) {
            shortOver = 00;
        } else {
            shortOver = Double.parseDouble(df.format(Double.parseDouble(tillShortOverEditText.getText().toString().trim())));
        }


        if (TextUtils.isEmpty(printOutTerminalEditText.getText().toString().trim())) {
            printOutTerminal = 00;
        } else {
            printOutTerminal = Double.parseDouble(df.format(Double.parseDouble(printOutTerminalEditText.getText().toString().trim())));
        }

        if (TextUtils.isEmpty(printOutPassportEditText.getText().toString().trim())) {
            printOutPassport = 00;
        } else {
            printOutPassport = Double.parseDouble(df.format(Double.parseDouble(printOutPassportEditText.getText().toString().trim())));
        }


        //Calculate difference between lotto terminal and passport.
        printOutDifference = printOutPassport - printOutTerminal;
        printOutDifferenceEditText.setText(String.valueOf(printOutDifference));

        //Get added scratch tickets
        if (TextUtils.isEmpty(scratchAddEditText.getText().toString().trim())) {
            scratchAdd = 00;
        } else {
            scratchAdd = Integer.parseInt(scratchAddEditText.getText().toString().trim());
        }

        //Get closing scratch tickets
        if (TextUtils.isEmpty(scratchCloseEditText.getText().toString().trim())) {
            scratchClose = 00;
        } else {
            scratchClose = Integer.parseInt(scratchCloseEditText.getText().toString().trim());
        }


        if (TextUtils.isEmpty(scratchPassportEditText.getText().toString().trim())) {
            scratchPassport = 00;
        } else {
            scratchPassport = Integer.parseInt(scratchPassportEditText.getText().toString().trim());
        }

        //Calculate scratch sold count.
        scratchSold = scratchStart + scratchAdd - scratchClose;
        scratchSoldEditText.setText(String.valueOf(scratchSold));


        //Calculate scratch difference.
        scratchDifference = scratchPassport - scratchSold;
        scratchDifferenceEditText.setText(String.valueOf(scratchDifference));

        //Pass all the collected values to dbHelper to update in the db.
        shiftsDataAccess.updateShiftForm(shiftID, declaredEndTime, driveOffs, finalDrop, shortOver,
                printOutTerminal, printOutPassport, printOutDifference, scratchAdd, scratchClose,
                scratchPassport, scratchSold, scratchDifference);

        finish();
        startActivity(getIntent());
    }

    private void openNewShift() {
        Log.d("openingNewShift", dateStart);

        shiftsDataAccess.openNewShift(employeeName, userID, dateStart, yearStart, monthStart, dayOfMonthStart, dayOfWeek, declaredStartTime,
                actualStartTime, declaredEndTime, tillNumber, startingTillAmount, scratchStart, 1);


    }





    public void openShiftButtonOnClick(View view) {
        if (areManditoryOpenningFormFieldsValid()) {


            if (isDeclaredEndTimeValid()) {
                declaredEndTime = getDeclaredEndTime();
            } else {
                declaredEndTime = "00:00AM";
            }


            actualStartTime = calendar.getTime().toString().substring(11, 16);
            openNewShift();
            toaster.toastUp(getApplicationContext(), "Shift Opened.");
        }
        shiftsDataAccess.printTable();

        finish();
        startActivity(getIntent());
    }

    public void updateShiftButtonOnClick(View view)  {
        checkAndUpdateFormFields();
        shiftsDataAccess.printTable();
        toaster.toastUp(getApplicationContext(), "Shift Updated.");

        finish();
        startActivity(getIntent());
    }

    public void closeShiftButtonOnClick(View view) {
        if (areManditoryOpenningFormFieldsValid()) {
            if (areManditoryClosingFormFieldsValid()) {
                getEndDate();
                actualEndTime = calendar.getTime().toString().substring(11, 16);

                try {
                    calculateHoursWorked();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("CloseShiftButton", "calculateHours error");
                }

                shiftsDataAccess.closeShift(declaredEndTime, actualEndTime, redemptionsAmount,
                        driveOffs, finalDrop, shortOver, printOutTerminal, printOutPassport,
                        printOutDifference, scratchAdd, scratchClose, scratchPassport, scratchSold,
                        scratchDifference, shiftID, 0, hoursWorked);
                toaster.toastUp(getApplicationContext(), "Shift Closed.");
                finish();
            }
        }
        shiftsDataAccess.printTable();


    }



    private void calculateHoursWorked() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyEEE MMM ddh:mma");

        Date start;
        Date end;
        double difference;


        start = simpleDateFormat.parse(yearStart + dateStart + declaredStartTime);
        Log.d("StartDataFormat", String.valueOf(yearStart + dateStart + declaredStartTime));

        end = simpleDateFormat.parse(yearEnd + dateEnd + declaredEndTime);
        Log.d("EndDataFormat", String.valueOf(yearEnd + dateEnd + declaredEndTime));

        difference = end.getTime() - start.getTime();

        Log.d("Difference", String.valueOf(difference)); // 3.06E7
        Log.d("Time difference", String.valueOf(((difference/1000)/60)/60));
        //Prints '8'

        difference = (((difference/1000)/60)/60);

        String dif;
        dif = df.format(difference);

        hoursWorked = Double.parseDouble(dif);

        //Round to the nearest quarter decimal.
        hoursWorked = Math.round(hoursWorked*4)/4f;



    }


}