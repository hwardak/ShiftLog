package hwardak.shiftlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ShiftFormActivity extends AppCompatActivity {

    int userID;

    EmployeeDataAccess employeeDataAccess;
    ShiftsDataAccess shiftsDataAccess;


    EditText employeeNameEditText;
    EditText dateEditText;
    EditText declaredStartTimeHourEditText;
    EditText declaredStartTimeMinuteEditText;
    EditText declaredEndTimeHourEditText;
    EditText declaredEndTimeMinuteEditText;
    EditText startingTillAmountEditText;
    EditText finalDropAmountEditText;
    EditText redemptionsEditText;
    TextView infoBannerTextView;

    RadioGroup tillNumberRadioGroup;
    RadioButton tillOneRadioButton;
    RadioButton tillTwoRadioButton;
    RadioButton tillOverLapRadioButton;
    ToggleButton startTimeAmPmToggleButton;
    ToggleButton endTimeAmPmToggleButton;

    boolean employeeHasOpenShift;

    String employeeName;
    String date;
    int year;
    int month;
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

    Calendar calendar;


    /**
     * Views and variables instantiated.
     * Checks if employee has an open shift.
     * If employee is not signed in, time and date are collected and a new form is prepared for them.
     * If employee is signed in, their singed in form is reloaded, and ready to modify or close.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_form);

        this.instantiateVariables();

        employeeHasOpenShift = this.doesEmployeeHaveOpenShift(userID);

        if (employeeHasOpenShift) {
            ArrayList<String> shiftData = shiftsDataAccess.getEmployeesOpenShiftData(userID);
            this.loadExistingForm(shiftData);

        } else {
            this.getStartTimeDate();
            this.loadNewForm(userID);

        }
    }

    private boolean doesEmployeeHaveOpenShift(int userID) {
        if (shiftsDataAccess.doesEmployeeHaveOpenShift(userID)) {
            return true;
        } else {
            return false;
        }
    }


    private void instantiateVariables() {
        Intent intent = this.getIntent();
        userID = intent.getIntExtra("userID", 0);
        calendar = Calendar.getInstance();

        employeeDataAccess = new EmployeeDataAccess(this);
        shiftsDataAccess = new ShiftsDataAccess(this);

        employeeNameEditText = (EditText) findViewById(R.id.employeeNameEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        declaredStartTimeHourEditText = (EditText) findViewById(R.id.startTimeHourEditText);
        declaredStartTimeMinuteEditText = (EditText) findViewById(R.id.startTimeMinuteEditText);
        declaredEndTimeHourEditText = (EditText) findViewById(R.id.endTimeHourEditText);
        declaredEndTimeMinuteEditText = (EditText) findViewById(R.id.endTimeMinuteEditText);
        startingTillAmountEditText = (EditText) findViewById(R.id.startingTillAmountEditText);
        finalDropAmountEditText = (EditText) findViewById(R.id.finalDropAmountEditText);
        redemptionsEditText = (EditText) findViewById(R.id.redemptionsEditText);
        infoBannerTextView = (TextView) findViewById(R.id.infoBannerTextView);
        startTimeAmPmToggleButton = (ToggleButton) findViewById(R.id.startTimeAmPmToggleButton);
        endTimeAmPmToggleButton = (ToggleButton) findViewById(R.id.endTimeAmPmToggleButton);

        tillNumberRadioGroup = (RadioGroup) findViewById(R.id.tillNumberRadioGroup);
        tillOneRadioButton = (RadioButton) findViewById(R.id.tillOneRadioButton);
        tillTwoRadioButton = (RadioButton) findViewById(R.id.tillTwoRadioButton);
        tillOverLapRadioButton = (RadioButton) findViewById(R.id.tillOverLapRadioButton);


        tillNumberRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("TillOverLap", " " + tillOverLapRadioButton.getId());
                Log.d("checkedId", " " + checkedId);
                if(tillOverLapRadioButton.getId() == checkedId){
                    Log.d("TillOverLapToggled", " ");
                }

            }
        });

    }


    private void loadNewForm(int userID) {
        employeeName = employeeDataAccess.getEmployeeName(userID);
        employeeNameEditText.setText(employeeName);
        dateEditText.setText(date);

    }

    private void loadExistingForm(ArrayList<String> shiftData) {
        employeeName = shiftData.get(0);
        employeeNameEditText.setText(employeeName);

        declaredStartTime = shiftData.get(1);
//        declaredStartTimeEditText.setText(declaredStartTime);

        date = shiftData.get(2);
        dateEditText.setText(date);

        tillNumber = Integer.parseInt(shiftData.get(3));
        tillNumberRadioGroup.check(tillNumber);

        startingTillAmount = Double.parseDouble(shiftData.get(4));
        startingTillAmountEditText.setText(String.valueOf(startingTillAmount));


    }

    private void getStartTimeDate() {
        date = calendar.getTime().toString().substring(0, 11); // Wed Aug 09
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonthStart = calendar.get(Calendar.DAY_OF_MONTH);

    }


    public void openShiftButtonOnClick(View view) {
        actualStartTime = calendar.getTime().toString().substring(11, 16);
        checkOpeningFormFields();

    }



    private boolean checkOpeningFormFields() {
        boolean pass = true;
        infoBannerTextView.setText("");

        if(tillOverLapRadioButton.isChecked()){
            return true;
        }

        if(isDeclaredStartTimeValid()){
            declaredStartTime = getDeclaredStartTime();
        } else {
            pass = false;
        }

        if(isStartingTillValid()){
            startingTillAmount = Double.parseDouble(startingTillAmountEditText.getText().toString());
        } else {
            pass = false;
        }

        return pass;
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

        //If the minute edittext only contains only one integer, a zero will be added before the
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





    //TODO: add one to two zeros to end of starting till
    public boolean isStartingTillValid() {
        String formErrors = "";
        boolean pass = true;

        //If starting till is NOT empty.
        if (!startingTillAmountEditText.getText().toString().equals("")) {
            String startingTill = startingTillAmountEditText.getText().toString();

            //If starting till contains a decimal.
            if (startingTill.contains(".")) {

                //If no chars after decimal, add '00'
                if (startingTill.substring(startingTill.indexOf(".")).length() == 1) {
                    startingTill += "00";
                }

                //If only 1 char after decimal, add '0'
                if (startingTill.substring(startingTill.indexOf(".")).length() == 2) {
                    startingTill += "0";
                }

                //No decimal, add '.00'
            } else {
                startingTill += ".00";
            }
            //Update starting till EditText with formatted starting till value.
            startingTillAmountEditText.setText(startingTill);

            Log.d("Starting Till", " " + startingTill);

        } else {
            formErrors += "Starting Till Invalid.";
            updateInfoBanner(formErrors);
            pass = false;
        }
        return pass;
    }

    public String getDeclaredStartTime() {
        String timeAmPm = "";
        timeAmPm
                = declaredStartTimeHourEditText.getText().toString()
                + declaredStartTimeMinuteEditText.getText().toString()
                + startTimeAmPmToggleButton.getText().toString();

        Log.d("timeAmPm", timeAmPm);

        return timeAmPm;
    }


    private void updateInfoBanner(String formErrors) {
        String currentBannerText = infoBannerTextView.getText().toString();
        String newBannerText = currentBannerText + formErrors;
        infoBannerTextView.setText(newBannerText);
    }
}
