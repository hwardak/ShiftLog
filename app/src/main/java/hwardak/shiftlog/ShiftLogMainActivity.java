package hwardak.shiftlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ShiftLogMainActivity extends AppCompatActivity {


    private EditText employeeIdEditText;

    EmployeeDataAccess employeeDataAccess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.instantiateAllVariable();
        this.applyTextChangeListener();

    }

    private void instantiateAllVariable() {
        employeeDataAccess = new EmployeeDataAccess(this);
        employeeIdEditText = (EditText) findViewById(R.id.employeeIdEditText);
    }


    private void applyTextChangeListener() {
        final  Intent shiftFormIntent = new Intent(this, ShiftFormActivity.class);


        employeeIdEditText.addTextChangedListener(new TextWatcher() {
            @Override

            /*
             * Not in use.
             */
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChangeEF:", s.toString());

            }

            /*
             * Not in use.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChangeEF:", s.toString());

            }

            /*
             * After a new char is added to the employee id EditText, this method is invoked.
             */
            @Override
            public void afterTextChanged(Editable userInput) {
                //    Log.i("afterTextChangeEF:", userID.toString());
                if(!userInput.toString().equals("")) {
                    int userID = Integer.parseInt(userInput.toString());
                    if (employeeDataAccess.doesEmployeeExist(userID)) {
                        shiftFormIntent.putExtra("userID", userID);
                        startActivity(shiftFormIntent);
                    }
                }
            }


        });
    }

    public void EmployeesButtonOnClick(View view) {
        final Intent employeeLogIntent = new Intent(this, EmployeeOptions.class);
        startActivity(employeeLogIntent);
    }

    public void ShiftLogButtonOnClick(View view) {
        final Intent shiftLogRecord = new Intent(this, ShiftLogRecord.class);
        startActivity(shiftLogRecord);
    }

    public void SettingsButtonOnClick(View view) {
        final Intent settingsIntent = new Intent(this, ShiftLogSettings.class);
        startActivity(settingsIntent);
    }

}