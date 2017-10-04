package hwardak.shiftlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
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

import java.util.ArrayList;
import java.util.Calendar;

public class ShiftFormActivity extends AppCompatActivity {

    int userID;
    int shiftID;

    EmployeeDataAccess employeeDataAccess;
    ShiftsDataAccess shiftsDataAccess;

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
    int yearStart;
    int monthStart;
    int monthEnd;

    int dayOfMonthStart;
    int dayOfMonthEnd;


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
            ArrayList<String> shiftData = shiftsDataAccess.getEmployeesOpenShiftData(userID);
            this.loadExistingForm(shiftData);
            enableClosingViews(true);

        } else {
            this.getStartDate();
            this.preloadStartTime();
            this.loadNewForm(userID);
            enableClosingViews(false);


        }
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

        printOutTerminalEditText  = (EditText) findViewById(R.id.printOutTerminalEditText);
        printOutPassportEditText  = (EditText) findViewById(R.id.printOutPassportEditText);
        printOutDifferenceEditText  = (EditText) findViewById(R.id.printOutDifferenceEditText);


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
                if(offTillRadioButton.getId() == checkedId){
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
        String hourString = "";
        String minuteString = "";


        //Convert hour from 24hr to 12hr format and toggle AmPm toggleButton.
        if (hour == 0) {
            hour = 12;
            startTimeAmPmToggleButton.setChecked(false);
        } else if (hour > 12) {
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
        } else if (minute > 52){
            hour++;
            minuteString = "00";
        }


        hourString = Integer.toString(hour);




//        Log.d("PreloadHour",  " " + hour);
//        Log.d("PreloadMinute",  " " + minute);
//        Log.d("PreloadMinuteString",  " " + minuteString);
//        Log.d("PreloadHourString",  " " + hourString);




        declaredStartTimeHourEditText.setText(hourString);
        declaredStartTimeMinuteEditText.setText(minuteString);


    }

