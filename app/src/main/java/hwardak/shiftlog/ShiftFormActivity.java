package hwardak.shiftlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    ArrayList<EditText> lottoOpenEditTextsList;
    ArrayList<Integer> lottoOpenIntList;



    EditText lotto1AddEditText;
    EditText lotto2AddEditText;
    EditText lotto3AddEditText;
    EditText lotto4AddEditText;
    EditText lotto5AddEditText;
    EditText lotto10AddEditText;
    EditText lotto20AddEditText;
    EditText lotto30AddEditText;
    EditText lottoOtherAddEditText;
    ArrayList<EditText> lottoAddEditTextsList;
    ArrayList<Integer> lottoAddIntList;

    EditText lotto1CloseEditText;
    EditText lotto2CloseEditText;
    EditText lotto3CloseEditText;
    EditText lotto4CloseEditText;
    EditText lotto5CloseEditText;
    EditText lotto10CloseEditText;
    EditText lotto20CloseEditText;
    EditText lotto30CloseEditText;
    EditText lottoOtherCloseEditText;
    ArrayList<EditText> lottoCloseEditTextsList;
    ArrayList<Integer>  lottoCloseIntList;

    EditText lotto1SoldEditText;
    EditText lotto2SoldEditText;
    EditText lotto3SoldEditText;
    EditText lotto4SoldEditText;
    EditText lotto5SoldEditText;
    EditText lotto10SoldEditText;
    EditText lotto20SoldEditText;
    EditText lotto30SoldEditText;
    EditText lottoOtherSoldEditText;
    ArrayList<EditText> lottoSoldEditTextsList;
    ArrayList<Integer>lottoSoldIntList;


    EditText lotto1PassportEditText;
    EditText lotto2PassportEditText;
    EditText lotto3PassportEditText;
    EditText lotto4PassportEditText;
    EditText lotto5PassportEditText;
    EditText lotto10PassportEditText;
    EditText lotto20PassportEditText;
    EditText lotto30PassportEditText;
    EditText lottoOtherPassportEditText;
    ArrayList<EditText> lottoPassportEditTextsList;
    ArrayList<Integer> lottoPassportIntList;

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

    int lottoOtherValue;

    int lotto1Open;
    int lotto2Open;
    int lotto3Open;
    int lotto4Open;
    int lotto5Open;
    int lotto10Open;
    int lotto20Open;
    int lotto30Open;
    int lottoOtherOpen;
    int lottoOpenTotal;

    int lotto1Add;
    int lotto2Add;
    int lotto3Add;
    int lotto4Add;
    int lotto5Add;
    int lotto10Add;
    int lotto20Add;
    int lotto30Add;
    int lottoOtherAdd;
    int lottoAddTotal;

    int lotto1Close;
    int lotto2Close;
    int lotto3Close;
    int lotto4Close;
    int lotto5Close;
    int lotto10Close;
    int lotto20Close;
    int lotto30Close;
    int lottoOtherClose;
    int lottoCloseTotal;

    int lotto1Sold;
    int lotto2Sold;
    int lotto3Sold;
    int lotto4Sold;
    int lotto5Sold;
    int lotto10Sold;
    int lotto20Sold;
    int lotto30Sold;
    int lottoOtherSold;
    int lottoSoldTotal;

    int lotto1Passport;
    int lotto2Passport;
    int lotto3Passport;
    int lotto4Passport;
    int lotto5Passport;
    int lotto10Passport;
    int lotto20Passport;
    int lotto30Passport;
    int lottoOtherPassport;
    int lottoPassportTotal;

    int lotto1Difference;
    int lotto2Difference;
    int lotto3Difference;
    int lotto4Difference;
    int lotto5Difference;
    int lotto10Difference;
    int lotto20Difference;
    int lotto30Difference;
    int lottoOtherDifference;

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

        lottoOpenEditTextsList = new ArrayList<EditText>();
        lottoAddEditTextsList = new ArrayList<EditText>();
        lottoCloseEditTextsList = new ArrayList<EditText>();
        lottoSoldEditTextsList = new ArrayList<EditText>();
        lottoPassportEditTextsList = new ArrayList<EditText>();

        lottoOpenIntList = new ArrayList<Integer>();
        lottoAddIntList = new ArrayList<Integer>();
        lottoCloseIntList = new ArrayList<Integer>();
        lottoSoldIntList = new ArrayList<Integer>();
        lottoPassportIntList = new ArrayList<Integer>();



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


    private void activateTextChangeListners(){

        lotto1CloseEditText.addTextChangedListener(new TextWatcher() {
            //Not in use.
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //Not in use
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //
            @Override
            public void afterTextChanged(Editable s) {

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

    private boolean doesEmployeeHaveOpenShift(int userID) {
        if (shiftsDataAccess.doesEmployeeHaveOpenShift(userID)) {
            return true;
        } else {
            return false;
        }
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
     * Checks and formats starting till value, and alerts user of invalid entries.
     * @return returns true if valid, false otherwise.
     */
    private boolean isStartingTillValid() {
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

        //If starting till is NOT empty.
        if (!finalDropAmountEditText.getText().toString().equals("")) {
            String finalDrop = finalDropAmountEditText.getText().toString();

            //If starting till contains a decimal.
            if (finalDrop.contains(".")) {

                //If no chars after decimal, add '00'
                if (finalDrop.substring(finalDrop.indexOf(".")).length() == 1) {
                    finalDrop += "00";
                }

                //If only 1 char after decimal, add '0'
                if (finalDrop.substring(finalDrop.indexOf(".")).length() == 2) {
                    finalDrop += "0";
                }

                //No decimal, add '.00'
            } else {
                finalDrop += ".00";
            }
            //Update starting till EditText with formatted starting till value.
            finalDropAmountEditText.setText(finalDrop);

            Log.d("Final Drop", " " + finalDrop);

        } else {
            formErrors += "Final drop invalid.\n";
            updateInfoBanner(formErrors);
            pass = false;
        }
        return pass;


    }





    private boolean isOpenLottoCountValid() {
        boolean pass = true;
        String formErrors = "";


        for(int i = 0; i < lottoOpenEditTextsList.size(); i++){
            if(lottoOpenEditTextsList.get(i).getText().toString().equals("")){
                pass = false;
            }
        }

//        if(lotto1OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//
//        if(lotto2OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto3OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto4OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto5OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto10OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto20OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto30OpenEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherValueEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherOpenEditText.getText().toString().equals("")){
//            pass = false;
//        }

        if(!pass){
            formErrors = "Invalid starting lotto entry.\n";
        } else {
            formErrors = "";
        }

        updateInfoBanner(formErrors);

        return pass;
    }

    private boolean isAddLottoCountValid() {

        boolean pass = true;
        String formErrors = "";

        for(int i = 0; i < lottoAddEditTextsList.size(); i++){
            if(lottoAddEditTextsList.get(i).getText().toString().equals("")){
                pass = false;
            }
        }


//        if(lotto1AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto2AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto3AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto4AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto5AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto10AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto20AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto30AddEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherValueEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherAddEditText.getText().toString().equals("")){
//            pass = false;
//        }

        if(!pass){
            formErrors = "Invalid add lotto entry.\n";
        } else {
            formErrors = "";
        }

        updateInfoBanner(formErrors);

        return pass;
    }

    private boolean isCloseLottoCountValid() {

        boolean pass = true;
        String formErrors = "";

        for(int i = 0; i < lottoCloseEditTextsList.size(); i++){
            if(lottoCloseEditTextsList.get(i).getText().toString().equals("")){
                pass = false;
            }
        }

//        if(lotto1CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto2CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto3CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto4CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto5CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto10CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto20CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto30CloseEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherValueEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherCloseEditText.getText().toString().equals("")){
//            pass = false;
//        }

        if(!pass){
            formErrors = "Invalid close lotto entry.\n";
        } else {
            formErrors = "";
        }

        updateInfoBanner(formErrors);

        return pass;
    }

    private boolean isPassportLottoCountValid() {

        boolean pass = true;
        String formErrors = "";

        for(int i = 0; i < lottoPassportEditTextsList.size(); i++){
            if(lottoPassportEditTextsList.get(i).getText().toString().equals("")){
                pass = false;
            }
        }

//        if(lotto1PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto2PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto3PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto4PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto5PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto10PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto20PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//        if(lotto30PassportEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherValueEditText.getText().toString().equals("")){
//            pass = false;
//        }
//
//        if(lottoOtherPassportEditText.getText().toString().equals("")){
//            pass = false;
//        }

        if(!pass){
            formErrors = "Invalid Passport lotto entry.\n";
        } else {
            formErrors = "";
        }

        updateInfoBanner(formErrors);

        return pass;


    }



    private void getLottoOpenCounts() {

        //TODO: How can I make this work?
//        for(int i = 0; i < lottoOpenIntList.size(); i++){
//            lottoOpenIntList.get(i) = Integer.parseInt(lottoOpenEditTextsList.get(i).getText().toString());
//        }


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

    private void getLottoAddCounts() {

        lotto1Add = Integer.parseInt(lotto1AddEditText.getText().toString());
        lotto2Add = Integer.parseInt(lotto2AddEditText.getText().toString());
        lotto3Add = Integer.parseInt(lotto3AddEditText.getText().toString());
        lotto4Add = Integer.parseInt(lotto4AddEditText.getText().toString());
        lotto5Add = Integer.parseInt(lotto5AddEditText.getText().toString());
        lotto10Add = Integer.parseInt(lotto10AddEditText.getText().toString());
        lotto20Add = Integer.parseInt(lotto20AddEditText.getText().toString());
        lotto30Add = Integer.parseInt(lotto30AddEditText.getText().toString());
        lottoOtherAdd = Integer.parseInt(lottoOtherAddEditText.getText().toString());

        lottoAddTotal =
                + lotto1Add
                        + (lotto2Add * 2)
                        + (lotto3Add * 3)
                        + (lotto4Add * 4)
                        + (lotto5Add * 5)
                        + (lotto10Add * 10)
                        + (lotto20Add * 20)
                        + (lotto30Add * 30)
                        + (lottoOtherAdd * lottoOtherValue);

        Log.d("AddTotal", " " + lottoAddTotal);
    }

    private void getLottoCloseCounts() {
        lotto1Close = Integer.parseInt(lotto1CloseEditText.getText().toString());
        lotto2Close = Integer.parseInt(lotto2CloseEditText.getText().toString());
        lotto3Close = Integer.parseInt(lotto3CloseEditText.getText().toString());
        lotto4Close = Integer.parseInt(lotto4CloseEditText.getText().toString());
        lotto5Close = Integer.parseInt(lotto5CloseEditText.getText().toString());
        lotto10Close = Integer.parseInt(lotto10CloseEditText.getText().toString());
        lotto20Close = Integer.parseInt(lotto20CloseEditText.getText().toString());
        lotto30Close = Integer.parseInt(lotto30CloseEditText.getText().toString());
        lottoOtherValue = Integer.parseInt(lottoOtherValueEditText.getText().toString());
        lottoOtherClose = Integer.parseInt(lottoOtherCloseEditText.getText().toString());

        lottoCloseTotal =
                + lotto1Sold
                        + (lotto2Close * 2)
                        + (lotto3Close * 3)
                        + (lotto4Close * 4)
                        + (lotto5Close * 5)
                        + (lotto10Close * 10)
                        + (lotto20Close * 20)
                        + (lotto30Close * 30)
                        + (lottoOtherClose * lottoOtherValue);

        Log.d("CloseTotal", " " + lottoCloseTotal);
    }

    private void getLottoSoldCount() {
        lotto1Sold = Integer.parseInt(lotto1SoldEditText.getText().toString());
        lotto2Sold = Integer.parseInt(lotto2SoldEditText.getText().toString());
        lotto3Sold = Integer.parseInt(lotto3SoldEditText.getText().toString());
        lotto4Sold = Integer.parseInt(lotto4SoldEditText.getText().toString());
        lotto5Sold = Integer.parseInt(lotto5SoldEditText.getText().toString());
        lotto10Sold = Integer.parseInt(lotto10SoldEditText.getText().toString());
        lotto20Sold = Integer.parseInt(lotto20SoldEditText.getText().toString());
        lotto30Sold = Integer.parseInt(lotto30SoldEditText.getText().toString());
        lottoOtherValue = Integer.parseInt(lottoOtherValueEditText.getText().toString());
        lottoOtherSold = Integer.parseInt(lottoOtherSoldEditText.getText().toString());

        lottoSoldTotal =
                + lotto1Sold
                        + (lotto2Sold * 2)
                        + (lotto3Sold * 3)
                        + (lotto4Sold * 4)
                        + (lotto5Sold * 5)
                        + (lotto10Sold * 10)
                        + (lotto20Sold * 20)
                        + (lotto30Sold * 30)
                        + (lottoOtherSold * lottoOtherValue);

        Log.d("SoldTotal", " " + lottoSoldTotal);

    }

    private void getLottoPassportCounts() {
        lotto1Passport = Integer.parseInt(lotto1PassportEditText.getText().toString());
        lotto2Passport = Integer.parseInt(lotto2PassportEditText.getText().toString());
        lotto3Passport = Integer.parseInt(lotto3PassportEditText.getText().toString());
        lotto4Passport = Integer.parseInt(lotto4PassportEditText.getText().toString());
        lotto5Passport = Integer.parseInt(lotto5PassportEditText.getText().toString());
        lotto10Passport = Integer.parseInt(lotto10PassportEditText.getText().toString());
        lotto20Passport = Integer.parseInt(lotto20PassportEditText.getText().toString());
        lotto30Passport = Integer.parseInt(lotto30PassportEditText.getText().toString());
        lottoOtherPassport = Integer.parseInt(lottoOtherPassportEditText.getText().toString());

        lottoPassportTotal =
                + lotto1Passport
                        + (lotto2Passport * 2)
                        + (lotto3Passport * 3)
                        + (lotto4Passport * 4)
                        + (lotto5Passport * 5)
                        + (lotto10Passport * 10)
                        + (lotto20Passport * 20)
                        + (lotto30Passport * 30)
                        + (lottoOtherPassport * lottoOtherValue);

        Log.d("PassportTotal", " " + lottoPassportTotal);

    }



    private boolean areClosingFormFieldsValid(){
        boolean pass = true;
        infoBannerTextView.setText("");

        if(isFinalDropValid()){
            finalDropAmount = Double.parseDouble(finalDropAmountEditText.getText().toString());
        } else {
            pass = false;
        }

        if(isRedemptionsValid()) {
            redemptionsAmount = Double.parseDouble(redemptionsEditText.getText().toString());
        } else {
            pass = false;
        }

        if(isAddLottoCountValid()){
            getLottoAddCounts();
        } else {
            pass = false;
        }

        if(isCloseLottoCountValid()){
            getLottoCloseCounts();
        } else {
            pass = false;
        }

        if(isPassportLottoCountValid()){
            getLottoPassportCounts();
        } else {
            pass = false;
        }

        if(pass){
            getLottoSoldCount();
        }


        return pass;
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

        if(isOpenLottoCountValid()){
            getLottoOpenCounts();
        } else {
            pass = false;
        }

        return pass;
    }


    public void openShiftButtonOnClick(View view) {
        actualStartTime = calendar.getTime().toString().substring(11, 16);
        if(areOpenningFormFieldsValid()){
//            openNewShift();
        }
    }

    public void closeShiftButtonOnClick(View view) {
        actualEndTime = calendar.getTime().toString().substring(11, 16);
        areClosingFormFieldsValid();
    }




    private void LoadEditTextLists(){
        lottoOpenEditTextsList.add(lotto1OpenEditText);
        lottoOpenEditTextsList.add(lotto2OpenEditText);
        lottoOpenEditTextsList.add(lotto3OpenEditText);
        lottoOpenEditTextsList.add(lotto4OpenEditText);
        lottoOpenEditTextsList.add(lotto5OpenEditText);
        lottoOpenEditTextsList.add(lotto10OpenEditText);
        lottoOpenEditTextsList.add(lotto20OpenEditText);
        lottoOpenEditTextsList.add(lotto30OpenEditText);
        lottoOpenEditTextsList.add(lottoOtherOpenEditText);

        lottoAddEditTextsList.add(lotto1AddEditText);
        lottoAddEditTextsList.add(lotto2AddEditText);
        lottoAddEditTextsList.add(lotto3AddEditText);
        lottoAddEditTextsList.add(lotto4AddEditText);
        lottoAddEditTextsList.add(lotto5AddEditText);
        lottoAddEditTextsList.add(lotto10AddEditText);
        lottoAddEditTextsList.add(lotto20AddEditText);
        lottoAddEditTextsList.add(lotto30AddEditText);
        lottoAddEditTextsList.add(lottoOtherAddEditText);

        lottoCloseEditTextsList.add(lotto1CloseEditText);
        lottoCloseEditTextsList.add(lotto2CloseEditText);
        lottoCloseEditTextsList.add(lotto3CloseEditText);
        lottoCloseEditTextsList.add(lotto4CloseEditText);
        lottoCloseEditTextsList.add(lotto5CloseEditText);
        lottoCloseEditTextsList.add(lotto10CloseEditText);
        lottoCloseEditTextsList.add(lotto20CloseEditText);
        lottoCloseEditTextsList.add(lotto30CloseEditText);
        lottoCloseEditTextsList.add(lottoOtherCloseEditText);

        lottoSoldEditTextsList.add(lotto1SoldEditText);
        lottoSoldEditTextsList.add(lotto2SoldEditText);
        lottoSoldEditTextsList.add(lotto3SoldEditText);
        lottoSoldEditTextsList.add(lotto4SoldEditText);
        lottoSoldEditTextsList.add(lotto5SoldEditText);
        lottoSoldEditTextsList.add(lotto10SoldEditText);
        lottoSoldEditTextsList.add(lotto20SoldEditText);
        lottoSoldEditTextsList.add(lotto30SoldEditText);
        lottoSoldEditTextsList.add(lottoOtherSoldEditText);

        lottoPassportEditTextsList.add(lotto1PassportEditText);
        lottoPassportEditTextsList.add(lotto2PassportEditText);
        lottoPassportEditTextsList.add(lotto3PassportEditText);
        lottoPassportEditTextsList.add(lotto4PassportEditText);
        lottoPassportEditTextsList.add(lotto5PassportEditText);
        lottoPassportEditTextsList.add(lotto10PassportEditText);
        lottoPassportEditTextsList.add(lotto20PassportEditText);
        lottoPassportEditTextsList.add(lotto30PassportEditText);
        lottoPassportEditTextsList.add(lottoOtherPassportEditText);
    }

    private void loadLottoIntList(){
        lottoOpenIntList.add(lotto1Open);
        lottoOpenIntList.add(lotto2Open);
        lottoOpenIntList.add(lotto3Open);
        lottoOpenIntList.add(lotto4Open);
        lottoOpenIntList.add(lotto5Open);
        lottoOpenIntList.add(lotto10Open);
        lottoOpenIntList.add(lotto2Open);
        lottoOpenIntList.add(lotto3Open);
        lottoOpenIntList.add(lottoOtherOpen);

        lottoAddIntList.add(lotto1Add);
        lottoAddIntList.add(lotto2Add);
        lottoAddIntList.add(lotto3Add);
        lottoAddIntList.add(lotto4Add);
        lottoAddIntList.add(lotto5Add);
        lottoAddIntList.add(lotto10Add);
        lottoAddIntList.add(lotto20Add);
        lottoAddIntList.add(lotto30Add);
        lottoAddIntList.add(lottoOtherAdd);

        lottoCloseIntList.add(lotto1Close);
        lottoCloseIntList.add(lotto2Close);
        lottoCloseIntList.add(lotto3Close);
        lottoCloseIntList.add(lotto4Close);
        lottoCloseIntList.add(lotto5Close);
        lottoCloseIntList.add(lotto10Close);
        lottoCloseIntList.add(lotto20Close);
        lottoCloseIntList.add(lotto30Close);
        lottoCloseIntList.add(lottoOtherClose);

        lottoSoldIntList.add(lotto1Sold);
        lottoSoldIntList.add(lotto2Sold);
        lottoSoldIntList.add(lotto3Sold);
        lottoSoldIntList.add(lotto4Sold);
        lottoSoldIntList.add(lotto5Sold);
        lottoSoldIntList.add(lotto10Sold);
        lottoSoldIntList.add(lotto20Sold);
        lottoSoldIntList.add(lotto30Sold);
        lottoSoldIntList.add(lottoOtherSold);

        lottoPassportIntList.add(lotto1Passport);
        lottoPassportIntList.add(lotto2Passport);
        lottoPassportIntList.add(lotto3Passport);
        lottoPassportIntList.add(lotto4Passport);
        lottoPassportIntList.add(lotto5Passport);
        lottoPassportIntList.add(lotto10Passport);
        lottoPassportIntList.add(lotto20Passport);
        lottoPassportIntList.add(lotto30Passport);
        lottoPassportIntList.add(lottoOtherPassport);

    }






}
