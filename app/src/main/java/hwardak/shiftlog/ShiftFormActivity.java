package hwardak.shiftlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

    EmployeeDataAccess employeeDataAccess;
    ShiftsDataAccess shiftsDataAccess;

    LinearLayout nonOverLapShiftFieldsLinearLayout;

    TextView infoBannerTextView;

    EditText employeeNameEditText;
    EditText dateEditText;
    EditText declaredStartTimeHourEditText;
    EditText declaredStartTimeMinuteEditText;
    EditText declaredEndTimeHourEditText;
    EditText declaredEndTimeMinuteEditText;
    EditText startingTillAmountEditText;
    EditText finalDropAmountEditText;
    EditText redemptionsEditText;

    EditText lotto1OpenEditText;
    EditText lotto2OpenEditText;
    EditText lotto3OpenEditText;
    EditText lotto4OpenEditText;
    EditText lotto5OpenEditText;
    EditText lotto10OpenEditText;
    EditText lotto20OpenEditText;
    EditText lotto30OpenEditText;
    EditText lottoOtherOpenEditText;
    ArrayList lottoOpenEditTextList;

    EditText lotto1AddEditText;
    EditText lotto2AddEditText;
    EditText lotto3AddEditText;
    EditText lotto4AddEditText;
    EditText lotto5AddEditText;
    EditText lotto10AddEditText;
    EditText lotto20AddEditText;
    EditText lotto30AddEditText;
    EditText lottoOtherAddEditText;

    EditText lotto1CloseEditText;
    EditText lotto2CloseEditText;
    EditText lotto3CloseEditText;
    EditText lotto4CloseEditText;
    EditText lotto5CloseEditText;
    EditText lotto10CloseEditText;
    EditText lotto20CloseEditText;
    EditText lotto30CloseEditText;
    EditText lottoOtherCloseEditText;

    EditText lotto1SoldEditText;
    EditText lotto2SoldEditText;
    EditText lotto3SoldEditText;
    EditText lotto4SoldEditText;
    EditText lotto5SoldEditText;
    EditText lotto10SoldEditText;
    EditText lotto20SoldEditText;
    EditText lotto30SoldEditText;
    EditText lottoOtherSoldEditText;

    EditText lotto1PassportEditText;
    EditText lotto2PassportEditText;
    EditText lotto3PassportEditText;
    EditText lotto4PassportEditText;
    EditText lotto5PassportEditText;
    EditText lotto10PassportEditText;
    EditText lotto20PassportEditText;
    EditText lotto30PassportEditText;
    EditText lottoOtherPassportEditText;

    EditText lottoOtherValueEditText;



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


    int lotto1Open;
    int lotto2Open;
    int lotto3Open;
    int lotto4Open;
    int lotto5Open;
    int lotto10Open;
    int lotto20Open;
    int lotto30Open;
    int lottoOtherValue;
    int lottoOtherOpen;
    int lottoOpenTotal;


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

        employeeHasOpenShift = this.doesEmployeeHaveOpenShift(userID);

        //If the employee has previsously signed in, but has not signed out yet, their unclosed
        //shift data is reloaded into a form.
        if (employeeHasOpenShift) {
            ArrayList<String> shiftData = shiftsDataAccess.getEmployeesOpenShiftData(userID);
            this.loadExistingForm(shiftData);

        } else {
            this.getStartTimeDate();
            this.preloadStartTime();
            this.loadNewForm(userID);

        }
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
        } else if (minute > 8 && minute < 22) {
            minuteString = "15";
        } else if (minute > 22 && minute < 37) {
            minuteString = "30";
        } else if (minute > 37 && minute < 52) {
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
        nonOverLapShiftFieldsLinearLayout = (LinearLayout) findViewById(R.id.nonOverLapShiftFieldsLinearLayout);

        lotto1OpenEditText = (EditText) findViewById(R.id.lotto1Open);
        lotto2OpenEditText = (EditText) findViewById(R.id.lotto2Open);
        lotto3OpenEditText = (EditText) findViewById(R.id.lotto3Open);
        lotto4OpenEditText = (EditText) findViewById(R.id.lotto4Open);
        lotto5OpenEditText = (EditText) findViewById(R.id.lotto5Open);
        lotto10OpenEditText = (EditText) findViewById(R.id.lotto10Open);
        lotto20OpenEditText = (EditText) findViewById(R.id.lotto20Open);
        lotto30OpenEditText = (EditText) findViewById(R.id.lotto30Open);
        lottoOtherOpenEditText = (EditText) findViewById(R.id.lottoOtherOpen);

        lotto1AddEditText = (EditText) findViewById(R.id.lotto1Add);
        lotto2AddEditText = (EditText) findViewById(R.id.lotto2Add);
        lotto3AddEditText = (EditText) findViewById(R.id.lotto3Add);
        lotto4AddEditText = (EditText) findViewById(R.id.lotto4Add);
        lotto5AddEditText = (EditText) findViewById(R.id.lotto5Add);
        lotto10AddEditText = (EditText) findViewById(R.id.lotto10Add);
        lotto20AddEditText = (EditText) findViewById(R.id.lotto20Add);
        lotto30AddEditText = (EditText) findViewById(R.id.lotto30Add);
        lottoOtherAddEditText = (EditText) findViewById(R.id.lottoOtherAdd);

        lotto1CloseEditText = (EditText) findViewById(R.id.lotto1Close);
        lotto2CloseEditText = (EditText) findViewById(R.id.lotto2Close);
        lotto3CloseEditText = (EditText) findViewById(R.id.lotto3Close);
        lotto4CloseEditText = (EditText) findViewById(R.id.lotto4Close);
        lotto5CloseEditText = (EditText) findViewById(R.id.lotto5Close);
        lotto10CloseEditText = (EditText) findViewById(R.id.lotto10Close);
        lotto20CloseEditText = (EditText) findViewById(R.id.lotto20Close);
        lotto30CloseEditText = (EditText) findViewById(R.id.lotto30Close);
        lottoOtherCloseEditText = (EditText) findViewById(R.id.lottoOtherClose);

        lotto1SoldEditText = (EditText) findViewById(R.id.lotto1Sold);
        lotto2SoldEditText = (EditText) findViewById(R.id.lotto2Sold);
        lotto3SoldEditText = (EditText) findViewById(R.id.lotto3Sold);
        lotto4SoldEditText = (EditText) findViewById(R.id.lotto4Sold);
        lotto5SoldEditText = (EditText) findViewById(R.id.lotto5Sold);
        lotto10SoldEditText = (EditText) findViewById(R.id.lotto10Sold);
        lotto20SoldEditText = (EditText) findViewById(R.id.lotto20Sold);
        lotto30SoldEditText = (EditText) findViewById(R.id.lotto30Sold);
        lottoOtherSoldEditText = (EditText) findViewById(R.id.lottoOtherSold);

        lotto1PassportEditText = (EditText) findViewById(R.id.lotto1Passport);
        lotto2PassportEditText = (EditText) findViewById(R.id.lotto2Passport);
        lotto3PassportEditText = (EditText) findViewById(R.id.lotto3Passport);
        lotto4PassportEditText = (EditText) findViewById(R.id.lotto4Passport);
        lotto5PassportEditText = (EditText) findViewById(R.id.lotto5Passport);
        lotto10PassportEditText = (EditText) findViewById(R.id.lotto10Passport);
        lotto20PassportEditText = (EditText) findViewById(R.id.lotto20Passport);
        lotto30PassportEditText = (EditText) findViewById(R.id.lotto30Passport);
        lottoOtherPassportEditText = (EditText) findViewById(R.id.lottoOtherPassport);

        lottoOtherValueEditText = (EditText) findViewById(R.id.lottoOtherValue);


        tillNumberRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("checkedId", " " + checkedId);

                //If the overlap radiobutton is clicked, the nonOverLapShiftFieldsLayout is made
                //visible.
                if(tillOverLapRadioButton.getId() == checkedId){
                    nonOverLapShiftFieldsLinearLayout.setVisibility(View.GONE);
                } else {
                    nonOverLapShiftFieldsLinearLayout.setVisibility(View.VISIBLE);
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
        if(areOpenningFormFieldsValid()){
//            openNewShift();
        }

    }



    private boolean areOpenningFormFieldsValid() {
        boolean pass = true;
        infoBannerTextView.setText("");



        //Check the declared start time.
        if(isDeclaredStartTimeValid()){
            declaredStartTime = getDeclaredStartTime();
        } else {
            pass = false;
        }

        if(isDeclaredEndTimeValid()){
            declaredEndTime = getDeclaredEndTime();
        } else {
            pass = false;
        }

        //If the shift is an over lap shift, retrun true, the rest of the checks are for on-till
        //shifts
        if(tillOverLapRadioButton.isChecked()){
            return true;
        }

        //Check the starting till amount.
        if(isStartingTillValid()){
            startingTillAmount = Double.parseDouble(startingTillAmountEditText.getText().toString());
        } else {
            pass = false;
        }

        if(isStartingLottoCountValid()){
            getLottoStartingCounts();
        } else {
            pass = false;
        }

        return pass;
    }

    private void getLottoStartingCounts() {

        lotto1Open = Integer.parseInt(lotto1OpenEditText.getText().toString());
        lotto2Open = Integer.parseInt(lotto2OpenEditText.getText().toString());
        lotto3Open = Integer.parseInt(lotto3OpenEditText.getText().toString());
        lotto4Open = Integer.parseInt(lotto4OpenEditText.getText().toString());
        lotto5Open = Integer.parseInt(lotto5OpenEditText.getText().toString());
        lotto10Open = Integer.parseInt(lotto10OpenEditText.getText().toString());
        lotto20Open = Integer.parseInt(lotto20OpenEditText.getText().toString());
        lotto30Open = Integer.parseInt(lotto30OpenEditText.getText().toString());
        lottoOtherValue = Integer.parseInt(lottoOtherValueEditText.getText().toString());
        lottoOtherOpen = Integer.parseInt(lottoOtherOpenEditText.getText().toString());

        lottoOpenTotal =
                + lotto1Open
                        + (lotto2Open * 2)
                        + (lotto3Open * 3)
                        + (lotto4Open * 4)
                        + (lotto5Open * 5)
                        + (lotto10Open * 10)
                        + (lotto20Open * 20)
                        + (lotto30Open * 30)
                        + (lottoOtherOpen * lottoOtherValue);

        Log.d("OpenTotal", " " + lottoOpenTotal);
    }

//    private int getLottoTotalOpenCount(){
//        return lotto1Open
//                + (lotto2Open * 2)
//                + (lotto3Open * 3)
//                + (lotto4Open * 4)
//                + (lotto5Open * 5)
//                + (lotto10Open * 10)
//                + (lotto20Open * 20)
//                + (lotto30Open * 30)
//                + (lottoOtherOpen * lottoOtherValue);
//    }


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


    /**
     * Checks and formats starting till value, and alerts user of invalid entries.
     * @return returns true if valid, false otherwise.
     */
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
            formErrors += "Starting Till Invalid.\n";
            updateInfoBanner(formErrors);
            pass = false;
        }
        return pass;
    }

    /**
     * Combines and returns the values from the declared start time editTexts.
     * @return declared start time in AmPm format.
     */
    public String getDeclaredStartTime() {
        String timeAmPm = "";
        timeAmPm
                = declaredStartTimeHourEditText.getText().toString()
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
        String timeAmPm = "";
        timeAmPm
                = declaredEndTimeHourEditText.getText().toString()
                + declaredEndTimeMinuteEditText.getText().toString()
                + endTimeAmPmToggleButton.getText().toString();

        Log.d("startTimeAmPm", timeAmPm);

        return timeAmPm;
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

    private boolean isDeclaredEndTimeValid() {
        boolean pass = true;
        String formErrors = "";

        //Check if the hour EditText is not empty.
        if(!declaredEndTimeHourEditText.getText().toString().equals("")){
            //Check if the hour EditText value is less than 12, since we will be using the AmPm format.
            if(Integer.parseInt(declaredEndTimeHourEditText.getText().toString()) > 12 ){
                pass = false;
                formErrors += "Invalid ending Hour.\n";
            }
        } else {
            pass = false;
            formErrors += "Enter ending time hour.\n";

        }

        //Check if the minute EditText is not empty.
        if(!declaredEndTimeMinuteEditText.getText().toString().equals("")) {
            //Check if the minute EditText value is less than 59.
            if (Integer.parseInt(declaredEndTimeMinuteEditText.getText().toString()) > 59) {
                pass = false;
                formErrors += "Invalid ending Minute.\n";
            }
        } else {
            pass = false;
            formErrors += "Enter ending time minute.\n";
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

    public boolean isStartingLottoCountValid() {
        boolean pass = true;
        String formErrors = "";

        if(lotto1OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto2OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto3OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto4OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto5OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto10OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto20OpenEditText.getText().toString().equals("")){
            pass = false;
        }
        if(lotto30OpenEditText.getText().toString().equals("")){
            pass = false;
        }

        if(lottoOtherValueEditText.getText().toString().equals("")){
            pass = false;
        }

        if(lottoOtherOpenEditText.getText().toString().equals("")){
            pass = false;
        }

        if(!pass){
            formErrors = "Invalid starting lotto entry.\n";
        } else {
            formErrors = "";
        }

        updateInfoBanner(formErrors);

        return pass;
    }
}
