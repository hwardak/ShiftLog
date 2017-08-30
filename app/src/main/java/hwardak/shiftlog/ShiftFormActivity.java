package hwardak.shiftlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ShiftFormActivity extends AppCompatActivity {

    int userID;

    EmployeeDataAccess employeeDataAccess;
    ShiftsDataAccess shiftsDataAccess;


    EditText employeeNameEditText;
    EditText dateEditText;
    EditText declaredStartTimeEditText;
    EditText declaredEndTimeEditText;
    EditText startingTillAmountEditText;
    EditText finalDropAmountEditText;
    EditText redemptionsEditText;
    TextView infoBannerTextView;
    RadioGroup tillNumberRadioGroup;

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
        declaredStartTimeEditText = (EditText) findViewById(R.id.startTimeEditText);
//        declaredEndTimeEditText = (EditText) findViewById(R.id.endTimeEditText);
        startingTillAmountEditText = (EditText) findViewById(R.id.startingTillAmountEditText);
        finalDropAmountEditText = (EditText) findViewById(R.id.finalDropAmountEditText);
        redemptionsEditText = (EditText) findViewById(R.id.redemptionsEditText);
        tillNumberRadioGroup = (RadioGroup) findViewById(R.id.tillNumberRadioGroup);
        infoBannerTextView = (TextView) findViewById(R.id.infoBannerTextView);


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
        declaredStartTimeEditText.setText(declaredStartTime);

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


    private void checkOpeningFormFields() {

    }

//    //TODO: add zero to start of time if length is 3.
//    private boolean isDeclaredStartTimeValid() {
//        declaredStartTime = declaredStartTimeEditText.getText().toString();
//        String formErrors = "";
//        int hour;
//        int minute;
//        boolean formValidationPass = true;
//
//        Log.d("declaredStart", declaredStartTime);
//
//        //remove semicolon.
//        declaredStartTime = declaredStartTime.replace(":", "");
//
//        //Check if declared time is NULL and between 3 and 4 chars in length.
//        if (declaredStartTime != null && (declaredStartTime.length() >= 3 && declaredStartTime.length() <= 4)) {
//
//            //If the declaredStartTime length is, a zero will be added to the begining of the String.
//            // 930 = 0930
//            if (declaredStartTime.length() == 3) {
//                declaredStartTime = "0" + declaredStartTime;
//            }
//
//            hour = Integer.parseInt(declaredStartTime.substring(0, 2));
//            minute = Integer.parseInt(declaredStartTime.substring(2));
//
//            Log.d("hour", " " + hour);
//            Log.d("min", " " + minute);
//
//            //check if hour is valid, greater than 0 and less than 24.
//            if (!(hour >= 0 && hour < 24)) {
//                formErrors += "Starting time invalid HOUR\n";
//                formValidationPass = false;
//            }
//
//            //check if minute is valid, greater than 0, and less than 59.
//            if (!(minute >= 0 && minute < 59)) {
//                formErrors += "Starting time invalid MINUTE\n";
//                formValidationPass = false;
//
//            }
//        } else {
//            formValidationPass = false;
//            formErrors += "Invalid starting time format.\n";
//        }
//
//        if(formValidationPass){
//            declaredStartTimeEditText.setText(declaredStartTime);
//        }
//        updateInfoBanner(formErrors);
//
//        return formValidationPass;
//
//    }

    private void updateInfoBanner(String formErrors) {
        infoBannerTextView.setText(formErrors);
    }

    //TODO: add one to two zeros to end of starting till
    public boolean isStartingTillValid() {
        String formErrors;
        if (!startingTillAmountEditText.getText().toString().equals("")) {
            if(!startingTillAmountEditText.getText().toString().contains(".")){
                String startingTillPlusDecimalAndZeros = startingTillAmountEditText.getText().toString() + ".00";
                startingTillAmountEditText.setText(startingTillPlusDecimalAndZeros);
            }
            startingTillAmount = Double.parseDouble(startingTillAmountEditText.getText().toString());
            Log.d("Starting Till", " " + startingTillAmount);

            if (startingTillAmount > 0) {
                return true;
            }
        }
//        formErrors += "Invalid Starting Till";
        return false;
    }


}