//    private boolean doesEmployeeHaveOpenShift(int userID) {
//        if (shiftsDataAccess.doesEmployeeHaveOpenShift(userID)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    private void loadNewForm(int userID) {
        employeeName = employeeDataAccess.getEmployeeName(userID);
        employeeNameEditText.setText(employeeName);
        dateEditText.setText(dateStart);

    }

    /**
     * Recieves a String Array of shiftForm entries from a previously opened shift.
     * Each entry from the String Array is set into its respective EditText.
     * The declaredStartTime comes in the format '1234PM', it is split into 3 subStrings; hour
     * min, and amPm.
     * @param shiftData String Array of entries from when the shift was opened.
     */
    private void loadExistingForm(ArrayList<String> shiftData) {
        shiftID = Integer.parseInt(shiftData.get(5));
        employeeName = shiftData.get(0);
        employeeNameEditText.setText(employeeName);

/////////////////////
        //TODO: Move all the declared time functions below to its own method.

        //declaredStartTime comes in the format '1234PM', it is split into 3 subStrings.
        //hour and min are set into their respective EditTexts, amPm is used to toggle the AMPM
        //toggle button.
        declaredStartTime = shiftData.get(1);
        Log.d("loadStartTime", declaredStartTime);

        //TODO: this could be imporved.
        //If the length of the declaredStart time is less than 4, a space is added to beginning of
        // of the string.
        if(declaredStartTime.length() == 5){
            declaredStartTime = "0" + declaredStartTime;
        }

        String hour = declaredStartTime.substring(0,declaredStartTime.indexOf(":"));
        String min = declaredStartTime.substring(declaredStartTime.indexOf(":") + 1 , 5);
        String amPM = declaredStartTime.substring(5);


//        Log.d("loadStartTimeHour", hour);
//        Log.d("loadStartTimeMin", min);
//        Log.d("loadStartTimeAMPM", amPM);

        declaredStartTimeHourEditText.setText(hour);
        declaredStartTimeMinuteEditText.setText(min);

        if(amPM.equals("AM")){
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
        if(declaredEndTime.length() == 5){
            declaredEndTime = "0" + declaredEndTime;
        }

        String hourEnd = declaredEndTime.substring(0,declaredEndTime.indexOf(":"));
        String minEnd = declaredEndTime.substring(declaredEndTime.indexOf(":") + 1 , 5);
        String amPMEnd = declaredEndTime.substring(5);


        Log.d("loadEndTimeHour", hourEnd);
        Log.d("loadEndTimeMin", minEnd);
        Log.d("loadEndTimeAMPM", amPMEnd);

        declaredEndTimeHourEditText.setText(hourEnd);
        declaredEndTimeMinuteEditText.setText(minEnd);

        if(amPM.equals("AM")){
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
        if(tillNumber == tillOneRadioButton.getId()){
            tillOneRadioButton.setChecked(true);
        } else if(tillNumber == tillTwoRadioButton.getId()) {
            tillTwoRadioButton.setChecked(true);
        } else if(tillNumber == offTillRadioButton.getId()){
            offTillRadioButton.setChecked(true);
        }


        startingTillAmount = Double.parseDouble(formatToDollarValue(shiftData.get(4)));
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
        dateStart = calendar.getTime().toString().substring(0, 11); // Wed Aug 09
        yearStart = calendar.get(Calendar.YEAR);
        monthStart = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonthStart = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private boolean isShiftOverLap() {
        RadioButton tillNumber = (RadioButton) findViewById(tillNumberRadioGroup.getCheckedRadioButtonId());
        Log.d("TillNumber", tillNumber.getText().toString());

        if(tillNumber.getText().toString().equals("Over Lap")){
            return true;
        } else {
            return false;
        }
    }

    private String formatTimeTo24h(String time) {

        return time;

    }

    /**
     * Receives a string of errors and displays them to the user via textview above the scroll view.
     * @param formErrors String of errors
     */
    private void updateInfoBanner(String formErrors) {
        String currentBannerText = infoBannerTextView.getText().toString();
        String newBannerText = currentBannerText + formErrors;
        infoBannerTextView.setText(newBannerText);
    }

    /**
     * Checks if the declared start hour and minutes editText entries are valid.
     * @return Returns true if valid, false if otherwise.
     */
    private boolean isDeclaredStartTimeValid() {
        boolean pass = true;
        String formErrors = "";

        //Check if the hour EditText is not empty.
        if(!declaredStartTimeHourEditText.getText().toString().equals("")){
            //Check if the hour EditText value is less than 12, since we will be using the AmPm format.
            if(Integer.parseInt(declaredStartTimeHourEditText.getText().toString()) > 12 ){
                pass = false;
                formErrors += "Invalid Starting Hour.\n";
            }
        } else {
            pass = false;
            formErrors += "Enter starting time hour.";

        }

        //Check if the minute EditText is not empty.
        if(!declaredStartTimeMinuteEditText.getText().toString().equals("")) {

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
        if(declaredStartTimeMinuteEditText.getText().toString().length() == 1){
            String currentStartTimeMinute = declaredStartTimeMinuteEditText.getText().toString();
            String newStartTimeMinute = "0" + currentStartTimeMinute;
            declaredStartTimeMinuteEditText.setText(newStartTimeMinute);
        }

        //If the validation was a fail, info banner will be updated with a list of all form errors.
        if(!pass){
            updateInfoBanner(formErrors);
        }

        return pass;
    }

    private boolean isDeclaredEndTimeValid() {
        boolean pass = true;
        String formErrors = "";

        //Check if the hour EditText is not empty.
        if(!declaredEndTimeHourEditText.getText().toString().equals("")){
            //Check if the hour EditText value is less than 12, since we will be using the AmPm format.
            if(Integer.parseInt(declaredEndTimeHourEditText.getText().toString()) > 12 ){
                pass = false;
//                formErrors += "Invalid ending Hour.\n";
            }
        } else {
            pass = false;
//            formErrors += "Enter ending time hour.\n";

        }

        //Check if the minute EditText is not empty.
        if(!declaredEndTimeMinuteEditText.getText().toString().equals("")) {
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
        if(declaredEndTimeMinuteEditText.getText().toString().length() == 1){
            String currentStartTimeMinute = declaredEndTimeMinuteEditText.getText().toString();
            String newStartTimeMinute = "0" + currentStartTimeMinute;
            declaredEndTimeMinuteEditText.setText(newStartTimeMinute);
        }

        //If the validation was a fail, info banner will be updated with a list of all form errors.
        if(!pass){
            updateInfoBanner(formErrors);
        }

        return pass;    }

    /**
     * Combines and returns the values from the declared start time editTexts.
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
     * @return declared start time in AmPm format.
     */
    public String getDeclaredEndTime() {
        String timeAmPm;
        timeAmPm
                = declaredEndTimeHourEditText.getText().toString()
                + ":"
                + declaredEndTimeMinuteEditText.getText().toString()
                + endTimeAmPmToggleButton.getText().toString();

        Log.d("startTimeAmPm", timeAmPm);

        return timeAmPm;
    }

    /**
     * Checks and formats starting till value, and alerts user of invalid entries.
     * @return returns true if valid, false otherwise.
     */
    private boolean isStartingTillValid() {
        String formErrors = "";
        boolean pass = true;

        if(TextUtils.isEmpty(startingTillEditText.getText().toString())) {
            startingTillEditText.setError("Entry missing");
            Log.d("TextUtils", " startingTill " );
            pass = false;
        }


//        //If starting till is NOT empty.
//        if (!startingTillEditText.getText().toString().equals("")) {
//            String startingTill = startingTillEditText.getText().toString();
//
        return pass;
    }

    private boolean isRedemptionsValid() {
        String formErrors = "";
        boolean pass = true;

        //If starting till is NOT empty.
        if (!redemptionsEditText.getText().toString().equals("")) {
            String redemptions = redemptionsEditText.getText().toString();

            //If starting till contains a decimal.
            if (redemptions.contains(".")) {

                //If no chars after decimal, add '00'
                if (redemptions.substring(redemptions.indexOf(".")).length() == 1) {
                    redemptions += "00";
                }

                //If only 1 char after decimal, add '0'
                if (redemptions.substring(redemptions.indexOf(".")).length() == 2) {
                    redemptions += "0";
                }

                //No decimal, add '.00'
            } else {
                redemptions += ".00";
            }
            //Update starting till EditText with formatted starting till value.
            redemptionsEditText.setText(redemptions);

            Log.d("Redemption", " " + redemptions);

        } else {
            formErrors += "Redemptions invalid.\n";
            updateInfoBanner(formErrors);
            pass = false;
        }
        return pass;
    }

    private boolean isFinalDropValid() {
        String formErrors = "";
        boolean pass = true;

        Log.d("TextUtils", " oooooooooooo " );

        String finalDrop = finalDropEditText.getText().toString();
        if(TextUtils.isEmpty(finalDrop)) {
            finalDropEditText.setError("Your message");
            Log.d("TextUtils", " oooooooooooo " );
            pass = false;
        }


        //If starting till is NOT empty.
//        if (!finalDropEditText.getText().toString().equals("")) {
//            String finalDrop = finalDropEditText.getText().toString();
//
//            //If starting till contains a decimal.
//
//            //Update starting till EditText with formatted starting till value.
//            finalDropEditText.setText(finalDrop);
//
//            Log.d("Final Drop", " " + finalDrop);
//
//
//        } else {
//            formErrors += "Final drop invalid.\n";
//            updateInfoBanner(formErrors);
//            pass = false;
//        }
        return pass;


    }

    private String formatToDollarValue(String string){
        if (string.contains(".")) {

            //If no chars after decimal, add '00'
            if (string.substring(string.indexOf(".")).length() == 1) {
                string += "00";
            }

            //If only 1 char after decimal, add '0'
            if (string.substring(string.indexOf(".")).length() == 2) {
                string += "0";
            }

            //No decimal, add '.00'
        } else {
            string += ".00";
        }

        return string;
    }

    //TODO: Should move all collecting and data writing to another method, only invoked if this method returns true.
    private boolean areManditoryClosingFormFieldsValid(){
        boolean pass = true;
        infoBannerTextView.setText("");

        if(isFinalDropValid()){
            finalDropAmount = Double.parseDouble(finalDropEditText.getText().toString());
        } else {
            pass = false;
        }

        if(isRedemptionsValid()) {
            redemptionsAmount = Double.parseDouble(redemptionsEditText.getText().toString());
        } else {
            pass = false;
        }

        return pass;
    }

    //TODO: Should move all collecting and data writing to another method, only invoked if this method returns true.
    private boolean areManditoryOpenningFormFieldsValid() {
        boolean pass = true;
        infoBannerTextView.setText("");

        //Check the declared start time.
        if(isDeclaredStartTimeValid()){
            declaredStartTime = getDeclaredStartTime();
        } else {
            pass = false;
        }



        //Till option must be selected before moving onwards.
        //If the 'offTill' option is selected, the methods returns and all onTill form fields are
        //not checked.
        if (tillNumberRadioGroup.getCheckedRadioButtonId() == -1)
        {
            updateInfoBanner("Must select till option");
        } else  {
            //Get the till number, if off till return and do not collect remain form fields.
            tillNumber = tillNumberRadioGroup.getCheckedRadioButtonId();

            //If the shift is an over lap shift, retrun true, the rest of the checks are for on-till
            //shifts
            if(offTillRadioButton.isChecked()){
                return true;
            }
        }




        //Check and get the starting till amount.
        if(TextUtils.isEmpty(startingTillEditText.getText().toString().trim())) {
            startingTillEditText.setError("Entry needed.");
            pass = false;
        } else {
            startingTillAmount = Double.parseDouble(startingTillEditText.getText().toString().trim());
        }
        Log.d("TextUtils", " scratchStart " );


        //Check and get the starting scratch ticket count.
        if(TextUtils.isEmpty(scratchStartEditText.getText().toString().trim())) {
            scratchStartEditText.setError("Entry needed.");
            pass = false;
        } else {
            scratchStart = Integer.parseInt(scratchStartEditText.getText().toString().trim());
        }
        Log.d("TextUtils", " scratchStart " );

        return pass;
    }

    public void openShiftButtonOnClick(View view) {
        if(areManditoryOpenningFormFieldsValid()) {
            actualStartTime = calendar.getTime().toString().substring(11, 16);
            openNewShift();
        }
    }

    private void openNewShift() {
        Log.d("openingNewShift", dateStart);

        shiftsDataAccess.openNewShift(employeeName, userID, dateStart, declaredStartTime,
                actualStartTime, tillNumber, startingTillAmount, scratchStart, 1);

    }

    public void closeShiftButtonOnClick(View view) {
        if(areManditoryOpenningFormFieldsValid()) {
            if (areManditoryClosingFormFieldsValid()) {
                actualEndTime = calendar.getTime().toString().substring(11, 16);
            }
        }
    }



    /**
     * This method will check all EditTexts that are not manditory for opening a shift, if they
     * contain a valid entry, it will be saved in the db.
     */
    private void checkAndUpdateFormFields(){

        if(isDeclaredEndTimeValid()){
            declaredEndTime = getDeclaredEndTime();
        } else {
            declaredEndTime = "00:00AM";
        }


        //Get redemptions
        if(TextUtils.isEmpty(redemptionsEditText.getText().toString().trim())) {
            redemptionsAmount = 00;
        } else {
            redemptionsAmount = Double.parseDouble(redemptionsEditText.getText().toString().trim());
        }

        //Get drive offs
        if(TextUtils.isEmpty(driveOffsEditText.getText().toString().trim())) {
            driveOffs = 00;
        } else {
            driveOffs = Double.parseDouble(redemptionsEditText.getText().toString().trim());
        }

        //Get finalDrop
        if(TextUtils.isEmpty(finalDropEditText.getText().toString().trim())) {
            finalDrop = 00;
        } else {
            finalDrop = Double.parseDouble(finalDropEditText.getText().toString().trim());
        }

        //Get till short over
        if(TextUtils.isEmpty(tillShortOverEditText.getText().toString().trim())) {
            shortOver = 00;
        } else {
            shortOver = Double.parseDouble(tillShortOverEditText.getText().toString().trim());
        }


        if(TextUtils.isEmpty(printOutTerminalEditText.getText().toString().trim())) {
            printOutTerminal = 00;
        } else {
            printOutTerminal = Double.parseDouble(printOutTerminalEditText.getText().toString().trim());
        }

        if(TextUtils.isEmpty(printOutPassportEditText.getText().toString().trim())) {
            printOutPassport = 00;
        } else {
            printOutPassport = Double.parseDouble(printOutPassportEditText.getText().toString().trim());
        }


        //Calculate difference between lotto terminal and passport.
        printOutDifference = printOutPassport - printOutTerminal;
        printOutDifferenceEditText.setText(String.valueOf(printOutDifference));

        //Get added scratch tickets
        if(TextUtils.isEmpty(scratchAddEditText.getText().toString().trim())) {
            scratchAdd = 00;
        } else {
            scratchAdd = Integer.parseInt(scratchAddEditText.getText().toString().trim());
        }

        //Get closing scratch tickets
        if(TextUtils.isEmpty(scratchCloseEditText.getText().toString().trim())) {
            scratchClose = 00;
        } else {
            scratchClose = Integer.parseInt(scratchCloseEditText.getText().toString().trim());
        }


        if(TextUtils.isEmpty(scratchPassportEditText.getText().toString().trim())) {
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

        Intent intent = getIntent();
        finish();
        startActivity(getIntent());
    }


    public void updateShiftButtonOnClick(View view) {
        checkAndUpdateFormFields();


    }
}
